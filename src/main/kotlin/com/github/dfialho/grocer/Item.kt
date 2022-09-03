package com.github.dfialho.grocer

data class Item(
    val id: String,
    val name: String,
    val category: String,
    val amount: Long,
    val labeled: Boolean,
)
