package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Receipt

class OrderLabeler {

    private val labeler = ItemLabeler()

    fun label(order: Order): Receipt {
        return Receipt(
            id = order.id,
            store = "continente-online",
            date = order.date,
            items = order.items.map { labeler.label(it) }
        )
    }
}
