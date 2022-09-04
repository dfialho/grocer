package com.github.dfialho.grocer.continente

import com.github.dfialho.grocer.UnlabeledReceipt
import java.time.LocalDate

data class ContinenteReceipt(
    val id: String,
    val date: LocalDate,
    val items: List<ContinenteItem>
) {
    fun toUnlabeled() = UnlabeledReceipt(
        id = id,
        date = date,
        items = items.map {
            it.toUnlabeled()
        }
    )
}