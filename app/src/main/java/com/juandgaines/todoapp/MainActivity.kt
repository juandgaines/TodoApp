package com.juandgaines.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.juandgaines.todoapp.presentation.screens.home.HomeDataState
import com.juandgaines.todoapp.presentation.screens.home.HomeScreen
import com.juandgaines.todoapp.presentation.screens.home.providers.completedTask
import com.juandgaines.todoapp.presentation.screens.home.providers.pendingTask
import com.juandgaines.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme() {
                HomeScreen(
                    state = HomeDataState(
                        date = "March 9, 2024",
                        summary = "5 incomplete, 5 completed",
                        completedTask = completedTask,
                        pendingTask = pendingTask
                    )
                )
            }
        }
    }
}

