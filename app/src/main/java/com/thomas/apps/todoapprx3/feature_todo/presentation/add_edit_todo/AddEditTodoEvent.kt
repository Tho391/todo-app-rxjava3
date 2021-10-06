package com.thomas.apps.todoapprx3.feature_todo.presentation.add_edit_todo

sealed class AddEditTodoEvent {
    object SaveTodo : AddEditTodoEvent()
}