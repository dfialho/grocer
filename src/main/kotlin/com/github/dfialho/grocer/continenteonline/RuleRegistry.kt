package com.github.dfialho.grocer.continenteonline

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.dfialho.grocer.continenteonline.rules.Rule
import java.nio.file.Paths


class RuleRegistry {

    private val ruleConfigPath = Paths.get("/home/dfialho/Projects/grocer/rules.yml")
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

    data class RulesList(val rules: List<Rule>)

    fun rules(): List<Rule> {
        val rules = mapper.readValue(ruleConfigPath.toFile(), RulesList::class.java)
        return rules.rules
    }
}
