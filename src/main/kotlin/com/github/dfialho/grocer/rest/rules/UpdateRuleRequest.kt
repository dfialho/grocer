package com.github.dfialho.grocer.rest.rules

import com.github.dfialho.grocer.rest.rules.conditions.Condition
import java.util.*

data class UpdateRuleRequest(
    val condition: Condition,
    val categoryId: UUID
)