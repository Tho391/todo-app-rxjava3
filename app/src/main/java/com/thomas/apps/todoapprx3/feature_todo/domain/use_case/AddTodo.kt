package com.thomas.apps.todoapprx3.feature_todo.domain.use_case

import com.thomas.apps.todoapprx3.feature_todo.domain.model.InvalidTodoException
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.TodoRepository

class AddTodo(
    private val repository: TodoRepository
) {
    @Throws(InvalidTodoException::class)
    operator fun invoke(todo: Todo) {
        if (todo.title.isBlank()) {
            throw  InvalidTodoException("The title can't be empty")
        }
        if (todo.content.isBlank()) {
            throw  InvalidTodoException("The content can't be empty")
        }
        repository.insertTodo(todo)
    }
}