package com.juandgaines.todoapp.presentation.screens.home.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.juandgaines.todoapp.domain.Category.OTHER
import com.juandgaines.todoapp.domain.Category.WORK
import com.juandgaines.todoapp.domain.Task

class TaskItemPreviewProvider:PreviewParameterProvider<Task> {
    override val values: Sequence<Task>
        get() = sequenceOf(
            Task(
                id = "1",
                title = "Task 1",
                isCompleted = false,
                description = "Description 1",
                category = WORK,
            ),

            Task(
                id = "2",
                title = "Task 2",
                isCompleted = true,
                description = "Description 2",
                category = OTHER,
            ),
        )
}