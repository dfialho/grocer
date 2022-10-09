package com.github.dfialho.grocer.rest.items

import java.util.*
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
                id = UUID.randomUUID(),
                name = "BANANA TROP",
                category = "Bananas",
                categoryGroup = "Fruits",
                amount = 1000,
                receiptId = UUID.randomUUID(),
                importCategory = "FRUITS & VEGS",
            ),
            Item(
                id = UUID.randomUUID(),
                name = "BANANA TROP",
                category = null,
                categoryGroup = null,
                amount = 1000,
                receiptId = UUID.randomUUID(),
                importCategory = "FRUITS & VEGS",
            )
        )
    }

    @GET
    @Path("{item-id}")
    fun get(@PathParam("item-id") itemId: UUID): Item {
        return Item(
            id = UUID.randomUUID(),
            name = "BANANA TROP",
            category = "Bananas",
            categoryGroup = "Fruits",
            amount = 1000,
            receiptId = UUID.randomUUID(),
            importCategory = "FRUITS & VEGS",
        )
    }
}