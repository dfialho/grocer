package com.github.dfialho.grocer.rest.receipts

import java.time.LocalDate
import java.util.*
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("receipts")
class ReceiptsResource {

    @GET
    fun list(): List<Receipt> {
        return listOf(
            Receipt(
                id = UUID.randomUUID(),
                name = "receipt B",
                store = "Continente",
                amount = 1000,
                date = LocalDate.of(2022, 10, 11),
                categorization = ReceiptCategorization.Unprocessed
            ),
            Receipt(
                id = UUID.randomUUID(),
                name = "receipt B",
                store = "Continente",
                amount = 1000,
                date = LocalDate.of(2022, 10, 11),
                categorization = ReceiptCategorization.Processing
            ),
            Receipt(
                id = UUID.randomUUID(),
                name = "receipt A",
                store = "Continente",
                amount = 1000,
                date = LocalDate.of(2022, 10, 10),
                categorization = ReceiptCategorization.Processed(3)
            ),
        )
    }

    @GET
    @Path("{receipt-id}")
    fun get(@PathParam("receipt-id") receiptId: UUID): Receipt {
        return Receipt(
            id = receiptId,
            name = "receipt A",
            store = "Continente",
            amount = 1000,
            date = LocalDate.of(2022, 10, 11),
            categorization = ReceiptCategorization.Unprocessed
        )
    }
}