package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Item
import java.util.UUID.randomUUID

class ItemLabeler {

    private val labelRules = listOf<Rule>(
        NameContainsRule(pattern = "Lenços", RuleResult(category = "Higiene", name = "Lenços"))
    )

    fun label(orderItem: OrderItem): Item {

        val itemId = randomUUID().toString()

        for (rule in labelRules) {
            if (rule.matches(orderItem)) {
                return Item(
                    id = itemId,
                    name = rule.result.name,
                    category = rule.result.category,
                    amount = orderItem.amount,
                    labeled = false
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
