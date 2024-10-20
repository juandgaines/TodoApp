package com.juandgaines.todoapp.presentation.screens.detail.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.juandgaines.todoapp.domain.Category.OTHER
import com.juandgaines.todoapp.domain.Category.WORK
import com.juandgaines.todoapp.presentation.screens.detail.TaskScreenState

class TaskScreenStatePreviewProvider:PreviewParameterProvider<TaskScreenState> {
    override val values: Sequence<TaskScreenState>
        get() = sequenceOf(
            TaskScreenState(
                taskName = "Task 1",
                taskDescription = "Description 1",
                isTaskDone = false,
                category = WORK
            ),
            TaskScreenState(
                taskName = "Task 2",
                taskDescription = "Description 2",
                isTaskDone = true,
                category = WORK
            ),
            TaskScreenState(
                taskName = "Task 3",
                taskDescription = "Description 3",
                isTaskDone = false,
                category = OTHER
            ),
            TaskScreenState(
                taskName = "Task 4",
                taskDescription = "Description 4",
                isTaskDone = true,
                category = null
            ),
            TaskScreenState(
                taskName = "Task 5",
                taskDescription = null,
                isTaskDone = false,
                category = null
            )
        )
}