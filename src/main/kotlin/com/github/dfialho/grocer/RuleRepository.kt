package com.github.dfialho.grocer

import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.sql.DataSource

@ApplicationScoped
class RuleRepository(@Suppress("CdiInjectionPointsInspection") private val dataSource: DataSource) {

    fun all(): List<Category> {
        return emptyList()
    }

    fun create(category: Category) {

    }

    fun delete(categoryId: UUID) {

    }
}
