package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Quantity

data class OrderItem(
    val name: String,
    val category: String,
    val amount: Long,
    val quantity: Quantity,
)
