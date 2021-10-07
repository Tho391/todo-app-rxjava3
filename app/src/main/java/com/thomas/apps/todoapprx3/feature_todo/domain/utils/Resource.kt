package com.thomas.apps.todoapprx3.feature_todo.domain.utils

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val msg: String) : Resource<T>()
}