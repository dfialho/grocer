package com.github.dfialho.grocer.rest

import java.util.*

data class CreateRuleRequest(
    val name: String,
    val groupId: UUID
)
