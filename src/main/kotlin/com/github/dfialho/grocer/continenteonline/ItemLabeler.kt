package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Item
import java.util.UUID.randomUUID

class ItemLabeler(private val ruleRegistry: RuleRegistry) {

    fun label(orderItem: OrderItem): Item {

        val itemId = randomUUID().toString()

        for (rule in ruleRegistry.rules()) {
            if (rule.matches(orderItem)) {
                return Item(
                    id = itemId,
                    category = rule.result.category,
                    subcategory = rule.result.subcategory ?: rule.result.category,
                    name = orderItem.name,
                    amount = orderItem.amount,
                    labeled = true
                )
            }
        }

        return Item(
            id = itemId,
            category = orderItem.category,
            subcategory = orderItem.category,
            name = orderItem.name,
            amount = orderItem.amount,
            labeled = false
        )
    }
}
