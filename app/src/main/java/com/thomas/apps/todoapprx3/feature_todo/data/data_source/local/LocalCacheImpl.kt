package com.thomas.apps.todoapprx3.feature_todo.data.data_source.local

import android.app.Application
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.utils.Cache.clearCache
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.utils.Cache.getToken
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.utils.Cache.saveToken
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LocalCacheImpl(application: Application) : LocalCache {
    private val context = application.applicationContext

    override fun saveToken(token: String?): Completable {
        return context.saveToken(token)
    }

    override fun getToken(): Single<String> {
        return context.getToken()
    }


    override fun clearCache(): Completable {
        return context.clearCache()
    }
}