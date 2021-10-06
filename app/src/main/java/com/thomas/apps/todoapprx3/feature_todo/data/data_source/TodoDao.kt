package com.thomas.apps.todoapprx3.feature_todo.data.data_source

import androidx.room.*
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import io.reactivex.rxjava3.core.Observable

@Dao
interface TodoDao {

    @Query("select * from todo")
    fun getTodos(): Observable<List<Todo>>

    @Query("select * from Todo where id = :id")
    fun getTodoById(id: Long): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("delete from Todo")
    fun deleteAllTodos()
}