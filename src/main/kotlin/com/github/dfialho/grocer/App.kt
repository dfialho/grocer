package com.github.dfialho.grocer

import com.github.dfialho.grocer.continenteonline.ContinenteOnlineGrocer
import io.agroal.api.AgroalDataSource
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import mu.KLogging
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.nio.file.Path
import java.nio.file.Paths
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import kotlin.io.path.extension

@ApplicationScoped
class App(
    @Suppress("CdiInjectionPointsInspection") dataSource: AgroalDataSource,
    @ConfigProperty(name = "grocer.continenteonline.directory") watchDirectory: String,
    @ConfigProperty(name = "grocer.continenteonline.rulesFile") rulesFile: String
) {
    private val grocer = ContinenteOnlineGrocer(
        watchDirectory = Paths.get(watchDirectory),
        rulesFile = Paths.get(rulesFile),
        sink = DatabaseSink(dataSource),
    )

    class ContinenteOnlineProcessor(override val watchDirectory: Path) : ReceiptFileProcessor {

        companion object : KLogging()

        override fun onReceiptFile(receiptFile: Path) {

            if (receiptFile.extension != "html") {
                logger.info { "Ignoring file because it is not HTML: $receiptFile" }
                return
            }

            logger.info { receiptFile }
        }
    }

    class ContinenteProcessor(override val watchDirectory: Path) : ReceiptFileProcessor {

        companion object : KLogging()

        override fun onReceiptFile(receiptFile: Path) {
            logger.info { receiptFile }
        }
    }

    private val watcher = ReceiptFileWatcher(
        listOf(
            ContinenteOnlineProcessor(watchDirectory = Paths.get("/home/igno/projects/grocer/exp/grocer/continenteonline")),
            ContinenteProcessor(watchDirectory = Paths.get("/home/igno/projects/grocer/exp/grocer/continente"))
        )
    )

    fun onStart(@Observes ev: StartupEvent?) {
        watcher.start()
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        watcher.stop()
    }
}