package com.thomas.apps.todoapprx3.utils.view

import android.content.SharedPreferences
import com.google.gson.Gson

object SharedPreferenceUtils {

    inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
        when (T::class) {
            Boolean::class -> return this.getBoolean(key, defaultValue as Boolean) as T
            Float::class -> return this.getFloat(key, defaultValue as Float) as T
            Int::class -> return this.getInt(key, defaultValue as Int) as T
            Long::class -> return this.getLong(key, defaultValue as Long) as T
            String::class -> return this.getString(key, defaultValue as String) as T
            Array::class -> {
                val gSon = Gson()
                val jsonText = this.getString(key, null)
                val array = if (jsonText != null) {
                    gSon.fromJson(
                        jsonText,
                        Array<String>::class.java
                    )
                } else {
                    emptyArray()
                }
                return array as T
            }
            else -> {
                if (defaultValue is Set<*>) {
                    return this.getStringSet(key, defaultValue as Set<String>) as T
                }
            }
        }

        return defaultValue
    }

    inline fun <reified T> SharedPreferences.put(key: String, value: T): T {
        val editor = this.edit()

        when (T::class) {
            Boolean::class -> editor.putBoolean(key, value as Boolean)
            Float::class -> editor.putFloat(key, value as Float)
            Int::class -> editor.putInt(key, value as Int)
            Long::class -> editor.putLong(key, value as Long)
            String::class -> editor.putString(key, value as String)
            Array::class -> {
                val values = value as Array<*>
                val gSon = Gson()
                val jsonText = gSon.toJson(values)
                editor.putString(key, jsonText)
            }
            else -> {
                if (value is Set<*>) {
                    editor.putStringSet(key, value as Set<String>)
                }
            }
        }

        editor.apply()
        return value
    }

    fun SharedPreferences.clear() {
        val editor = this.edit()
        editor.clear()
        editor.apply()
    }
}