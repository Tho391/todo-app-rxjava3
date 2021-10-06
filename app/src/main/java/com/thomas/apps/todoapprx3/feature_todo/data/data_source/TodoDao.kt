package com.thomas.apps.todoapprx3.feature_todo.data.data_source

import androidx.room.*
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface TodoDao {

    @Query("select * from todo")
    fun getNotes(): Observable<List<Todo>>

    @Query("select * from Todo where id = :id")
    fun getNoteById(id: Long): Single<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Todo): Single<Long>

    @Delete
    fun deleteNote(note: Todo): Completable

    @Query("delete from Todo")
    fun deleteAllNotes(): Completable
}