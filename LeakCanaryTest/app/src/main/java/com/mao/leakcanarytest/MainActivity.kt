package com.mao.leakcanarytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.os.Trace
import android.util.Log
import com.mao.leakcanarytest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Trace.beginSection("onCreate")
        //ViewBinding 方式绑定 view
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tv.setOnClickListener {
            //触发内存泄漏
            LeakTest(this).leak()
            finish()
        }
        SystemClock.sleep(3000)
        Trace.endSection()

    }

    //对象回收会调用 该方法
    protected fun finalize(){
        println("maoqitian  MainActivity finalize......")
    }
}