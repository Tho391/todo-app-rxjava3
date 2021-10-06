package com.thomas.apps.todoapprx3.feature_todo.data.repository

import com.thomas.apps.todoapprx3.feature_todo.data.data_source.TodoDao
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.TodoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {
    override fun getTodos(): Flowable<List<Todo>> {
        return dao.getTodos()
    }

    override fun getTodoById(id: Long): Single<Todo> {
        return dao.getTodoById(id)
    }

    override fun insertTodo(todo: Todo): Completable {
       return dao.insertTodo(todo)
    }

    override fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override fun deleteAllTodos() {
        dao.deleteAllTodos()
    }
}