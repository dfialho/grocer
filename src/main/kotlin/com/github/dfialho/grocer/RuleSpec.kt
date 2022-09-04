package com.github.dfialho.grocer

import com.github.dfialho.grocer.rules.RuleResult

data class RuleSpec(
    val condition: Condition,
    val result: RuleResult,
)
