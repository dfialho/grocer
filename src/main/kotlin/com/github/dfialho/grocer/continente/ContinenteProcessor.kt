package com.github.dfialho.grocer.continente

import com.github.dfialho.grocer.ReceiptFileProcessor
import mu.KLogging
import java.nio.file.Path
import kotlin.io.path.extension

class ContinenteProcessor(override val watchDirectory: Path) : ReceiptFileProcessor {
    companion object : KLogging()

    private val reader = ContinenteReceiptReader()

    override fun onReceiptFile(receiptFile: Path) {

        if (receiptFile.extension != "pdf") {
            logger.info { "Ignoring file because it is not PDF: $receiptFile" }
            return
        }

        val receipt = reader.read(receiptFile)
        println(receipt)
    }
}