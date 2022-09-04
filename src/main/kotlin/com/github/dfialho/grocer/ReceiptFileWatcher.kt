package com.github.dfialho.grocer

import mu.KLogging
import java.nio.file.*
import java.util.concurrent.Executors
import kotlin.concurrent.thread


class ReceiptFileWatcher(private val processors: List<ReceiptFileProcessor>) {

    companion object : KLogging()

    private lateinit var watchService: WatchService
    private lateinit var serviceThread: Thread
    private val executor = Executors.newSingleThreadExecutor {
        val thread = Executors.defaultThreadFactory().newThread(it)
        thread.name = "processor"
        thread
    }

    fun start() {

        serviceThread = thread(name = "watcher") {
            watchService = FileSystems.getDefault().newWatchService()

            for (processor in processors) {
                processor.watchDirectory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE)
                logger.info { "Watching directory: ${processor.watchDirectory}" }
            }

            val processorsByDirectory = processors.associateBy { it.watchDirectory }

            while (true) {
                logger.debug { "Waiting for receipt files" }
                val watchKey = try {
                    watchService.take()
                } catch (e: ClosedWatchServiceException) {
                    logger.info { "Stopping watcher service thread" }
                    break
                }

                val watchDirectory = watchKey.watchable()

                for (event in watchKey.pollEvents()) {
                    logger.info { "New file '${event.kind()} ${event.context()} ${event.count()}' from directory '$watchDirectory'" }

                    val processor = processorsByDirectory[watchDirectory]
                    if (processor == null) {
                        logger.error { "No processor found for watch directory: $watchDirectory. This is not expected. Will ignore event" }
                        continue
                    }

                    val filePath = event.context() as Path

                    executor.submit {
                        try {
                            processor.onReceiptFile(processor.watchDirectory.resolve(filePath))
                        } catch (e: Exception) {
                            logger.error(e) { "Failed to process receipt file: $filePath" }
                        }
                    }
                }

                if (!watchKey.reset()) {
                    logger.error { "Watch directory was deleted: $watchDirectory" }
                    watchKey.cancel()
                    break
                }
            }

            logger.info { "Stopped watcher service thread" }
        }

    }

    fun stop() {
        logger.info { "Stopping" }
        watchService.close()
        logger.info { "Waiting for service thread to close" }
        serviceThread.join()
        logger.info { "Stopped" }
    }
}