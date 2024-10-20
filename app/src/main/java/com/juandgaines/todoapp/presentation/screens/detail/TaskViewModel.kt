package com.juandgaines.todoapp.presentation.screens.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.toRoute
import com.juandgaines.todoapp.TodoApplication
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import com.juandgaines.todoapp.presentation.navigation.TaskScreenDes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel (
    savedStateHandle: SavedStateHandle,
    private val localDataSource: TaskLocalDataSource
): ViewModel() {


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
                localDataSource.getTaskById(taskData.taskId)?.let { task ->
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
                        this@TaskViewModel.localDataSource.updateTask(
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
                        localDataSource.addTask(
                            task = task
                        )
                    }
                    eventChannel.send(TaskEvent.TaskCreated)
                }
                else -> Unit
            }
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory  {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                    val application = checkNotNull(extras[APPLICATION_KEY])
                    val savedStateHandle = extras.createSavedStateHandle()
                    val dataSource = (application as TodoApplication).dataSource
                    return TaskViewModel(savedStateHandle,dataSource) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}