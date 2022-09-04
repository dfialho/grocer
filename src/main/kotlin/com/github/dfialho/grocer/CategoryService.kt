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
}