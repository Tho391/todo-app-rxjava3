package com.thomas.apps.todoapprx3.utils.view

import android.content.Context
import android.util.TypedValue
import com.thomas.apps.todoapprx3.R

object ResourceUtils {
    fun Context.getColorOnBackground(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.colorOnBackground, type, true)
        return type.data
    }

    fun Context.getColorSurface(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.colorSurface, type, true)
        return type.data
    }

    fun Context.getColorOnSurface(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.colorOnSurface, type, true)
        return type.data
    }

    fun Context.getColorBackground(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.backgroundColor, type, true)
        return type.data
    }

    fun Context.getColorPrimary(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimary, type, true)
        return type.data
    }

    fun Context.getColorPrimaryVariant(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimaryVariant, type, true)
        return type.data
    }

    fun Context.getColorSecondary(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.colorSecondary, type, true)
        return type.data
    }

    fun Context.getColorSecondaryVariant(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.colorSecondaryVariant, type, true)
        return type.data
    }

    fun Context.getColorError(): Int {
        val type = TypedValue()
        theme.resolveAttribute(R.attr.colorError, type, true)
        return type.data
    }
}