package com.mao.transitiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.transition.Scene
import androidx.transition.TransitionManager

/**
 * 使用 TransitionManager.go 执行过渡动画
 */
class TransitionGoActivity : AppCompatActivity(),View.OnClickListener  {
    //lateinit var viewBing : ActivityTransitionGoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_go)
        //viewBing= ActivityTransitionGoBinding.inflate(layoutInflater)
        bindData()

    }
    private var toggle = true


    override fun onClick(view : View){
        val root = findViewById<ViewGroup>(R.id.root)
        val start = Scene.getSceneForLayout(root,R.layout.start_go_layout,this)
        val end = Scene.getSceneForLayout(root,R.layout.end_go_layout,this)

        if (toggle){
            TransitionManager.go(end)
        }else{
            TransitionManager.go(start)
        }
        //需要重新绑定数据 很麻烦 不推荐
        bindData()
        toggle = !toggle
    }

    private fun bindData() {
        findViewById<ImageView>(R.id.image_film_cover).setOnClickListener(this)
        findViewById<RatingBar>(R.id.rating_film_rating).rating = 4.5f
        findViewById<TextView>(R.id.text_film_title).text = getString(R.string.film_title)
        findViewById<TextView>(R.id.text_film_description).text = getString(R.string.film_description)
    }
}