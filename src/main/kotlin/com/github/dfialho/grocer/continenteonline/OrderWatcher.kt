package com.github.dfialho.grocer.continenteonline

import mu.KLogging
import java.nio.file.*
import kotlin.concurrent.thread

class OrderWatcher(private val watchDirectory: Path) {

    companion object : KLogging()

    private lateinit var watchService: WatchService
    private lateinit var directoryKey: WatchKey

    fun start() {

        thread {
            watchService = FileSystems.getDefault().newWatchService()
            directoryKey = watchDirectory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE)
            logger.info { "Watching directory: $watchDirectory" }

            while (true) {
                logger.debug { "Waiting for order files" }
                val watchKey = watchService.take()

                for (event in watchKey.pollEvents()) {
                    logger.info { "New file: ${event.kind()} ${event.context()}" }
                }

                if (!watchKey.reset()) {
                    logger.error { "The watch directory was deleted. Will stop service" }
                    watchKey.cancel()
                    stop()
                    break
                }
            }

            stop()
        }
    }

    fun stop() {
        logger.info { "Stopping" }
        directoryKey.cancel()
        watchService.close()
        logger.info { "Stopped" }
    }
}