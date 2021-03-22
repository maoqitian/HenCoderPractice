package com.mao.constraintlayoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.helper.widget.Layer

class SampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_circular_position)



        // layer 对一组 view 做操作
        /*findViewById<Button>(R.id.button).setOnClickListener {
            findViewById<Layer>(R.id.layer).rotation = 45f
            findViewById<Layer>(R.id.layer).translationY = 100f
            findViewById<Layer>(R.id.layer).translationX = 100f
        }*/


    }
}