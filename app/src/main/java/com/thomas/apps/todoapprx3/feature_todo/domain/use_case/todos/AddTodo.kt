package com.thomas.apps.todoapprx3.feature_todo.domain.use_case.todos

import com.thomas.apps.todoapprx3.feature_todo.domain.model.InvalidTodoException
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.TodoRepository
import io.reactivex.rxjava3.core.Completable

class AddTodo(
    private val repository: TodoRepository
) {
    @Throws(InvalidTodoException::class)
    operator fun invoke(todo: Todo): Completable {
        if (todo.title.isBlank()) {
            return Completable.error(InvalidTodoException("The title can't be empty"))
        }
        if (todo.content.isBlank()) {
            return Completable.error(InvalidTodoException("The content can't be empty"))
        }
        return repository.insertTodo(todo)
    }
}