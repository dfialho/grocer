package com.github.dfialho.grocer.continente

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.StringReader
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ContinenteReceiptReader {

    fun read(receiptFile: Path): ContinenteReceipt {
        val text = PDDocument.load(receiptFile.toFile()).use { document ->
            PDFTextStripper().getText(document)
        }

        val iterator = StringReader(text).readLines().listIterator()
        val date = readDate(iterator)
        val items = readItems(iterator)
        val id = readId(iterator)

        return ContinenteReceipt(id, date, items)
    }

    private fun readItems(iterator: ListIterator<String>): MutableList<ContinenteItem> {
        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.contains("DESCRICAO VALOR")) {
                ContinenteProcessor.logger.debug { "Reached the start of the items list" }
                break
            }
        }

        val items = mutableListOf<ContinenteItem>()
        var category = "Unknown"

        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.startsWith("TOTAL A PAGAR")) {
                ContinenteProcessor.logger.debug { "Reached the end of the items list" }
                break
            }

            if (line.endsWith(":")) {
                ContinenteProcessor.logger.debug { "Category line: $line" }
                category = line.removeSuffix(":")
                ContinenteProcessor.logger.info { "Category '$category'" }
            } else if (!line.startsWith("(")) {
                ContinenteProcessor.logger.debug { "Skip line" }
                continue
            } else {
                ContinenteProcessor.logger.debug { "Item line: $line" }
                val lineWithoutTaxTag = line.substringAfter(") ")
                ContinenteProcessor.logger.info { "Item '$lineWithoutTaxTag'" }

                val tokens = lineWithoutTaxTag.split(" ")

                val item = if (tokens.last().matches(Regex("\\d+,\\d\\d"))) {
                    ContinenteItem(
                        category,
                        name = tokens.subList(0, tokens.lastIndex).joinToString(" "),
                        amount = tokens.last().replace(",", "").toLong(),
                    )

                } else {
                    ContinenteProcessor.logger.debug { "Amount is in the next line" }
                    val nextLine = iterator.next()
                    val tokensNextLine = nextLine.split(" ")
                    ContinenteItem(
                        category,
                        name = lineWithoutTaxTag.trim(),
                        amount = tokensNextLine.last().replace(",", "").toLong(),
                    )
                }

                ContinenteProcessor.logger.info { "$item" }
                items.add(item)
            }
        }
        return items
    }

    private fun readId(iterator: ListIterator<String>): String {
        var previousLine = ""
        while (iterator.hasNext()) {
            val line = iterator.next()
            if (previousLine.matches(Regex("\\d{20}")) && (line.contains("Processado por programa certificado") || line == "<at_qrcode>")) {
                return previousLine
            }
            previousLine = line
        }

        throw IllegalStateException("Receipt is missing the id")
    }

    private fun readDate(iterator: ListIterator<String>): LocalDate {

        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.startsWith("Nro: ")) {
                ContinenteProcessor.logger.debug { "Reached line with date" }
                val tokens = line.split(" ")
                val dateAsString = tokens[tokens.lastIndex - 1]
                return LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
        }

        throw IllegalStateException("Receipt is missing the date")
    }
}
