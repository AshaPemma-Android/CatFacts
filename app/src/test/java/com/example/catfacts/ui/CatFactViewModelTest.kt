package com.example.catfacts.ui

import app.cash.turbine.test
import com.example.catfacts.domain.model.CatFact
import com.example.catfacts.domain.usecase.GetCatFactUseCase
import com.example.catfacts.util.TestDispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CatFactViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val testDispatcher = dispatcherRule.testDispatcher

    private val getCatFactUseCase: GetCatFactUseCase = mockk()
    private val dispatcherProvider = TestDispatcherProvider(testDispatcher)

    @Test
    fun `getCatFact emits Success when use case returns fact`() = runTest {
        val expectedFact = CatFact("Cats sleep 70% of their lives", 31)
        coEvery { getCatFactUseCase() } returns expectedFact

        val viewModel = CatFactViewModel(getCatFactUseCase, dispatcherProvider)

        viewModel.uiState.test {
            assertEquals(CatFactUiState.Idle, awaitItem())
            viewModel.getCatFact()
            assertEquals(CatFactUiState.Loading, awaitItem())

            val success = awaitItem()
            assertTrue(success is CatFactUiState.Success)
            assertEquals(expectedFact.fact, (success as CatFactUiState.Success).fact)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCatFact emits Error when use case throws`() = runTest {
        coEvery { getCatFactUseCase() } throws RuntimeException("API error")

        val viewModel = CatFactViewModel(getCatFactUseCase, dispatcherProvider)

        viewModel.uiState.test {
            assertEquals(CatFactUiState.Idle, awaitItem())
            viewModel.getCatFact()
            assertEquals(CatFactUiState.Loading, awaitItem())

            val error = awaitItem()
            assertTrue(error is CatFactUiState.Error)
            assertEquals("Failed to load cat fact.", (error as CatFactUiState.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
