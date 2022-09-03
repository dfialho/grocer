package com.github.dfialho.grocer.continenteonline

interface Rule {
    val result: RuleResult

    fun matches(orderItem: OrderItem): Boolean
}
