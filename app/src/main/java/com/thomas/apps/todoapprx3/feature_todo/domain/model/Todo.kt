package com.thomas.apps.todoapprx3.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title: String,
    val content: String,
    val timeStamp: Long,

    @PrimaryKey
    val id: Long? = null,
)

class InvalidTodoException(message: String) : Exception(message)