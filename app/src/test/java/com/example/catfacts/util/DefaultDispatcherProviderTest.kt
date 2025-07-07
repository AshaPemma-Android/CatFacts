package com.example.catfacts.util

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import org.junit.Test

class DefaultDispatcherProviderTest {

    private val provider = DefaultDispatcherProvider()

    @Test
    fun `test io dispatcher is Dispatchers IO`() {
        assertEquals(Dispatchers.IO, provider.io())
    }

    @Test
    fun `test main dispatcher is Dispatchers Main`() {
        assertEquals(Dispatchers.Main, provider.main())
    }
}
