package com.mao.customviewanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * 测试 View 绘制 硬件加速和软件加速 堆栈调用
 */
class TestSoftHardActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_soft_hard2)
    }
}