package com.mao.transitiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.mao.transitiondemo.databinding.ActivityConstraintStartLayoutBinding
import com.mao.transitiondemo.databinding.ActivityMainBinding

class ConstraintSetActivity : AppCompatActivity(), View.OnClickListener {

    //使用 viewBinding
    lateinit var viewBing : ActivityConstraintStartLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBing = ActivityConstraintStartLayoutBinding.inflate(layoutInflater)
        setContentView(viewBing.root)

        viewBing.imageFilmCover.setOnClickListener(this)
        viewBing.ratingFilmRating.rating = 4.5f

    }

    private var toggle = true

    override fun onClick(v: View?) {

        TransitionManager.beginDelayedTransition(viewBing.root)
        val constraintSet = ConstraintSet()
        if(toggle){
            constraintSet.clone(this,R.layout.activity_constraint_end_layout)
        }else{
            constraintSet.clone(this,R.layout.activity_constraint_start_layout)
        }

        constraintSet.applyTo(viewBing.root)
        toggle = !toggle
    }
}