package com.thomas.apps.todoapprx3.feature_todo.data.repository

import com.thomas.apps.todoapprx3.feature_todo.data.data_source.local.LocalCache
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.local.TodoDao
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.remote.TodosApi
import com.thomas.apps.todoapprx3.feature_todo.domain.model.User
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.LoginRepository
import com.thomas.apps.todoapprx3.feature_todo.domain.utils.Resource
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit

class LoginRepositoryImpl(
    private val todosApi: TodosApi,
    private val localCache: LocalCache,
    private val todoDao: TodoDao,
) : LoginRepository {
    override fun login(username: String, password: String): Flowable<Resource<User>> {
        return Flowable.create({ emitter ->
            emitter.onNext(Resource.Loading())
            todosApi.login(username, password)
                .delay(2, TimeUnit.SECONDS)
                .subscribe(
                    { user ->
                        localCache.saveToken(user.token)
                            .subscribe(
                                {
                                    emitter.onNext(Resource.Success(user))
                                    emitter.onComplete()
                                }, {
                                    emitter.onError(it)
                                }
                            )
                    }, {
                        emitter.onError(it)
                    }
                )
        }, BackpressureStrategy.BUFFER)
    }

    override fun logout(): Completable {
        //call api logout and clear cache
        val deleteCache = localCache.clearCache()
        val deleteDb = todoDao.deleteAllTodos()

        return Completable.mergeArray(deleteCache, deleteDb)
    }
}