package com.github.dfialho.grocer

data class Item(
    val id: String,
    val category: String,
    val subcategory: String,
    val name: String,
    val amount: Long,
    val properties: Map<String, Any>,
    val labeled: Boolean,
)
