package com.mao.leakcanarytest.koom

import android.content.Context
import com.kwai.koom.javaoom.common.KConstants




/**
 *  author : maoqitian
 *  date : 2021/9/7 23:33
 *  description :
 */
class ByteArrayLeakMaker : LeakMaker<ByteArrayLeakMaker.ByteArrayHolder>() {




    inner class ByteArrayHolder {
        private val array: ByteArray = ByteArray(KConstants.ArrayThreshold.DEFAULT_BIG_PRIMITIVE_ARRAY + 1)

    }

    override fun startLeak(context: Context?) {
        uselessObjectList.add(ByteArrayHolder())
    }
}