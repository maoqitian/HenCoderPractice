package com.mao.leakcanarytest.koom

import android.content.Context
import com.kwai.koom.javaoom.common.KConstants




/**
 *  author : maoqitian
 *  date : 2021/9/7 23:19
 *  description :
 */
class StringLeakMaker :LeakMaker<String>() {

    override fun startLeak(context: Context?) {
        val largeStr = String(ByteArray(KConstants.ArrayThreshold.DEFAULT_BIG_PRIMITIVE_ARRAY + 1))
        uselessObjectList.add(largeStr)
    }
}