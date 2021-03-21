package com.mao.constraintlayoutproject

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val rootLayout = findViewById<LinearLayout>(R.id.root_layout)


        //遍历 注册的 activity 为其添加 跳转 button
        packageManager.getPackageInfo(packageName,PackageManager.GET_ACTIVITIES).activities
                .filterNot { it.name == this :: class.java.name }
                .map { Class.forName(it.name) }
                .forEach{ clazz ->
                    rootLayout.addView(AppCompatButton(this).apply {
                        isAllCaps = false
                        text = clazz.simpleName
                        setOnClickListener {
                            startActivity(Intent(this@MainActivity, clazz))
                        }
                    })
                }
    }
}