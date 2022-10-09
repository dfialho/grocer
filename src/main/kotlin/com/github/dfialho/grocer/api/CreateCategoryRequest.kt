package com.github.dfialho.grocer.api

import java.util.*

data class CreateCategoryRequest(
    val name: String,
    val groupId: UUID
)
