package com.thomas.apps.todoapprx3.feature_todo.presentation.todos

import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo

data class TodoState(
    val todos: List<Todo> = emptyList()
)