package com.thomas.apps.todoapprx3.feature_todo.domain.use_case

import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.TodoRepository
import io.reactivex.rxjava3.core.Flowable

class GetTodos(private val repository: TodoRepository) {

    operator fun invoke(): Flowable<List<Todo>> {
        return repository.getTodos()
    }
}