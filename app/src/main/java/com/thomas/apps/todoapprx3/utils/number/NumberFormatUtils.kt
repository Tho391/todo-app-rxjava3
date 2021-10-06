package com.thomas.apps.todoapprx3.utils.number

import timber.log.Timber
import java.text.NumberFormat

object NumberFormatUtils {

    fun Double.formatNumber(fractionDigits: Int = 2): String? {
        val format = NumberFormat.getInstance()
        format.isParseIntegerOnly = true
        format.maximumFractionDigits = fractionDigits
        return try {
            format.format(this)
        } catch (e: Exception) {
            Timber.e(e.message)
            null
        }
    }

    fun Float.formatNumber(fractionDigits: Int = 2): String? {
        val format = NumberFormat.getInstance()
        format.isParseIntegerOnly = true
        format.maximumFractionDigits = fractionDigits
        return try {
            format.format(this)
        } catch (e: Exception) {
            Timber.e(e.message)
            null
        }
    }
}