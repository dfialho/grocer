package com.github.dfialho.grocer.continenteonline

import java.time.LocalDate

data class Order(
    val id: String,
    val date: LocalDate,
    val items: List<OrderItem>
)
