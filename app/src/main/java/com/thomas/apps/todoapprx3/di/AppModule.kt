package com.thomas.apps.todoapprx3.di

import android.app.Application
import androidx.room.Room
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.local.LocalCache
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.local.LocalCacheImpl
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.local.TodoDatabase
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.remote.TodosApi
import com.thomas.apps.todoapprx3.feature_todo.data.repository.LoginRepositoryImpl
import com.thomas.apps.todoapprx3.feature_todo.data.repository.TodoRepositoryImpl
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.LoginRepository
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.TodoRepository
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.login.Login
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.login.LoginUseCases
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.login.Logout
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.todos.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.todoDao)
    }

    @Provides
    @Singleton
    fun provideTodoUseCases(repository: TodoRepository): TodoUseCases {
        return TodoUseCases(
            getTodos = GetTodos(repository),
            getTodo = GetTodo(repository),
            addTodo = AddTodo(repository),
            deleteTodo = DeleteTodo(repository)
        )
    }

    @Provides
    @Singleton
    fun provideTodoApi(): TodosApi {
        return TodosApi()
    }

    @Provides
    @Singleton
    fun provideLocalCache(app: Application): LocalCache {
        return LocalCacheImpl(app)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        todosApi: TodosApi,
        localCache: LocalCache,
        todoDatabase: TodoDatabase
    ): LoginRepository {
        return LoginRepositoryImpl(
            todosApi = todosApi,
            localCache = localCache,
            todoDao = todoDatabase.todoDao
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(repository: LoginRepository): LoginUseCases {
        return LoginUseCases(
            login = Login(repository),
            logout = Logout(repository)
        )
    }
}