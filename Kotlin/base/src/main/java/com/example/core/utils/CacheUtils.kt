package com.example.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.core.BaseApplication
import com.example.core.R


@SuppressLint("StaticFieldLeak")
object CacheUtils {

    private val context = BaseApplication.currentApplication
    private val SP = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    fun save(key: String?, value: String?)= SP.edit().putString(key, value).apply()

    operator fun get(key: String?) = SP.getString(key, null)
}