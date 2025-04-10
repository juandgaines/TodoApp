package com.juandgaines.todoapp

import android.content.Context
import androidx.room.Room
import com.juandgaines.todoapp.data.RoomTaskLocalDataSource
import com.juandgaines.todoapp.data.TaskDao
import com.juandgaines.todoapp.data.TodoDatabase
import com.juandgaines.todoapp.data.di.DataModule
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class] , replaces =[DataModule::class] )
class TestDataModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext
        context: Context
    ): TodoDatabase {
        return Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        database: TodoDatabase
    ): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskLocalDataSource(
        taskDao: TaskDao,
        @Named("dispatcherIO")
        dispatcherIO: CoroutineDispatcher
    ): TaskLocalDataSource {
        return RoomTaskLocalDataSource(taskDao, dispatcherIO)
    }
}