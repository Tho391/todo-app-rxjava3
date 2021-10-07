package com.thomas.apps.todoapprx3.feature_todo.data.data_source.remote

import com.thomas.apps.todoapprx3.feature_todo.domain.model.User
import io.reactivex.rxjava3.core.Single

class TodosApi {

    fun login(username: String, password: String): Single<User> {
        return Single.create {
            if (username == "tho" && password == "123456") {
                it.onSuccess(User("Thọ", "tho", "this_is_token"))
            } else {
                it.onError(Throwable("Invalid credentials"))
            }
        }
    }
}