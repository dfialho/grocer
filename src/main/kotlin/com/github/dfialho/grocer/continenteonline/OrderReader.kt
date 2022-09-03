package com.github.dfialho.grocer.continenteonline

import mu.KLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.nio.file.Path
import java.time.LocalDate

class OrderReader {
    companion object : KLogging()

    fun readOrder(orderFile: Path): Order {
        val document = Jsoup.parse(orderFile.toFile())

        return Order(
            id = parseOrderId(document),
            date = parseDate(document),
            items = parseItems(document)
        )
    }

    private fun parseItems(document: Document): List<OrderItem> {

        val itemNames = document.select("a[class*=ct-font--opensans-bold d-block]").map { it.text() }
        val itemAmounts = document.select("span[class*=ct-order-history--product-total-price]")
            .map { it.text() }
            .map { it.replace("€", "") }
            .map { it.replace(",", "") }
            .map { it.toLong() }
        val itemCategories = document.select("div.ct-order-history--product-item")
            .map { it.attr("data-category") }

        return itemNames.zip(itemAmounts).zip(itemCategories)
            .map { (nameAndAmount, category) -> OrderItem(nameAndAmount.first, category, nameAndAmount.second) }
    }

    private fun parseDate(document: Document): LocalDate {

        val dateTokens = document.select("div[class*=ct-order-history--summary-delivery]")
            .select("span")
            .first()
            ?.text()
            ?.split(" ")
            ?: listOf("1", "Jan", "1970")

        val monthNumber = when (dateTokens[1]) {
            "Jan" -> 1
            "Fev" -> 2
            "Mar" -> 3
            "Abr" -> 4
            "Mai" -> 5
            "Jun" -> 6
            "Jul" -> 7
            "Ago" -> 8
            "Set" -> 9
            "Out" -> 10
            "Nov" -> 11
            "Dez" -> 12
            else -> 1
        }

        return LocalDate.of(dateTokens[2].toInt(), monthNumber, dateTokens[0].toInt())
    }

    private fun parseOrderId(document: Document): String {
        val orderElements = document.select("span[itemprop]:contains(Encomenda )")
        return orderElements[0].text().replace("Encomenda ", "").trim()
    }
}