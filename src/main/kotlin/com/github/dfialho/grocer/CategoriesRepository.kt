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

    fun createSubCategory(subCategory: SubCategory) {

        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("INSERT INTO subcategories VALUES (?, ?, ?)")
            ps.setObject(1, subCategory.id)
            ps.setObject(2, subCategory.categoryId)
            ps.setString(3, subCategory.name)
            ps.executeUpdate()
        }
    }

    fun getCategorySubcategories(categoryId: UUID): List<SubCategory> {

        // TODO check if category exists, if not throw not found or find another representation
        //      like putting the subcategories as a json under categories
        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("SELECT * FROM subcategories WHERE category_id = ?")
            ps.setObject(1, categoryId)

            val resultSet = ps.executeQuery()
            val subCategories = mutableListOf<SubCategory>()
            while (resultSet.next()) {
                subCategories.add(
                    SubCategory(
                        id = resultSet.getObject("id") as UUID,
                        categoryId = resultSet.getObject("category_id") as UUID,
                        name = resultSet.getString("name")
                    )
                )
            }
            return subCategories
        }
    }
}
