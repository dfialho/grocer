package com.github.dfialho.grocer.rest.rules

import com.github.dfialho.grocer.rest.rules.conditions.Condition
import java.util.*

data class Rule(
    val id: UUID,
    val condition: Condition,
    val categoryId: UUID,
    val categoryGroup: String,
    val category: String,
)