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
            return connection.prepareStatement("SELECT * FROM rules")
                .executeQuery()
                .map {
                    Rule(
                        id = it.getObject("id") as UUID,
                        spec = mapper.readValue(it.getString("spec")),
                    )
                }
        }
    }

    fun create(rule: Rule) {

        dataSource.connection.use { connection ->
            with(connection.prepareStatement("INSERT INTO rules VALUES (?, ?)")) {
                setObject(1, rule.id)
                setObject(2, PGobject().apply {
                    type = "json"
                    value = mapper.writeValueAsString(rule.spec)
                })
                executeUpdate()
            }
        }
    }

    fun delete(ruleId: UUID) {

        dataSource.connection.use { connection ->
            with(connection.prepareStatement("DELETE FROM rules WHERE id = ?")) {
                setObject(1, ruleId)
                executeUpdate()
            }
        }
    }
}
