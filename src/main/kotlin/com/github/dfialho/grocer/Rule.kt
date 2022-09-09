package com.github.dfialho.grocer

import java.util.*

data class Rule(
    val id: UUID,
    val spec: RuleSpec,
)
