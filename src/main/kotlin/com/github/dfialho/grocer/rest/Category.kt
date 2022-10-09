package com.github.dfialho.grocer.rest

import java.util.*

data class Category(
    val id: UUID,
    val name: String,
    val groupId: UUID,
    val group: String
)
