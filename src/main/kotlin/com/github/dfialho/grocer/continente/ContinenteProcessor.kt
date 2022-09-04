package com.github.dfialho.grocer.continente

import com.github.dfialho.grocer.ItemLabeler
import com.github.dfialho.grocer.ReceiptFileProcessor
import com.github.dfialho.grocer.rules.RuleRegistry
import mu.KLogging
import java.nio.file.Path
import kotlin.io.path.extension

class ContinenteProcessor(override val watchDirectory: Path, ruleRegistry: RuleRegistry) : ReceiptFileProcessor {
    companion object : KLogging()

    private val reader = ContinenteReceiptReader()
    private val labeler = ContinenteLabeler(ItemLabeler(ruleRegistry))

    override fun onReceiptFile(receiptFile: Path) {

        if (receiptFile.extension.lowercase() != "pdf") {
            logger.info { "Ignoring file because it is not PDF: $receiptFile" }
            return
        }

        val continenteReceipt = try {
            logger.info { "Reading file: $receiptFile" }
            reader.read(receiptFile)
        } catch (e: Exception) {
            logger.warn(e) { "Failed to read file: $receiptFile. Will ignore the file." }
            return
        }

        logger.info { "Continente receipt: $continenteReceipt" }
        val receipt = labeler.label(continenteReceipt.toUnlabeled())
        logger.info { "Labeled receipt: $receipt" }
    }
}