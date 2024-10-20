package com.juandgaines.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.presentation.screens.home.SectionTitle
import com.juandgaines.todoapp.presentation.screens.home.SummaryInfo
import com.juandgaines.todoapp.presentation.screens.home.TaskItem
import com.juandgaines.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme() {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        SummaryInfo(
                            modifier = Modifier.fillMaxWidth().padding(innerPadding),
                            date = "March 9, 2024",
                            tasksSummary = "5 incomplete, 5 completed"
                        )
                        SectionTitle(
                            modifier = Modifier.fillMaxWidth(),
                            title = "Completed"
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp).background(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = RoundedCornerShape(16.dp)
                            )
                        ) {
                            TaskItem(
                                modifier = Modifier.padding(16.dp),
                                onClickItem = {},
                                onDeleteItem = {},
                                onToggleCompletion = {},
                                task = Task(
                                    id = "1",
                                    title = "Task 1",
                                    isCompleted = false,
                                    description = "Description 1",
                                )
                            )
                            TaskItem(
                                modifier = Modifier.padding(16.dp),
                                onClickItem = {},
                                onDeleteItem = {},
                                onToggleCompletion = {},
                                task = Task(
                                    id = "1",
                                    title = "Task 1",
                                    isCompleted = false,
                                    description = "Description 1",
                                )
                            )
                        }
                        SectionTitle(
                            modifier = Modifier.fillMaxWidth(),
                            title = "Uncompleted"
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp).background(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = RoundedCornerShape(16.dp)
                            )
                        ) {
                            TaskItem(
                                modifier = Modifier.padding(16.dp),
                                onClickItem = {},
                                onDeleteItem = {},
                                onToggleCompletion = {},
                                task = Task(
                                    id = "1",
                                    title = "Task 1",
                                    isCompleted = true,
                                    description = "Description 1",
                                )
                            )
                        }

                    }
                }
            }
        }
    }
}

