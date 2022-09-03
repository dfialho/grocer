package com.github.dfialho.grocer

import com.github.dfialho.grocer.continenteonline.ContinenteOnlineGrocer
import io.agroal.api.AgroalDataSource
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.config.ConfigProvider
import java.nio.file.Paths
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class App(dataSource: AgroalDataSource) {

    private val watchDirectory: String = ConfigProvider.getConfig().getValue("grocer.continenteonline.directory", String::class.java)
    private val rulesFile: String = ConfigProvider.getConfig().getValue("grocer.continenteonline.rulesFile", String::class.java)

    private val grocer = ContinenteOnlineGrocer(
        watchDirectory = Paths.get(watchDirectory),
        rulesFile = Paths.get(rulesFile),
        sink = DatabaseSink(dataSource),
    )

    fun onStart(@Observes ev: StartupEvent?) {
        grocer.start()
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        grocer.stop()
    }
}