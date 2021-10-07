package com.thomas.apps.todoapprx3.feature_todo.data.data_source.local

import androidx.room.*
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface TodoDao {

    @Query("select * from todo")
    fun getTodos(): Flowable<List<Todo>>

    @Query("select * from Todo where id = :id")
    fun getTodoById(id: Long): Single<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Todo): Completable

    @Delete
    fun deleteTodo(todo: Todo): Completable

    @Query("delete from Todo")
    fun deleteAllTodos(): Completable
}