package com.juandgaines.todoapp.presentation.screens.detail

sealed interface TaskEvent{
    data object TaskCreated: TaskEvent
}