package com.github.dfialho.grocer.rest

import java.util.*
import java.util.UUID.randomUUID
import javax.ws.rs.*

@Path("rules")
class RulesResource {

    @POST
    fun create(request: CreateRuleRequest): Rule {
        return Rule(
            id = randomUUID(),
            condition = NameIsCondition("BANANA trop"),
            categoryId = randomUUID(),
            categoryGroup = "Fruits",
            category = "Bananas",
        )
    }

    @GET
    fun list(@QueryParam("rule") ruleId: UUID): List<Rule> {
        return listOf(
            Rule(
                id = randomUUID(),
                condition = NameIsCondition("BANANA trop"),
                categoryId = randomUUID(),
                categoryGroup = "Fruits",
                category = "Bananas",
            ),
            Rule(
                id = randomUUID(),
                condition = NameIsCondition("CogMelo"),
                categoryId = randomUUID(),
                categoryGroup = "Vegetables",
                category = "Mushrooms",
            ),
        )
    }

    @GET
    @Path("{rule-id}")
    fun get(@PathParam("rule-id") ruleId: UUID): Rule {
        return Rule(
            id = ruleId,
            condition = NameIsCondition("BANANA trop"),
            categoryId = randomUUID(),
            categoryGroup = "Fruits",
            category = "Bananas",
        )
    }

    @DELETE
    @Path("{rule-id}")
    fun delete(@PathParam("rule-id") ruleId: UUID) {
    }

    @PUT
    @Path("{rule-id}")
    fun update(@PathParam("rule-id") ruleId: UUID, request: UpdateRuleRequest): Rule {
        return Rule(
            id = ruleId,
            condition = request.condition,
            categoryId = request.categoryId,
            categoryGroup = "Fruits",
            category = "Bananas",
        )
    }
}