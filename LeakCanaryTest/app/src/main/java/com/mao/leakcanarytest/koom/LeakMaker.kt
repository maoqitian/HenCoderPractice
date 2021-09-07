package com.mao.leakcanarytest.koom

import android.content.Context


/**
 *  author : maoqitian
 *  date : 2021/9/7 23:14
 *  description :
 */
abstract class LeakMaker<T> {

    var uselessObjectList: ArrayList<T> = ArrayList()
    abstract fun startLeak(context: Context?)

    companion object {
        private val leakMakerList: MutableList<LeakMaker<*>> = ArrayList()
        fun makeLeak(context: Context?) {
            leakMakerList.add(ActivityLeakMaker())
            leakMakerList.add(BitmapLeakMaker())
            leakMakerList.add(ByteArrayLeakMaker())
            leakMakerList.add(FragmentLeakMaker())
            leakMakerList.add(StringLeakMaker())
            for (leakMaker in leakMakerList) {
                leakMaker.startLeak(context)
            }
        }
    }
}