package com.github.dfialho.grocer

data class UnlabeledItem(
    val category: String,
    val name: String,
    val amount: Long,
    val properties: Map<String, Any>
)
