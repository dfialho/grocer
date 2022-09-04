package com.github.dfialho.grocer

import com.github.dfialho.grocer.continente.ContinenteConfig
import com.github.dfialho.grocer.continente.ContinenteProcessor
import com.github.dfialho.grocer.continenteonline.ContinenteOnlineConfig
import com.github.dfialho.grocer.continenteonline.ContinenteOnlineProcessor
import com.github.dfialho.grocer.rules.RuleRegistry
import io.agroal.api.AgroalDataSource
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import java.nio.file.Paths
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class App(
    @Suppress("CdiInjectionPointsInspection") dataSource: AgroalDataSource,
    continenteOnlineConfig: ContinenteOnlineConfig,
    continenteConfig: ContinenteConfig,
) {
    private val sink = DatabaseSink(dataSource)
    private val watcher = ReceiptFileWatcher(
        listOf(
            ContinenteOnlineProcessor(
                watchDirectory = Paths.get(continenteOnlineConfig.watchDirectory),
                ruleRegistry = RuleRegistry(Paths.get(continenteOnlineConfig.rulesFile)),
                sink = sink
            ),
            ContinenteProcessor(
                watchDirectory = Paths.get(continenteConfig.watchDirectory),
                ruleRegistry = RuleRegistry(Paths.get(continenteConfig.rulesFile)),
            )
        )
    )

    fun onStart(@Observes ev: StartupEvent?) {
        watcher.start()
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        watcher.stop()
    }
}