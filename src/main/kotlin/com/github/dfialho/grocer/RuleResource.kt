package com.github.dfialho.grocer

import javax.ws.rs.*

@Path("rules")
class RuleResource(private val service: RuleService) {

    @GET
    fun getAll(): List<Rule> {
        return service.getAll()
    }

    @POST
    fun create(rule: SaveRule): Rule {
        return service.create(rule)
    }

    @DELETE
    @Path("{ruleId}")
    fun remove(@PathParam("ruleId") ruleId: String) {
        service.remove(ruleId)
    }
}