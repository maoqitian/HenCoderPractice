package com.mao.leakcanarytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mao.leakcanarytest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ViewBinding 方式绑定 view
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tv.setOnClickListener {
            //触发内存泄漏
            LeakTest(this).leak()
            finish()
        }

    }

    //对象回收会调用 该方法
    protected fun finalize(){
        println("maoqitian  MainActivity finalize......")
    }
}