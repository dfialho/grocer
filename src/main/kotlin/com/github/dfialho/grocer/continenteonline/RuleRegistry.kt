package com.github.dfialho.grocer.continenteonline

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.dfialho.grocer.continenteonline.rules.Rule
import mu.KLogging
import java.nio.file.Paths


class RuleRegistry {

    companion object : KLogging()

    private val rulesFile = Paths.get("/home/dfialho/Projects/grocer/rules.yml")
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

    private var cachedRules: RulesList? = null

    data class RulesList(val rules: List<Rule>)

    fun rules(): List<Rule> {
        val rules = try {
            mapper.readValue(rulesFile.toFile(), RulesList::class.java)
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
