package com.github.dfialho.grocer

import com.github.dfialho.grocer.rules.RuleRegistry
import java.util.UUID.randomUUID

class ItemLabeler(private val ruleRegistry: RuleRegistry) {

    fun label(unlabeledItem: UnlabeledItem): Item {

        val itemId = randomUUID().toString()

        for (rule in ruleRegistry.rules()) {
            if (rule.matches(unlabeledItem)) {
                return Item(
                    id = itemId,
                    category = rule.result.category,
                    subcategory = rule.result.subcategory ?: rule.result.category,
                    name = unlabeledItem.name,
                    amount = unlabeledItem.amount,
                    properties = unlabeledItem.properties,
                    labeled = true
                )
            }
        }

        return Item(
            id = itemId,
            category = unlabeledItem.category,
            subcategory = unlabeledItem.category,
            name = unlabeledItem.name,
            amount = unlabeledItem.amount,
            properties = unlabeledItem.properties,
            labeled = false
        )
    }
}
