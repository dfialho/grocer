package com.github.dfialho.grocer.continente

import com.github.dfialho.grocer.UnlabeledItem

data class ContinenteItem(
    val category: String,
    val name: String,
    val amount: Long,
) {
    fun toUnlabeled() = UnlabeledItem(
        category = category,
        name = name,
        amount = amount,
        properties = emptyMap()
    )
}