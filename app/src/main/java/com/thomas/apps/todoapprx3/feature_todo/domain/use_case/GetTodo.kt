package com.thomas.apps.todoapprx3.feature_todo.domain.use_case

import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.TodoRepository

class GetTodo(private val repository: TodoRepository) {

    operator fun invoke(id: Long): Todo? {
        return repository.getTodoById(id)
    }
}