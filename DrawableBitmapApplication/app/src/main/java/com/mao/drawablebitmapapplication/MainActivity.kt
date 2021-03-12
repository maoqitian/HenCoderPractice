package com.mao.drawablebitmapapplication

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //事实上，由于 Bitmap 和 Drawable 是两个不同的概念，因此确切地说它们并不是互
        //相「转换」，⽽是从其中⼀个获得另⼀个的对象：
        //Bitmap -> Drawable：创建⼀个 BitmapDrawable。
        //Drawable -> Bitmap：如果是 BitmapDrawable，使⽤
        //BitmapDrawable.getBitmap() 直接获取；如果不是，创建⼀个 Bitmap
        //和⼀个 Canvas，使⽤ Drawable 通过 Canvas 把内容绘制到 Bitmap 中。
        //可以使用 ktx 中的扩展函数方便转换
        var bitmap = Bitmap.createBitmap(30.dp.toInt(),40.dp.toInt(),Bitmap.Config.ARGB_8888)

        var drawable = bitmap.toDrawable(resources)

        drawable.toBitmap()
    }
}