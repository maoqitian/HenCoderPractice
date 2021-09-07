package com.mao.leakcanarytest.koom

import android.content.Context
import androidx.fragment.app.Fragment

/**
 *  author : maoqitian
 *  date : 2021/9/7 23:34
 *  description :
 */
class FragmentLeakMaker :LeakMaker<Fragment>() {
    override fun startLeak(context: Context?) {

    }
}