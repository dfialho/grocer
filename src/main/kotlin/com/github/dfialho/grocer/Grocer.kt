package com.github.dfialho.grocer

sealed interface Grocer {
    fun start()
    fun stop()
}