package com.mao.constraintlayoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.Placeholder

/**
 * 占位 Placeholder
 */

class PlaceHolderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_place_holder)


    }
    fun onClick(view: View) {
        findViewById<Placeholder>(R.id.placeholder).setContentId(view.id)
    }
}