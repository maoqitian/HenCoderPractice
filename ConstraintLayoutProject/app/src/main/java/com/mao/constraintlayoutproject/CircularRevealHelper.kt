package com.mao.constraintlayoutproject

import android.content.Context
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.hypot

/**
 * @Description: 自定义ConstraintLayout控件组合帮助类 需继承 ConstraintHelper
 * @author maoqitian
 * @date 2021/3/22 0022 11:16
 */
class CircularRevealHelper(context: Context, attrs: AttributeSet) : ConstraintHelper(context, attrs) {

    //控件加载完成时执行
    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)

        //遍历关联的控件做出圆形变化显示动画
        referencedIds.forEach {
            val view = container.getViewById(it)
            val radius = hypot(view.width.toDouble(), view.height.toDouble()).toInt()

            ViewAnimationUtils.createCircularReveal(view, 0, 0, 0f, radius.toFloat())
                .setDuration(2000L)
                .start()
        }
    }
}