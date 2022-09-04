package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.UnlabeledItem

data class OrderItem(
    val name: String,
    val category: String,
    val amount: Long,
    val quantity: Quantity,
) {
    fun toUnlabeled() = UnlabeledItem(
        category = category,
        name = name,
        amount = amount,
        properties = mapOf("quantity" to quantity)
    )
}
