package com.github.dfialho.grocer

import java.util.*
import javax.ws.rs.*

@Path("categories")
class CategoryResource(private val service: CategoryService) {

    @GET
    fun getAll(): List<Category> {
        return service.getAll()
    }

    @POST
    fun create(category: SaveCategory): Category {
        return service.create(category)
    }

    @DELETE
    @Path("{categoryId}")
    fun remove(@PathParam("categoryId") categoryId: UUID) {
        service.remove(categoryId)
    }
}