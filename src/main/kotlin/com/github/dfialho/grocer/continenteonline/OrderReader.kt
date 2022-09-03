package com.github.dfialho.grocer.continenteonline

import mu.KLogging
import java.nio.file.Path
import java.time.LocalDate
import java.util.UUID.randomUUID

class OrderReader {
    companion object : KLogging()

    fun readOrder(orderFile: Path): Order {
        return Order(
            id = randomUUID().toString(),
            date = LocalDate.now(),
            items = emptyList()
        )
    }
}