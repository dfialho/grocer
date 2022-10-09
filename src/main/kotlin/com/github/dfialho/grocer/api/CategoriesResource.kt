package com.github.dfialho.grocer.api

import java.util.*
import java.util.UUID.randomUUID
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("categories")
class CategoriesResource {

    @POST
    fun create(request: CreateCategoryRequest): Category {
        return Category(
            id = randomUUID(),
            name = request.name,
            groupId = request.groupId,
            group = "Fruits",
        )
    }

    @GET
    fun list(): List<Category> {
        return listOf(
            Category(
                id = randomUUID(),
                name = "Bananas",
                groupId = randomUUID(),
                group = "Fruits",
            ),
            Category(
                id = randomUUID(),
                name = "Bread",
                groupId = randomUUID(),
                group = "Breakfast",
            ),
        )
    }

    @GET
    @Path("{category-id}")
    fun get(@PathParam("category-id") categoryId: UUID): Category {
        return Category(
            id = categoryId,
            name = "Bananas",
            groupId = randomUUID(),
            group = "Fruits",
        )
    }
}