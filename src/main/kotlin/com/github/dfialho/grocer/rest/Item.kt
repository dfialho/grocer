package com.github.dfialho.grocer.rest

import java.util.*

data class Item(
    val id: UUID,
    val name: String,
    val category: String?,
    val categoryGroup: String?,
    val amount: Int,
    val receiptId: UUID,
    val importCategory: String,
)