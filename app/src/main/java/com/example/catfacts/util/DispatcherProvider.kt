package com.example.catfacts.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}

class DefaultDispatcherProvider : DispatcherProvider {
    override fun io() = Dispatchers.IO
    override fun main() = Dispatchers.Main
}