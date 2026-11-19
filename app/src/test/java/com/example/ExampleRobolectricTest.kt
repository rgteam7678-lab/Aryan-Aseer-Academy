package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.AcademicStream
import com.example.data.model.EducationBoard
import com.example.data.model.StudentProfile
import com.example.data.model.SubjectData
import com.example.ui.screens.LearnHomeScreen
import com.example.ui.theme.ShikshaTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "w411dp-h891dp-xxhdpi")
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Aryan Aseer Academy", appName)
    }

    @Test
    fun `grade grid switches class and opens Mathematics and Urdu primary buttons`() {
        var selectedClassCallback = 10
        val openedSubjects = mutableListOf<SubjectData>()

        composeTestRule.setContent {
            ShikshaTheme(dynamicColor = false) {
                LearnHomeScreen(
                    profile = StudentProfile(
                        id = "test_uid",
                        userId = "test_uid",
                        displayName = "Aryan",
                        selectedClass = 10L,
                        stream = AcademicStream.GENERAL.name,
                        board = EducationBoard.CBSE.name,
                    ),
                    progressList = emptyList(),
                    pendingHomeworkCount = 0,
                    onSelectClass = { selectedClassCallback = it },
                    onSelectStream = {},
                    onOpenSubject = { openedSubjects.add(it) },
                    onOpenChapter = {},
                    onOpenFormulaVault = {},
                    onOpenQuizzesTab = {},
                )
            }
        }

        composeTestRule.waitForIdle()

        // Verify all 6 grade grid tiles (7..12) and both primary subject buttons are rendered
        for (grade in 7..12) {
            composeTestRule.onNodeWithTag("grade_grid_tile_$grade").assertIsDisplayed()
        }
        composeTestRule.onNodeWithTag("primary_math_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("primary_urdu_button").assertIsDisplayed()

        // Select Grade 8 from the grid and open Mathematics
        composeTestRule.onNodeWithTag("grade_grid_tile_8").performClick()
        composeTestRule.waitForIdle()
        assertEquals(8, selectedClassCallback)

        composeTestRule.onNodeWithTag("primary_math_button").performClick()
        composeTestRule.waitForIdle()
        assertEquals("c8_math", openedSubjects.last().id)

        // Select Grade 12 from the grid and open Urdu
        composeTestRule.onNodeWithTag("grade_grid_tile_12").performClick()
        composeTestRule.waitForIdle()
        assertEquals(12, selectedClassCallback)

        composeTestRule.onNodeWithTag("primary_urdu_button").performClick()
        composeTestRule.waitForIdle()
        assertEquals("c12_urdu", openedSubjects.last().id)
    }
}
