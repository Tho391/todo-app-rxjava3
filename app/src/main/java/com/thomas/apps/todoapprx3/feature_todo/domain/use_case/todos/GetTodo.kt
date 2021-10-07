package com.thomas.apps.todoapprx3.feature_todo.domain.use_case.todos

import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.TodoRepository
import io.reactivex.rxjava3.core.Single

class GetTodo(private val repository: TodoRepository) {

    operator fun invoke(id: Long): Single<Todo> {
        return repository.getTodoById(id)
    }
}