package com.juandgaines.todoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.juandgaines.todoapp.data.FakeTaskLocalDataSource
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.ui.theme.TodoAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme() {
                val fakeDatasource = FakeTaskLocalDataSource

                var text by remember { mutableStateOf("") }
                LaunchedEffect(true) {

                    launch {
                        fakeDatasource.tasksFlow.collect {
                            Log.d("MainActivity", "Tasks: $it")
                            text = it.toString()
                        }
                    }

                    launch {
                        fakeDatasource.addTask(
                            Task(
                                id = "1",
                                title = "Task 1",
                                description = "Description 1"
                            )
                        )
                        fakeDatasource.addTask(
                            Task(
                                id = "2",
                                title = "Task 2",
                                description = "Description 2"
                            )
                        )

                        fakeDatasource.getTaskById("1")?.let {
                            val updatedTask = it.copy(title = "Updated Task 1")
                            fakeDatasource.updateTask(updatedTask)
                        }

                        fakeDatasource.deleteAllTasks()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

