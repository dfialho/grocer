package com.github.dfialho.grocer.rules

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import mu.KLogging
import java.nio.file.Path


class RuleRegistry(private val rulesFile: Path) {

    companion object : KLogging()

    private val mapper = ObjectMapper(YAMLFactory())
        .findAndRegisterModules()
        .registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, enabled = true)
                .configure(KotlinFeature.NullToEmptyMap, enabled = true)
                .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
                .configure(KotlinFeature.SingletonSupport, enabled = true)
                .configure(KotlinFeature.StrictNullChecks, enabled = true)
                .build()
        )

    private var cachedRules: RulesDefinition? = null

    data class RulesDefinition(val rules: List<Rule>)

    fun rules(): List<Rule> {
        val rules = try {
            mapper.readValue(rulesFile.toFile(), RulesDefinition::class.java)
        } catch (e: Exception) {
            val previousRules = cachedRules

            if (previousRules != null) {
                logger.error(e) { "Failed to read rules file. Will fallback on previous read rules." }
                previousRules
            } else {
                logger.error(e) { "Failed to read rules file. No previous rules to fallback on." }
                throw e
            }
        }

        return rules.rules
    }
}
