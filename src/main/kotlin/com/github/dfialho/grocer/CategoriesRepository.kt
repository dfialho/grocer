package com.github.dfialho.grocer

import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.sql.DataSource

@ApplicationScoped
class CategoriesRepository(@Suppress("CdiInjectionPointsInspection") private val dataSource: DataSource) {

    fun all(): List<Category> {
        dataSource.connection.use { connection ->
            return connection.prepareStatement("SELECT * FROM categories")
                .executeQuery()
                .map {
                    Category(
                        id = it.getObject("id") as UUID,
                        name = it.getString("name")
                    )
                }
        }
    }

    fun create(category: Category) {
        dataSource.connection.use { connection ->
            with(connection.prepareStatement("INSERT INTO categories VALUES (?, ?)")) {
                setObject(1, category.id)
                setString(2, category.name)
                executeUpdate()
            }
        }
    }

    fun delete(categoryId: UUID) {
        dataSource.connection.use { connection ->
            with(connection.prepareStatement("DELETE FROM categories WHERE id = ?")) {
                setString(1, categoryId.toString())
                executeUpdate()
            }
        }
    }

    fun createSubCategory(subCategory: SubCategory) {

        dataSource.connection.use { connection ->
            with(connection.prepareStatement("INSERT INTO subcategories VALUES (?, ?, ?)")) {
                setObject(1, subCategory.id)
                setObject(2, subCategory.categoryId)
                setString(3, subCategory.name)
                executeUpdate()
            }
        }
    }

    fun getCategorySubcategories(categoryId: UUID): List<SubCategory> {

        // TODO check if category exists, if not throw not found or find another representation
        //      like putting the subcategories as a json under categories
        dataSource.connection.use { connection ->
            with(connection.prepareStatement("SELECT * FROM subcategories WHERE category_id = ?")) {

                setObject(1, categoryId)
                return executeQuery().map {
                    SubCategory(
                        id = it.getObject("id") as UUID,
                        categoryId = it.getObject("category_id") as UUID,
                        name = it.getString("name")
                    )
                }
            }
        }
    }
}
