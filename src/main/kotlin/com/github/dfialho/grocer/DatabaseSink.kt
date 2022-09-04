package com.github.dfialho.grocer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.agroal.api.AgroalDataSource
import mu.KLogging
import org.postgresql.util.PGobject
import java.sql.Date


class DatabaseSink(private val dataSource: AgroalDataSource) : Sink {

    companion object : KLogging()

    private val mapper = ObjectMapper()
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

    override fun sink(receipt: Receipt) {

        for (item in receipt.items) {
            if (!item.labeled) {
                logger.warn { "Item not labeled: $item" }
            }
        }

        dataSource.connection.use { connection ->
            connection.autoCommit = false

            try {
                connection.prepareStatement("INSERT INTO receipts VALUES (?, ?, ?)").use { ps ->
                    ps.setString(1, receipt.id)
                    ps.setString(2, receipt.store)
                    ps.setDate(3, Date.valueOf(receipt.date))
                    ps.executeUpdate()
                }

                connection.prepareStatement("INSERT INTO items VALUES (?, ?, ?, ?, ?, ?, ?)").use { ps ->
                    for (item in receipt.items) {
                        ps.setString(1, item.id)
                        ps.setString(2, receipt.id)
                        ps.setString(3, item.category)
                        ps.setString(4, item.subcategory)
                        ps.setString(5, item.name)
                        ps.setLong(6, item.amount)
                        ps.setObject(7, PGobject().apply {
                            type = "json"
                            value = mapper.writeValueAsString(item.properties)
                        })
                        ps.addBatch()
                    }
                    ps.executeBatch()
                }

                connection.commit()
                logger.info { "Stored receipt ${receipt.id}" }

            } catch (e: Exception) {
                logger.error { "Failed to store receipt ${receipt.id}. Will be rolling back all changes" }
                connection.rollback()
                throw e
            }
        }
    }
}