package com.example.catfacts.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class CatFactContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun displaysProvidedFactText() {
        val fact = "Cats purr when they are happy"

        composeRule.setContent {
            MaterialTheme {
                CatFactContent(
                    factText = fact,
                    onFetchClick = {}
                )
            }
        }

        composeRule.onNodeWithText(fact).assertExists()
    }

    @Test
    fun fetchButton_isClickable() {
        var clicked = false
        composeRule.setContent {
            MaterialTheme {
                CatFactContent(
                    factText = "Some cat fact",
                    onFetchClick = { clicked = true }
                )
            }
        }

        composeRule.onNodeWithText("Get New Fact").performClick()
        assert(clicked)
    }
}
