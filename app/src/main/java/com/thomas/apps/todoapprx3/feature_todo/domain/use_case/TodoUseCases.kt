package com.thomas.apps.todoapprx3.feature_todo.domain.use_case

data class TodoUseCases(
    val getTodos: GetTodos,
    val getTodo: GetTodo,
    val addTodo: AddTodo,
    val deleteTodo: DeleteTodo,
)