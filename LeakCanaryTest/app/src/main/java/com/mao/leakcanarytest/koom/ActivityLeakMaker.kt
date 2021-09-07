package com.mao.leakcanarytest.koom

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity







/**
 *  author : maoqitian
 *  date : 2021/9/7 23:22
 *  description :
 */
class ActivityLeakMaker :LeakMaker<Activity>() {
    override fun startLeak(context: Context?) {
        LeakedActivity(uselessObjectList)
        val intent = Intent(context, LeakedActivity::class.java)
        context!!.startActivity(intent)
    }


    class LeakedActivity(private val uselessObjectList: ArrayList<Activity>) : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            uselessObjectList.add(this)
            finish()
        }
    }


}