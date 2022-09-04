package com.github.dfialho.grocer.continente

import java.time.LocalDate

data class ContinenteReceipt(
    val id: String,
    val date: LocalDate,
    val items: List<ContinenteItem>
)