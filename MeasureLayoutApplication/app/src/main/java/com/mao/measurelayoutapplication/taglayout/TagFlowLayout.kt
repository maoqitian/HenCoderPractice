package com.mao.measurelayoutapplication.taglayout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.*
import kotlin.math.max

/**
 *  author : maoqitian
 *  date : 2021/3/14 16:30
 *  description : 自行测量布局 将测量的结果保存返回给 父View
 */
class TagFlowLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {


    //保存每个子View 的区域
    private val childBoundsRect = mutableListOf<Rect>()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //测量的值
        val withSpecWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val withSpecWidth = MeasureSpec.getSize(widthMeasureSpec)

        //总的最大宽度
        var widthUsed = 0
        //当前行的已经使用高度
        var heightUsed = 0

        //当前行已用宽度
        var lineCurrWidth = 0
        //当前行最大高度
        var lineMaxHeight = 0
        //遍历子View
        for ((index, child) in children.withIndex()) {

            //使用系统提供的方法来进行测量
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

            //折行 当前父view 不是不限制模式 并且 当前宽度已经超出限定宽度
            if (withSpecWidthMode != MeasureSpec.UNSPECIFIED && lineCurrWidth + child.measuredWidth + child.marginLeft + child.marginRight > withSpecWidth) {
                //换行重置初始值
                lineCurrWidth = 0
                heightUsed += lineMaxHeight
                lineMaxHeight = 0

                //重新测量
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }


            if (index >= childBoundsRect.size) {
                //避免取值数组越界
                childBoundsRect.add(Rect())
            }

            val childBounds = childBoundsRect[index]

            childBounds.set(
                lineCurrWidth,
                heightUsed,
                lineCurrWidth + child.measuredWidth,
                heightUsed + child.measuredHeight
            )

            //操作完成之后更新已用宽
            lineCurrWidth += child.measuredWidth + child.marginLeft + child.marginRight

            //更新总最大宽度

            widthUsed = max(lineCurrWidth, widthUsed)
            //取的是最大高度
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight + child.marginTop + child.marginBottom)

        }

        //最终算出整个ViewGroup 的宽高保存
        val realWith = widthUsed
        //总高度 等于使用高度加上当前行高度
        val realHeight = heightUsed + lineMaxHeight

        setMeasuredDimension(realWith, realHeight)

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        for ((index, child) in children.withIndex()) {
            val bounds = childBoundsRect[index]
            child.layout(bounds.left, bounds.top, bounds.right, bounds.bottom)
        }
    }

    //重新改方法保证测量中获取 LayoutParams 类型转换一致
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}