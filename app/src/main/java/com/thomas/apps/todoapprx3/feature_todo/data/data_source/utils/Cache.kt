package com.thomas.apps.todoapprx3.feature_todo.data.data_source.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.thomas.apps.todoapprx3.utils.view.SharedPreferenceUtils.clear
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

object Cache {
    private val TOKEN_KEY = "TOKEN_KEY"
    private val SETTING = "SETTING"

    private fun Context.sharePreference(): SharedPreferences? {
        return getSharedPreferences(SETTING, Context.MODE_PRIVATE)
    }

    fun Context.saveToken(token: String?): Completable {
        return Completable.create { emitter ->
            val sharedPreferences = sharePreference()
            if (sharedPreferences != null) {
                sharedPreferences.edit {
                    putString(TOKEN_KEY, token)
                    apply()
                }
                emitter.onComplete()
            } else {
                emitter.onError(LocalCacheException("Cannot save token"))
            }

        }

    }

    fun Context.getToken(): Single<String> {
        return Single.create { emitter ->
            val token = sharePreference()?.getString(TOKEN_KEY, null)
            if (token != null) {
                emitter.onSuccess(token)
            } else {
                emitter.onError(NoTokenException("Token is not exist"))
            }
        }
    }

    fun Context.clearCache(): Completable {
        return Completable.create { emitter ->
            val sharePref = sharePreference()
            if (sharePref != null) {
                sharePref.clear()
                emitter.onComplete()
            } else {
                emitter.onError(LocalCacheException("Cannot clear cache"))
            }
        }
    }

    class NoTokenException(msg: String) : Exception(msg)
    class LocalCacheException(msg: String) : Exception(msg)
}