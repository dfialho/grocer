package com.github.dfialho.grocer.continente

import com.github.dfialho.grocer.ReceiptFileProcessor
import mu.KLogging
import java.nio.file.Path
import kotlin.io.path.extension

class ContinenteProcessor(override val watchDirectory: Path) : ReceiptFileProcessor {
    companion object : KLogging()

    private val reader = ContinenteReceiptReader()

    override fun onReceiptFile(receiptFile: Path) {

        if (receiptFile.extension.lowercase() != "pdf") {
            logger.info { "Ignoring file because it is not PDF: $receiptFile" }
            return
        }

        val receipt = try {
            logger.info { "Reading file: $receiptFile" }
            reader.read(receiptFile)
        } catch (e: Exception) {
            logger.warn(e) { "Failed to read file: $receiptFile. Will ignore the file." }
            return
        }
    }
}