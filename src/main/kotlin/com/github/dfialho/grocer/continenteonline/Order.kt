package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.UnlabeledReceipt
import java.time.LocalDate

data class Order(
    val id: String,
    val date: LocalDate,
    val items: List<OrderItem>
) {
    fun toUnlabeled() = UnlabeledReceipt(
        id = id,
        date = date,
        items = items.map {
            it.toUnlabeled()
        }
    )
}
