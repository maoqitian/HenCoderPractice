package com.mao.transitiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mao.transitiondemo.databinding.ActivityConstraintStartLayoutBinding
import com.mao.transitiondemo.databinding.ActivityMotionLayoutBinding

/**
 * 使用 MotionLayout 来实现 ConstraintSetActivity 的 ConstraintLayout ConstraintSet 过渡动画效果
 */
class MotionActivity : AppCompatActivity() {


    //使用 viewBinding
    lateinit var viewBing : ActivityMotionLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBing = ActivityMotionLayoutBinding.inflate(layoutInflater)
        setContentView(viewBing.root)

        viewBing.ratingFilmRating.rating = 4.5f
    }
}