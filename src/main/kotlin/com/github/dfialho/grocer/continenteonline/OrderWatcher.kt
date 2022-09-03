package com.github.dfialho.grocer.continenteonline

import mu.KLogging
import java.nio.file.*
import kotlin.concurrent.thread

class OrderWatcher(private val watchDirectory: Path, private val listener: Listener) {

    interface Listener {
        fun onOrderFile(orderFile: Path)
    }

    companion object : KLogging()

    private lateinit var watchService: WatchService
    private lateinit var directoryKey: WatchKey
    private lateinit var serviceThread: Thread

    fun start() {

        serviceThread = thread {
            watchService = FileSystems.getDefault().newWatchService()
            directoryKey = watchDirectory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE)
            logger.info { "Watching directory: $watchDirectory" }

            while (true) {
                logger.debug { "Waiting for order files" }
                val watchKey = try {
                    watchService.take()
                } catch (e: ClosedWatchServiceException) {
                    logger.info { "Stopping service thread" }
                    break
                }

                for (event in watchKey.pollEvents()) {
                    logger.debug { "New file: ${event.kind()} ${event.context()} ${event.count()}" }
                    val filePath = event.context() as Path
                    listener.onOrderFile(filePath.resolve(watchDirectory))
                }

                if (!watchKey.reset()) {
                    logger.error { "The watch directory was deleted. Will stop service" }
                    watchKey.cancel()
                    directoryKey.cancel()
                    watchService.close()
                    break
                }
            }

            logger.info { "Stopped service thread" }
        }

    }

    fun stop() {
        logger.info { "Stopping" }
        directoryKey.cancel()
        watchService.close()
        logger.info { "Waiting for service thread to close" }
        serviceThread.join()
        logger.info { "Stopped" }
    }
}