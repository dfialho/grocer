package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Grocer
import mu.KLogging
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import java.nio.file.WatchKey
import java.nio.file.WatchService
import kotlin.concurrent.thread


class ContinenteOnlineGrocer(private val watchDirectory: Path) : Grocer {

    companion object : KLogging()

    private lateinit var watchService: WatchService
    private lateinit var directoryKey: WatchKey

    override fun start() {
        logger.info { "Starting" }

        thread {
            watchService = FileSystems.getDefault().newWatchService()
            directoryKey = watchDirectory.register(watchService, ENTRY_CREATE)
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

        logger.info { "Started" }
    }

    override fun stop() {
        logger.info { "Stopping" }
        directoryKey.cancel()
        watchService.close()
        logger.info { "Stopped" }
    }
}