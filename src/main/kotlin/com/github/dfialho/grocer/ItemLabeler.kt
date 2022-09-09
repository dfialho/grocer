package com.github.dfialho.grocer

import mu.KLogging
import java.util.UUID.randomUUID

class ItemLabeler(private val ruleRegistry: RuleRegistry) {

    companion object : KLogging()

    fun label(unlabeledItem: UnlabeledItem): Item {

        val itemId = randomUUID().toString()

        for (rule in ruleRegistry.rules()) {
            if (rule.spec.condition.matches(unlabeledItem)) {
                logger.debug { "Item ${unlabeledItem.name} matched rule ${rule.id}" }
                return Item(
                    id = itemId,
                    category = rule.spec.result.category,
                    subcategory = rule.spec.result.subcategory ?: rule.spec.result.category,
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
