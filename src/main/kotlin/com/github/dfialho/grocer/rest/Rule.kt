package com.github.dfialho.grocer.rest

import java.util.*

data class Rule(
    val id: UUID,
    val condition: Condition,
    val categoryId: UUID,
    val categoryGroup: String,
    val category: String,
)
