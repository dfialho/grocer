package com.github.dfialho.grocer

import java.time.LocalDate

data class Receipt(
    val id: String,
    val store: String,
    val date: LocalDate,
    val items: List<Item>,
)
