package com.mao.constraintlayoutproject

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.VirtualLayout

/**
 * @Description:自定义ConstraintLayout控件组合帮助类 包含控件自动排列
 * @author maoqitian
 * @date 2021/3/22 0022 11:24
 */
class Linear(context: Context, attrs: AttributeSet) : VirtualLayout(context, attrs) {

    //使用 constraintSet 为每个控件设置排列规则
    private val constraintSet : ConstraintSet by lazy {
        ConstraintSet().apply {
            //不强制控件加入 id
            isForceId = false
        }
    }


    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
        constraintSet.clone(container)

        val viewIds = referencedIds

        for (i in 1 until mCount){
            //遍历加入 Linear 的控件
            //当前控件 id
            val currentId = viewIds[i]
            //上一个控件 id
            val beforeId = viewIds[i-1]

            constraintSet.connect(currentId,ConstraintSet.START,beforeId,ConstraintSet.START)
            constraintSet.connect(currentId,ConstraintSet.TOP,beforeId,ConstraintSet.BOTTOM)

            constraintSet.applyTo(container)
        }
    }

}