package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Grocer
import mu.KLogging
import java.nio.file.Path


class ContinenteOnlineGrocer(watchDirectory: Path) : Grocer {

    companion object : KLogging()

    private val orderWatcher = OrderWatcher(watchDirectory)

    override fun start() {
        logger.info { "Starting" }
        orderWatcher.start()
        logger.info { "Started" }
    }

    override fun stop() {
        logger.info { "Stopping" }
        orderWatcher.stop()
        logger.info { "Stopped" }
    }
}