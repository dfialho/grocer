package com.github.dfialho.grocer

import com.github.dfialho.grocer.rules.RuleResult
import java.util.UUID.randomUUID
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.NotFoundException

@ApplicationScoped
class RuleService(private val repository: RuleRepository) {

    private val rules = mutableListOf<Rule>(
        Rule(
            id = "a",
            spec = RuleSpec(
                condition = NameContainsCondition("Couve"),
                result = RuleResult("legumes", "couves")
            )
        )
    )

    fun getAll(): List<Rule> {
        return rules.toList()
    }

    fun create(rule: SaveRule): Rule {

        val createdRule = Rule(
            id = randomUUID().toString(),
            spec = rule.spec
        )
        rules.add(createdRule)
        return createdRule
    }

    fun remove(ruleId: String) {
        if (!rules.removeIf { it.id == ruleId }) {
            throw NotFoundException("Not rule exists with id '$ruleId'")
        }
    }
}