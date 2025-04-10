package com.juandgaines.todoapp.presentation.screens.home

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.juandgaines.todoapp.FakeTaskLocalDataSource
import com.juandgaines.todoapp.domain.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeScreenViewModelTest {

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var fakeDataSource: FakeTaskLocalDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set the main dispatcher to our test dispatcher
        Dispatchers.setMain(testDispatcher)
        
        fakeDataSource = FakeTaskLocalDataSource
        viewModel = HomeScreenViewModel(
            savedStateHandle = SavedStateHandle(),
            taskLocalDataSource = fakeDataSource
        )
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `when initialized, state should have current date`() = runTest {
        // Given: Current date
        val currentDate = LocalDate.now()
        val expectedDate = DateTimeFormatter.ofPattern("EEEE, MMMM dd yyyy").format(currentDate)

        // Then: State should have current date
        assertThat(viewModel.state.date).isEqualTo(expectedDate)
    }

    @Test
    fun `when tasks are added, state should update with correct counts`() = runTest {
        // Given: A pending task
        fakeDataSource.deleteAllTasks()
        val pendingTask = Task(
            id = "1",
            title = "Pending Task",
            description = "Description",
            isCompleted = false,
            category = null,
            date = LocalDateTime.now()
        )


        // When: Task is added
        fakeDataSource.addTask(pendingTask)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: State should update with correct counts
        assertThat(viewModel.state.summary).isEqualTo("1")
        assertThat(viewModel.state.pendingTask).hasSize(1)
        assertThat(viewModel.state.completedTask).isEmpty()
    }

    @Test
    fun `when task is toggled, it should move between pending and completed lists`() = runTest {
        // Given: A task
        fakeDataSource.deleteAllTasks()
        val task = Task(
            id = "1",
            title = "Test Task",
            description = "Description",
            isCompleted = false,
            category = null,
            date = LocalDateTime.now()
        )
        fakeDataSource.addTask(task)
        testDispatcher.scheduler.advanceUntilIdle()

        // When: Task is toggled to completed
        viewModel.onAction(HomeScreenAction.OnToggleTask(task))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: Task should be in completed list
        assertThat(viewModel.state.completedTask).hasSize(1)
        assertThat(viewModel.state.pendingTask).isEmpty()
        assertThat(viewModel.state.summary).isEqualTo("0")

        // When: Task is toggled back to pending
        val completedTask = viewModel.state.completedTask.first()
        viewModel.onAction(HomeScreenAction.OnToggleTask(completedTask))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: Task should be back in pending list
        assertThat(viewModel.state.pendingTask).hasSize(1)
        assertThat(viewModel.state.completedTask).isEmpty()
        assertThat(viewModel.state.summary).isEqualTo("1")
    }

    @Test
    fun `when task is deleted, it should be removed from state`() = runTest {
        // Given: A task
        fakeDataSource.deleteAllTasks()
        val task = Task(
            id = "1",
            title = "Test Task",
            description = "Description",
            isCompleted = false,
            category = null,
            date = LocalDateTime.now()
        )
        fakeDataSource.addTask(task)
        testDispatcher.scheduler.advanceUntilIdle()

        // When: Task is deleted
        viewModel.onAction(HomeScreenAction.OnDeleteTask(task))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: Task should be removed from state
        assertThat(viewModel.state.pendingTask).isEmpty()
        assertThat(viewModel.state.completedTask).isEmpty()
        assertThat(viewModel.state.summary).isEqualTo("0")
    }

    @Test
    fun `when all tasks are deleted, state should be empty`() = runTest {
        // Given: Multiple tasks
        fakeDataSource.deleteAllTasks()
        val task1 = Task(
            id = "1",
            title = "Task 1",
            description = "Description",
            isCompleted = false,
            category = null,
            date = LocalDateTime.now()
        )
        val task2 = Task(
            id = "2",
            title = "Task 2",
            description = "Description",
            isCompleted = true,
            category = null,
            date = LocalDateTime.now()
        )
        fakeDataSource.addTask(task1)
        fakeDataSource.addTask(task2)
        testDispatcher.scheduler.advanceUntilIdle()

        // When: All tasks are deleted
        viewModel.onAction(HomeScreenAction.OnDeleteAllTasks)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: State should be empty
        assertThat(viewModel.state.pendingTask).isEmpty()
        assertThat(viewModel.state.completedTask).isEmpty()
        assertThat(viewModel.state.summary).isEqualTo("0")
    }
} 