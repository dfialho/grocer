package com.github.dfialho.grocer.rules

import com.github.dfialho.grocer.UnlabeledItem

class CategoryContainsRule(private val pattern: String, override val result: RuleResult) : Rule {

    override fun matches(item: UnlabeledItem): Boolean {
        return item.category.contains(pattern)
    }
}
