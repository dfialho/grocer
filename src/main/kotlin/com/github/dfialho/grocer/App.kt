package com.github.dfialho.grocer

import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.config.ConfigProvider
import java.nio.file.Paths
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class App {

    private val watchDirectory: String = ConfigProvider.getConfig().getValue("grocer.continenteonline.directory", String::class.java)
    private val grocer = ContinenteOnlineGrocer(Paths.get(watchDirectory))

    fun onStart(@Observes ev: StartupEvent?) {
        grocer.start()
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        grocer.stop()
    }
}