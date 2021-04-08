package com.mao.aptproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mao.lib_annotation.BindView
import com.mao.library_binging.ViewBing


class MainActivity : AppCompatActivity() {

    @BindView(R.id.text)
    lateinit var textView :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //使用反射的方式来处理注解完成 view 的自动绑定

        ViewBing.bind(this)

        textView.text = "adadafaaa"
    }
}