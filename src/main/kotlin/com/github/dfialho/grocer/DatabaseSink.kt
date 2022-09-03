package com.github.dfialho.grocer

import mu.KLogging

class DatabaseSink : Sink {

    companion object : KLogging()

    override fun sink(receipt: Receipt) {
        logger.info { "$receipt" }
    }
}