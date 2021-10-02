package com.mao.plugin

import android.os.Bundle
import android.widget.Toast
import com.mao.dynamicload.BasePluginActivity

class PluginActivity : BasePluginActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this,"插件PluginActivity调用",Toast.LENGTH_LONG).show()
    }
}