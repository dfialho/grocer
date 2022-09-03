package com.github.dfialho.grocer.continenteonline.rules

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.github.dfialho.grocer.continenteonline.OrderItem

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = NameContainsRule::class, name = "NameContains"),
    JsonSubTypes.Type(value = CategoryContainsRule::class, name = "CategoryContains")
)
interface Rule {
    val result: RuleResult
    fun matches(orderItem: OrderItem): Boolean
}
