package com.github.dfialho.grocer

import java.util.*
import java.util.UUID.randomUUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CategoryService(private val repository: CategoriesRepository) {

    fun getAll(): List<Category> {
        return repository.all()
    }

    fun create(category: SaveCategory): Category {

        val createdCategory = Category(
            id = randomUUID(),
            name = category.name
        )
        repository.create(createdCategory)
        return createdCategory
    }

    fun remove(categoryId: UUID) {
        repository.delete(categoryId)
    }

    fun createSubcategory(categoryId: UUID, subcategory: SaveSubCategory): SubCategory {

        val createdSubCategory = SubCategory(
            id = randomUUID(),
            categoryId = categoryId,
            name = subcategory.name
        )
        repository.createSubCategory(createdSubCategory)
        return createdSubCategory
    }

    fun getCategorySubcategories(categoryId: UUID): List<SubCategory> {
        return repository.getCategorySubcategories(categoryId)
    }
}