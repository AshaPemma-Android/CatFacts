package com.example.catfacts.data.remote.api

import com.example.catfacts.data.remote.dto.CatFactDto
import io.mockk.coEvery
import io.mockk.coVerify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatFactApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: CatFactApi

    @Before
    fun setup() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatFactApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getCatFact should parse response correctly`() = runTest {
        val mockJson = """{ "fact": "Cats purr.", "length": 10 }"""
        server.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val response = api.getCatFact()

        val expected = CatFactDto("Cats purr.", 10)
        assertEquals(expected, response)
        assertEquals("Cats purr.", response.fact)
        assertEquals(10, response.length)
    }
}
