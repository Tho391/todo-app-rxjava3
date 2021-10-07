package com.thomas.apps.todoapprx3.feature_todo.data.data_source.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocalCache {
    fun saveToken(token: String?): Completable

    fun getToken(): Single<String>

    fun clearCache(): Completable
}