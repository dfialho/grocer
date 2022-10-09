package com.github.dfialho.grocer.rest

import java.util.*

data class UpdateRuleRequest(
    val condition: Condition,
    val categoryId: UUID
)
