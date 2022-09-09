package com.github.dfialho.grocer

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RuleServiceRuleRegistry(private val service: RuleService) : RuleRegistry {
    override fun rules(): List<Rule> {
        return service.getAll()
    }
}