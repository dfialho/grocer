package com.github.dfialho.grocer

import java.util.*
import java.util.UUID.randomUUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RuleService(private val repository: RuleRepository) {

    fun getAll(): List<Rule> {
        return repository.all()
    }

    fun create(rule: SaveRule): Rule {

        val createdRule = Rule(
            id = randomUUID(),
            spec = rule.spec
        )
        repository.create(createdRule)
        return createdRule
    }

    fun remove(ruleId: UUID) {
        repository.delete(ruleId)
    }
}