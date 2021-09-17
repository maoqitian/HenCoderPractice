package com.mao.multipointerproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Bitmap 加载优化

        //dpi计算

        Log.e("maoqitian","dip xdpi: ${resources.displayMetrics.xdpi}   ydpi: ${resources.displayMetrics.ydpi}")


        //assets 目录不会对图片进行缩放
        val openAvatar = assets.open("avatar.png")
        val decodeStream = BitmapFactory.decodeStream(openAvatar)
        Log.e("maoqitian","bitmap assets size is ${decodeStream.allocationByteCount}")


        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        options.inSampleSize = 2
        val decodeResource = BitmapFactory.decodeResource(resources, R.drawable.avatar,options)
        Log.e("maoqitian","bitmap drawable size is ${decodeResource.allocationByteCount}")

    }
}