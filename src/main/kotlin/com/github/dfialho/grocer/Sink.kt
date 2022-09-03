package com.github.dfialho.grocer

interface Sink {
    fun sink(receipt: Receipt)
}