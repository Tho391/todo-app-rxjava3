package com.thomas.apps.todoapprx3.feature_todo.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo

@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "todos.db"
    }
}