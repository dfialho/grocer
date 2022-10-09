package com.github.dfialho.grocer.api

import java.time.LocalDate
import java.util.*

data class Receipt(
    val id: UUID,
    val name: String,
    val store: String,
    val amount: Long,
    val date: LocalDate,
    val categorization: ReceiptCategorization
)
