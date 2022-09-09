package com.github.dfialho.grocer

import java.sql.ResultSet

fun <T> ResultSet.map(extract: (ResultSet) -> T): List<T> {
    val items = mutableListOf<T>()
    while (this.next()) {
        items.add(extract(this))
    }
    return items
}