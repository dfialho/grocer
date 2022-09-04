package com.github.dfialho.grocer.continente

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithName

@ConfigMapping(prefix = "grocer.continente")
interface ContinenteConfig {

    @get:WithName("directory")
    val watchDirectory: String

    @get:WithName("rulesFile")
    val rulesFile: String
}