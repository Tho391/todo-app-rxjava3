package com.thomas.apps.todoapprx3.feature_todo.domain.use_case.login

import com.thomas.apps.todoapprx3.feature_todo.domain.model.User
import com.thomas.apps.todoapprx3.feature_todo.domain.repository.LoginRepository
import com.thomas.apps.todoapprx3.feature_todo.domain.utils.Resource
import io.reactivex.rxjava3.core.Flowable

class Login(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(username: String, password: String): Flowable<Resource<User>> {
        return loginRepository.login(username, password)
    }
}