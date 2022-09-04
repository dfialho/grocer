package com.github.dfialho.grocer

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = NameContainsCondition::class, name = "NameContains"),
)
interface Condition {
    fun matches(item: UnlabeledItem): Boolean
}
