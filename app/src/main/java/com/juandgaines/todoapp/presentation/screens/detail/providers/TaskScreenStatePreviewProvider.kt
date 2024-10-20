package com.juandgaines.todoapp.presentation.screens.detail.providers

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.juandgaines.todoapp.domain.Category.OTHER
import com.juandgaines.todoapp.domain.Category.WORK
import com.juandgaines.todoapp.presentation.screens.detail.TaskScreenState

class TaskScreenStatePreviewProvider:PreviewParameterProvider<TaskScreenState> {
    override val values: Sequence<TaskScreenState>
        get() = sequenceOf(
            TaskScreenState(
                taskName = TextFieldState("Task 1"),
                taskDescription = TextFieldState("Description 1"),
                isTaskDone = false,
                category = WORK
            ),
            TaskScreenState(
                taskName = TextFieldState("Task 2"),
                taskDescription = TextFieldState("Description 2"),
                isTaskDone = true,
                category = WORK
            ),
            TaskScreenState(
                taskName = TextFieldState("Task 3"),
                taskDescription = TextFieldState("Description 3"),
                isTaskDone = false,
                category = OTHER
            ),
            TaskScreenState(
                taskName = TextFieldState("Task 4"),
                taskDescription = TextFieldState("Description 4"),
                isTaskDone = true,
                category = null
            ),
            TaskScreenState(
                taskName = TextFieldState("Task 5"),
                taskDescription = TextFieldState(""),
                isTaskDone = false,
                category = null
            )
        )
}