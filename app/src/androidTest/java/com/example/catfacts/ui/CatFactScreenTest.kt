package com.example.catfacts.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.catfacts.ui.CatFactUiState.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatFactScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val uiStateFlow = MutableStateFlow<CatFactUiState>(Idle)

    @MockK(relaxed = true)
    lateinit var mockViewModel: ICatFactViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        every { mockViewModel.uiState } returns uiStateFlow
    }

    @Test
    fun showsIdleStateWithInitialText() {
        uiStateFlow.value = Idle

        composeTestRule.setContent {
            CatFactScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("catFactText")
            .assertIsDisplayed()
            .assertTextEquals("Click the button to get a cat fact!")

        composeTestRule.onNodeWithTag("fetchButton")
            .assertIsDisplayed()
    }

    @Test
    fun showsLoadingIndicator() {
        uiStateFlow.value = Loading

        composeTestRule.setContent {
            CatFactScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("loading")
            .assertIsDisplayed()
    }

    @Test
    fun showsSuccessFact() {
        val testFact = "Cats sleep 70% of their lives."
        uiStateFlow.value = Success(testFact)

        composeTestRule.setContent {
            CatFactScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("catFactText")
            .assertIsDisplayed()
            .assertTextEquals(testFact)

        composeTestRule.onNodeWithTag("fetchButton")
            .assertIsDisplayed()
    }

    @Test
    fun showsErrorMessage() {
        val errorMsg = "Something went wrong!"
        uiStateFlow.value = Error(errorMsg)

        composeTestRule.setContent {
            CatFactScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("catFactText")
            .assertIsDisplayed()
            .assertTextEquals(errorMsg)

        composeTestRule.onNodeWithTag("fetchButton")
            .assertIsDisplayed()
    }

    @Test
    fun clickingFetchButtonCallsViewModelMethod() {
        uiStateFlow.value = Idle

        composeTestRule.setContent {
            CatFactScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("fetchButton")
            .performClick()

        verify { mockViewModel.getCatFact() }
    }
}
