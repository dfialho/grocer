package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Item
import java.util.UUID.randomUUID

class ItemLabeler {

    private val ruleRegistry = RuleRegistry()
    fun label(orderItem: OrderItem): Item {

        val itemId = randomUUID().toString()

        for (rule in ruleRegistry.rules()) {
            if (rule.matches(orderItem)) {
                return Item(
                    id = itemId,
                    name = rule.result.name,
                    category = rule.result.category,
                    amount = orderItem.amount,
                    labeled = true
                )
            }
        }

        return Item(
            id = itemId,
            name = orderItem.name,
            category = orderItem.category,
            amount = orderItem.amount,
            labeled = false
        )
    }
}
