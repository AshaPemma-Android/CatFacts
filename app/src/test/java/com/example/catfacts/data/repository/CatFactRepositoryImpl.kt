package com.example.catfacts.data.repository

import com.example.catfacts.data.remote.api.CatFactApi
import com.example.catfacts.data.remote.dto.CatFactDto
import com.example.catfacts.domain.model.CatFact
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CatFactRepositoryImplTest {

    private lateinit var repository: CatFactRepositoryImpl
    private val api: CatFactApi = mockk()

    @Before
    fun setUp() {
        repository = CatFactRepositoryImpl(api)
    }

    @Test
    fun `getCatFact returns mapped CatFact from API`() = runTest {
        val dto = CatFactDto(fact = "Cats sleep 16 hours a day.", length = 30)
        val expected = CatFact(fact = "Cats sleep 16 hours a day.", length = 30)
        coEvery { api.getCatFact() } returns dto
        val result = repository.getCatFact()
        assertEquals(expected, result)
    }
}
