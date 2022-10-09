package com.github.dfialho.grocer.rest

import java.util.*
import java.util.UUID.randomUUID
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("groups")
class CategoryGroupsResource {

    @POST
    fun create(request: CreateCategoryGroupRequest): CategoryGroup {
        return CategoryGroup(
            id = randomUUID(),
            name = request.name,
        )
    }

    @GET
    fun list(): List<CategoryGroup> {
        return listOf(
            CategoryGroup(
                id = randomUUID(),
                name = "Fruits",
            ),
            CategoryGroup(
                id = randomUUID(),
                name = "Breakfast",
            ),
        )
    }

    @GET
    @Path("{group-id}")
    fun get(@PathParam("group-id") groupId: UUID): CategoryGroup {
        return CategoryGroup(
            id = groupId,
            name = "Bananas",
        )
    }
}