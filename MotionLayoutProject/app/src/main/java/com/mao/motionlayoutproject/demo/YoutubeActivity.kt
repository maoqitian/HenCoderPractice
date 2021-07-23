package com.mao.motionlayoutproject.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mao.motionlayoutproject.R
import com.mao.motionlayoutproject.databinding.ActivityYoutubeBinding

class YoutubeActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityYoutubeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}