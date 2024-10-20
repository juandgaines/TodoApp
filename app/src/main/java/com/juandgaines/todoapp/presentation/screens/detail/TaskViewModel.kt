package com.juandgaines.todoapp.presentation.screens.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juandgaines.todoapp.data.FakeTaskLocalDataSource
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.presentation.navigation.TaskScreenDes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel (
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val fakeTaskLocalDataSource = FakeTaskLocalDataSource

    var state by mutableStateOf(TaskScreenState())
        private set

    private var eventChannel = Channel<TaskEvent>()

    val event = eventChannel.receiveAsFlow()
    private val canSaveTask = snapshotFlow { state.taskName.text.toString() }
    private val taskData= savedStateHandle.toRoute<TaskScreenDes>()

    private var editedTask: Task? = null

    init {

        taskData.taskId?.let {
            viewModelScope.launch {
                fakeTaskLocalDataSource.getTaskById(taskData.taskId)?.let { task ->
                    editedTask= task
                    state = state.copy(
                        taskName = TextFieldState(task.title),
                        taskDescription = TextFieldState(task.description?:""),
                        isTaskDone = task.isCompleted,
                        category = task.category
                    )
                }
            }
        }

        canSaveTask.onEach {
            state = state.copy(canSaveTask = it.isNotEmpty())
        }.launchIn(viewModelScope)
    }

    fun onAction(action: ActionTask) {
        viewModelScope.launch {
            when (action) {

                is ActionTask.ChangeTaskCategory -> {
                    state = state.copy(category = action.category)
                }
                is ActionTask.ChangeTaskDone -> {
                    state = state.copy(isTaskDone = action.isTaskDone)
                }
                is ActionTask.SaveTask -> {

                    editedTask?.let {
                        fakeTaskLocalDataSource.updateTask(
                             updatedTask= it.copy(
                                 id = it.id,
                                title = state.taskName.text.toString(),
                                description = state.taskDescription.text.toString(),
                                isCompleted = state.isTaskDone,
                                category = state.category
                            )
                        )
                    }?:run {
                        val task= Task(
                            id =UUID.randomUUID().toString(),
                            title = state.taskName.text.toString(),
                            description = state.taskDescription.text.toString(),
                            isCompleted = state.isTaskDone,
                            category = state.category
                        )
                        fakeTaskLocalDataSource.addTask(
                            task = task
                        )
                    }
                    eventChannel.send(TaskEvent.TaskCreated)
                }
                else -> Unit
            }
        }
    }
}