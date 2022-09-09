package com.github.dfialho.grocer.continenteonline

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithName

@ConfigMapping(prefix = "grocer.continenteonline")
interface ContinenteOnlineConfig {

    @get:WithName("directory")
    val watchDirectory: String
}