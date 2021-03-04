package com.example.core.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.core.BaseApplication
import com.example.core.R

object CacheUtils {
    @SuppressLint("StaticFieldLeak")
    private val context = BaseApplication.currentApplication()
    private val SP = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    fun save(key: String?, value: String?) {
        SP.edit().putString(key, value).apply()
    }

    operator fun get(key: String?): String? {
        return SP.getString(key, null)
    }
}