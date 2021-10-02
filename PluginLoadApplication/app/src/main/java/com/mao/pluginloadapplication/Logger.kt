package com.mao.pluginloadapplication

import android.util.Log

/**
 *  author : maoqitian
 *  date : 2021/10/1 16:25
 *  description :
 */
class Logger {
    companion object {
        private const val TAG = "PLUGIN_LOAD"
        fun d (msg: String) {
            Log.d(TAG, msg)
        }
    }
}