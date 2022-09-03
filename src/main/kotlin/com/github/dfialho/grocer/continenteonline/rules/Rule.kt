package com.github.dfialho.grocer.continenteonline.rules

import com.github.dfialho.grocer.continenteonline.OrderItem

interface Rule {
    val result: RuleResult

    fun matches(orderItem: OrderItem): Boolean
}
