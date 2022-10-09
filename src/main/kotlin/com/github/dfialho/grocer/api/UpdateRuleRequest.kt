package com.github.dfialho.grocer.api

import java.util.*

data class UpdateRuleRequest(
    val condition: Condition,
    val categoryId: UUID
)
