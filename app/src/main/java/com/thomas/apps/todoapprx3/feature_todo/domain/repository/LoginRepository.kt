package com.thomas.apps.todoapprx3.feature_todo.domain.repository

import com.thomas.apps.todoapprx3.feature_todo.domain.model.User
import com.thomas.apps.todoapprx3.feature_todo.domain.utils.Resource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface LoginRepository {

    fun login(username: String, password: String): Flowable<Resource<User>>

    fun logout(): Completable
}