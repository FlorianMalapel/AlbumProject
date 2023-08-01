package com.flo.albumproject

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class BaseInstrumentalTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun BasicProcessOneAppLaunched() {
        composeTestRule.apply {
            this.waitUntil(2000) { onAllNodesWithTag("grid_catalog").fetchSemanticsNodes().isNotEmpty() }
            this.onNodeWithTag("grid_catalog").assertIsDisplayed()
            this.waitUntil(2000) { onAllNodesWithTag("grid_catalog_item_1").fetchSemanticsNodes().isNotEmpty() }
            this.onNodeWithTag("grid_catalog_item_1").assertIsDisplayed().performClick()
            this.onNodeWithTag("album_details_cover").assertIsDisplayed()
            this.onNodeWithTag("track_list_item_1").assertIsDisplayed()
            this.onNodeWithTag("back_button").assertIsDisplayed().performClick()
            this.onNodeWithTag("grid_catalog").assertIsDisplayed()
        }
    }
}