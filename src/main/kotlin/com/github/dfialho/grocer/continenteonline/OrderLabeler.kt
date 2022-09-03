package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Item
import com.github.dfialho.grocer.Receipt
import java.util.UUID.randomUUID

class OrderLabeler {
    fun label(order: Order): Receipt {
        return Receipt(
            id = order.id,
            store = "continente-online",
            date = order.date,
            items = order.items.map {
                Item(
                    id = randomUUID().toString(),
                    name = it.name,
                    category = it.category,
                    amount = it.amount
                )
            }
        )
    }
}
