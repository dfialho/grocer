package com.github.dfialho.grocer.api

import java.util.*

data class CreateRuleRequest(
    val name: String,
    val groupId: UUID
)
