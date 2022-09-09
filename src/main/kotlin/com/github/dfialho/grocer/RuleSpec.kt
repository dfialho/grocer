package com.github.dfialho.grocer

data class RuleSpec(
    val condition: Condition,
    val result: RuleResult,
)
