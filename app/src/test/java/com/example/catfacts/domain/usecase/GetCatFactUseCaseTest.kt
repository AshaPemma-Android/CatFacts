package com.example.catfacts.domain.usecase

import com.example.catfacts.domain.model.CatFact
import com.example.catfacts.domain.repository.CatFactRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCatFactUseCaseTest {
    private lateinit var useCase: GetCatFactUseCase
    private val repository: CatFactRepository = mockk()

    @Before
    fun setup() {
        useCase = GetCatFactUseCase(repository)
    }

    @Test
    fun `invoke returns cat fact from repository`() = runBlocking {
        val expectedFact = CatFact("A cat purrs at 25-150 vibrations per second.", 47)
        coEvery { repository.getCatFact() } returns expectedFact

        val result = useCase()
        assertEquals(expectedFact, result)
    }
}