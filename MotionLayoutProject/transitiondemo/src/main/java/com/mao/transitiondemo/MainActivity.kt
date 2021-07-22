package com.mao.transitiondemo

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.mao.transitiondemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //使用 viewBinding
    lateinit var viewBing :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBing= ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBing.root)


        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities.forEach {

                activity ->

              if (activity.name == this::class.java.name){
                  return@forEach
              }

             val clazz = Class.forName(activity.name)
            val button = Button(this).apply {
                isAllCaps = false
                text = clazz.simpleName
                setOnClickListener {
                    startActivity(Intent(this@MainActivity, clazz))
                }
            }
            viewBing.ll.addView(button)
        }
    }

}