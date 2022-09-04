package com.github.dfialho.grocer

import io.agroal.api.AgroalDataSource
import mu.KLogging
import java.sql.Date


class DatabaseSink(private val dataSource: AgroalDataSource) : Sink {

    companion object : KLogging()

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

                connection.prepareStatement("INSERT INTO items VALUES (?, ?, ?, ?, ?, ?, ?, ?)").use { ps ->
                    for (item in receipt.items) {
                        ps.setString(1, item.id)
                        ps.setString(2, receipt.id)
                        ps.setString(3, item.category)
                        ps.setString(4, item.subcategory)
                        ps.setString(5, item.name)
                        ps.setLong(6, item.amount)
                        ps.setDouble(7, item.quantity.amount)
                        ps.setString(8, item.quantity.unit)
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