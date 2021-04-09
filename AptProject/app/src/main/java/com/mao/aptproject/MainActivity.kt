package com.mao.aptproject

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mao.lib_annotation.BindView
import com.mao.library_binging.ViewBing


class MainActivity : AppCompatActivity() {

    @BindView(R.id.text)
    lateinit var textView :TextView
    @BindView(R.id.cLayout)
    lateinit var constraintLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //使用反射的方式来调用 apt 生成的View绑定类的构造方法，构造方法中写入了绑定代码

        ViewBing.bind(this)

        constraintLayout.setBackgroundColor(Color.DKGRAY)
        textView.text = "adadafaaa"
    }
}