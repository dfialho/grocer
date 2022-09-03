package com.github.dfialho.grocer.continenteonline

class NameContainsRule(private val pattern: String, override val result: RuleResult) : Rule {

    override fun matches(orderItem: OrderItem): Boolean {
        return orderItem.name.contains(pattern)
    }
}
