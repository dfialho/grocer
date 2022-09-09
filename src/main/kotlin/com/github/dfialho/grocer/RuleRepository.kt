package com.github.dfialho.grocer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.postgresql.util.PGobject
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.sql.DataSource

@ApplicationScoped
class RuleRepository(@Suppress("CdiInjectionPointsInspection") private val dataSource: DataSource) {

    private val mapper = ObjectMapper()
        .registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )

    fun all(): List<Rule> {

        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("SELECT * FROM rules")
            val resultSet = ps.executeQuery()

            val rules = mutableListOf<Rule>()
            while (resultSet.next()) {
                rules.add(
                    Rule(
                        id = resultSet.getObject("id") as UUID,
                        spec = mapper.readValue(resultSet.getString("spec")),
                    )
                )
            }

            return rules
        }
    }

    fun create(rule: Rule) {

        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("INSERT INTO rules VALUES (?, ?)")
            ps.setObject(1, rule.id)
            ps.setObject(2, PGobject().apply {
                type = "json"
                value = mapper.writeValueAsString(rule.spec)
            })
            ps.executeUpdate()
        }
    }

    fun delete(ruleId: UUID) {

        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("DELETE FROM rules WHERE id = ?")
            ps.setObject(1, ruleId)
            ps.executeUpdate()
        }
    }
}
