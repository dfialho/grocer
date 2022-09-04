package com.github.dfialho.grocer

open class StoreLabeler(private val storeName: String, private val itemLabeler: ItemLabeler) {

    fun label(receipt: UnlabeledReceipt): Receipt {
        return Receipt(
            id = receipt.id,
            store = storeName,
            date = receipt.date,
            items = receipt.items.map {
                itemLabeler.label(it)
            }
        )
    }
}