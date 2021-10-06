package com.thomas.apps.todoapprx3.feature_todo.domain.repository

import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface TodoRepository {
    fun getTodos(): Observable<List<Todo>>

    fun getTodoById(id: Long): Todo?

    fun insertTodo(todo: Todo)

    fun deleteTodo(todo: Todo)

    fun deleteAllTodos()
}