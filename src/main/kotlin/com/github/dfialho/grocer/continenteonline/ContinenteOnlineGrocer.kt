package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Grocer
import com.github.dfialho.grocer.Receipt
import com.github.dfialho.grocer.Sink
import mu.KLogging
import java.nio.file.Path


class ContinenteOnlineGrocer(watchDirectory: Path, rulesFile: Path, sink: Sink) : Grocer {

    companion object : KLogging()

    private val orderWatcher = OrderWatcher(
        watchDirectory = watchDirectory,
        listener = OrderProcessor(
            orderReader = OrderReader(),
            labeler = OrderLabeler(ItemLabeler(RuleRegistry(rulesFile))),
            sink = sink
        )
    )

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

    class OrderProcessor(
        private val orderReader: OrderReader,
        private val labeler: OrderLabeler,
        private val sink: Sink
    ) : OrderWatcher.Listener {

        override fun onOrderFile(orderFile: Path) {

            val order = try {
                logger.info { "Reading order file: $orderFile" }
                orderReader.readOrder(orderFile)
            } catch (e: Exception) {
                logger.warn(e) { "Failed to read order file: $orderFile. Will ignore the file." }
                return
            }

            logger.info { "Labeling order: $order" }
            val receipt: Receipt = labeler.label(order)

            logger.info { "Sending receipt to sink: $receipt" }
            sink.sink(receipt)
        }
    }
}