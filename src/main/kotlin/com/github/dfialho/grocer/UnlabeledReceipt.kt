package com.github.dfialho.grocer

import java.time.LocalDate

data class UnlabeledReceipt(
    val id: String,
    val date: LocalDate,
    val items: List<UnlabeledItem>,
)
