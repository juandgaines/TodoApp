package com.juandgaines.todoapp.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.google.common.truth.Truth
import com.juandgaines.todoapp.MainActivity
import com.juandgaines.todoapp.data.TaskDao
import com.juandgaines.todoapp.data.TaskEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var taskDao: TaskDao

    @Before
    fun init() {
        hiltRule.inject()
        // Clear the database before each test
        runBlocking {
            taskDao.deleteAllTasks()
        }
    }

    @Test
    fun whenNoTasks_showEmptyState() {
        // Given: No tasks in the database


        // Then: Empty state is shown
        composeTestRule.onNodeWithContentDescription("Empty Tasks State")
            .assertIsDisplayed()
    }

    @Test
    fun whenTasksExist_showTaskList() {
        // Given: A task in the database
        runBlocking {
            val testTask = TaskEntity(
                id = "1",
                title = "Test Task",
                description = "Test Description",
                isCompleted = false,
                category = null,
                date = System.currentTimeMillis()
            )
            taskDao.upsertTask(testTask)
        }


        // Then: Task list is shown with the task
        composeTestRule.onNodeWithContentDescription("Pending Task: Test Task")
            .assertIsDisplayed()
    }

    @Test
    fun whenCreatingNewTask_taskAppearsInList() {

        // When: Click on FAB to create new task
        composeTestRule.onNodeWithContentDescription("Add New Task Button")
            .performClick()


        composeTestRule.waitUntil {
            composeTestRule.onNodeWithContentDescription("Task Screen").isDisplayed()
        }

        // And: Enter task details
        composeTestRule.onNodeWithContentDescription("Task Title Input")
            .performTextInput("New Test Task")
        
        composeTestRule.onNodeWithContentDescription("Task Description Input")
            .performTextInput("New Test Description")

        // And: Save the task
        composeTestRule.onNodeWithContentDescription("Save Task Button")
            .performClick()

        composeTestRule.waitUntil {
            composeTestRule.onNodeWithContentDescription("Home Screen").isDisplayed()
        }

        // Then: Verify task appears in the list
        composeTestRule.onNodeWithText("New Test Task")
            .assertIsDisplayed()


        // And: Verify task was saved in database
        runBlocking {
            val tasks = taskDao.getAllTasks().first()
            Truth.assertThat(tasks.any{ it.title == "New Test Task" }).isTrue()
        }
    }
}