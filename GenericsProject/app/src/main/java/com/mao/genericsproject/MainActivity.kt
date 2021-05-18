package com.mao.genericsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Printer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Looper.getMainLooper().setMessageLogging(Printer {

        })

        val map = HashMap<String,String>()
    }
}