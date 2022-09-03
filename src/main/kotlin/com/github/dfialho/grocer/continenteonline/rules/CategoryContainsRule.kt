package com.github.dfialho.grocer.continenteonline.rules

import com.github.dfialho.grocer.continenteonline.OrderItem

class CategoryContainsRule(private val pattern: String, override val result: RuleResult) : Rule {

    override fun matches(orderItem: OrderItem): Boolean {
        return orderItem.category.contains(pattern)
    }
}
