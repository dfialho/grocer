package com.github.dfialho.grocer.rest.rules

import com.github.dfialho.grocer.rest.rules.conditions.NameIsCondition
import java.util.*
import javax.ws.rs.*

@Path("rules")
class RulesResource {

    @POST
    fun create(request: CreateRuleRequest): Rule {
        return Rule(
            id = UUID.randomUUID(),
            condition = NameIsCondition("BANANA trop"),
            categoryId = UUID.randomUUID(),
            categoryGroup = "Fruits",
            category = "Bananas",
        )
    }

    @GET
    fun list(@QueryParam("rule") ruleId: UUID): List<Rule> {
        return listOf(
            Rule(
                id = UUID.randomUUID(),
                condition = NameIsCondition("BANANA trop"),
                categoryId = UUID.randomUUID(),
                categoryGroup = "Fruits",
                category = "Bananas",
            ),
            Rule(
                id = UUID.randomUUID(),
                condition = NameIsCondition("CogMelo"),
                categoryId = UUID.randomUUID(),
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
            categoryId = UUID.randomUUID(),
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