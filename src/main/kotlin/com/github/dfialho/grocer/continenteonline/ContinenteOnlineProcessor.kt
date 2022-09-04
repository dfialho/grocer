package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.ItemLabeler
import com.github.dfialho.grocer.Receipt
import com.github.dfialho.grocer.ReceiptFileProcessor
import com.github.dfialho.grocer.Sink
import com.github.dfialho.grocer.rules.RuleRegistry
import mu.KLogging
import java.nio.file.Path
import kotlin.io.path.extension

class ContinenteOnlineProcessor(override val watchDirectory: Path, ruleRegistry: RuleRegistry, private val sink: Sink) : ReceiptFileProcessor {
    companion object : KLogging()

    private val orderReader = OrderReader()
    private val labeler = ContinenteOnlineLabeler(ItemLabeler(ruleRegistry))

    override fun onReceiptFile(receiptFile: Path) {

        if (receiptFile.extension.lowercase() != "html") {
            logger.info { "Ignoring file because it is not HTML: $receiptFile" }
            return
        }

        val order = try {
            logger.info { "Reading order file: $receiptFile" }
            orderReader.readOrder(receiptFile)
        } catch (e: Exception) {
            logger.warn(e) { "Failed to read order file: $receiptFile. Will ignore the file." }
            return
        }

        logger.info { "Labeling order: $order" }
        val receipt: Receipt = labeler.label(order.toUnlabeled())

        logger.info { "Sending receipt to sink: $receipt" }
        sink.sink(receipt)
    }
}