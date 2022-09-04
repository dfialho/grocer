package com.github.dfialho.grocer

import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.sql.DataSource

@ApplicationScoped
class CategoriesRepository(@Suppress("CdiInjectionPointsInspection") private val dataSource: DataSource) {

    fun all(): List<Category> {
        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("SELECT * FROM categories")
            val result = ps.executeQuery()

            val categories = mutableListOf<Category>()
            while (result.next()) {
                categories.add(
                    Category(
                        id = result.getObject("id") as UUID,
                        name = result.getString("name")
                    )
                )
            }

            return categories
        }
    }

    fun create(category: Category) {
        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("INSERT INTO categories VALUES (?, ?)")
            ps.setObject(1, category.id)
            ps.setString(2, category.name)
            ps.executeUpdate()
        }
    }

    fun delete(categoryId: UUID) {
        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("DELETE FROM categories WHERE id = ?")
            ps.setString(1, categoryId.toString())
            ps.executeUpdate()
        }
    }
}
