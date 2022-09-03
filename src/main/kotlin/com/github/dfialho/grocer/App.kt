package com.github.dfialho.grocer

import com.github.dfialho.grocer.continenteonline.ContinenteOnlineGrocer
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.config.ConfigProvider
import java.nio.file.Paths
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class App {

    private val watchDirectory: String = ConfigProvider.getConfig().getValue("grocer.continenteonline.directory", String::class.java)

    private val sink = DatabaseSink()
    private val grocer = ContinenteOnlineGrocer(Paths.get(watchDirectory), sink)

    fun onStart(@Observes ev: StartupEvent?) {
        grocer.start()
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        grocer.stop()
    }
}