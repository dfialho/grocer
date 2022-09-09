package com.github.dfialho.grocer

import java.util.*

data class SubCategory(
    val id: UUID,
    val categoryId: UUID,
    val name: String
)
