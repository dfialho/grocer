package com.github.dfialho.grocer.api

sealed class ReceiptCategorization {
    object Unprocessed : ReceiptCategorization()
    object Processing : ReceiptCategorization()
    data class Processed(val uncategorizedItems: Long) : ReceiptCategorization()
}
