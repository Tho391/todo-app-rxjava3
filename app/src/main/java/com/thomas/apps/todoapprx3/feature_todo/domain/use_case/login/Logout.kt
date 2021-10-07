package com.thomas.apps.todoapprx3.feature_todo.domain.use_case.login

import com.thomas.apps.todoapprx3.feature_todo.domain.repository.LoginRepository
import io.reactivex.rxjava3.core.Completable

class Logout(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(): Completable {
        return loginRepository.logout()
    }
}