package com.mao.leakcanarytest.koom

import android.content.Context
import android.graphics.Bitmap
import com.kwai.koom.javaoom.common.KConstants

/**
 *  author : maoqitian
 *  date : 2021/9/7 23:30
 *  description :
 */
class BitmapLeakMaker :LeakMaker<Bitmap>() {
    override fun startLeak(context: Context?) {
        val bitmap = Bitmap.createBitmap(
            KConstants.BitmapThreshold.DEFAULT_BIG_WIDTH+1,
            KConstants.BitmapThreshold.DEFAULT_BIG_HEIGHT+1,Bitmap.Config.ARGB_8888)
        uselessObjectList.add(bitmap)
    }
}