package com.github.dfialho.grocer.rules

import com.github.dfialho.grocer.UnlabeledItem


class NameContainsRule(private val pattern: String, override val result: RuleResult) : Rule {

    override fun matches(item: UnlabeledItem): Boolean {
        return item.name.contains(pattern)
    }
}
