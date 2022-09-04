package com.github.dfialho.grocer

class NameContainsCondition(val pattern: String) : Condition {

    override fun matches(item: UnlabeledItem): Boolean {
        return item.name.contains(pattern)
    }
}
