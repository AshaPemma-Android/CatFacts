package com.example.catfacts.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class MainActivityTest {

    private val hiltRule = HiltAndroidRule(this)
    private val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(composeRule)

    @Test
    fun appLaunches_andShowsLoadingState_afterButtonClick() {
        composeRule.onNodeWithTag("fetchButton")
            .assertIsDisplayed()
            .performClick()
        composeRule.onNodeWithTag("loading")
            .assertIsDisplayed()
    }
}
