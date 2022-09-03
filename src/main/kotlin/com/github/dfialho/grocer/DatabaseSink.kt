package com.github.dfialho.grocer

import mu.KLogging

class DatabaseSink : Sink {

    companion object : KLogging()

    override fun sink(receipt: Receipt) {

        for (item in receipt.items) {
            if (!item.labeled) {
                logger.warn { "Item not labeled: $item" }
            }
        }
    }
}