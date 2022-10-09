package com.github.dfialho.grocer.rest.rules

import java.util.*

data class CreateRuleRequest(
    val name: String,
    val groupId: UUID
)