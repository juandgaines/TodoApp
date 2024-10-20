package com.juandgaines.todoapp.data

import android.content.Context
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher

object DataSourceFactory {
    fun createDataSource(context: Context, dispatcher: CoroutineDispatcher): TaskLocalDataSource {
        val database = TodoDatabase.getDatabase(context)
        return RoomTaskLocalDataSource(database.taskDao(), dispatcher)
    }
}