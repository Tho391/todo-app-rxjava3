package com.thomas.apps.todoapprx3.feature_todo.presentation.todos

import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo

sealed class TodosEvent {
    data class DeleteTodo(val todo: Todo) : TodosEvent()
    object Logout : TodosEvent()
}