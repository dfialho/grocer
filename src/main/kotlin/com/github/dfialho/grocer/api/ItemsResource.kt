package com.github.dfialho.grocer.api

import java.util.*
import java.util.UUID.randomUUID
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.QueryParam

@Path("/items")
class ItemsResource {

    @GET
    fun list(@QueryParam("receipt") receiptId: UUID?): List<Item> {
        return listOf(
            Item(
                id = randomUUID(),
                name = "BANANA TROP",
                category = "Bananas",
                categoryGroup = "Fruits",
                amount = 1000,
                receiptId = randomUUID(),
                importCategory = "FRUITS & VEGS",
            ),
            Item(
                id = randomUUID(),
                name = "BANANA TROP",
                category = null,
                categoryGroup = null,
                amount = 1000,
                receiptId = randomUUID(),
                importCategory = "FRUITS & VEGS",
            )
        )
    }

    @GET
    @Path("{item-id}")
    fun get(@PathParam("item-id") itemId: UUID): Item {
        return Item(
            id = randomUUID(),
            name = "BANANA TROP",
            category = "Bananas",
            categoryGroup = "Fruits",
            amount = 1000,
            receiptId = randomUUID(),
            importCategory = "FRUITS & VEGS",
        )
    }
}