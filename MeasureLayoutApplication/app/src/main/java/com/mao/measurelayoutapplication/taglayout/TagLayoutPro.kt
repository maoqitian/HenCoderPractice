package com.mao.measurelayoutapplication.taglayout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.*

/**
 *  author : maoqitian
 *  date : 2021/8/15 15:44
 *  description : 自定义View之自定义布局（layout） TagFlow 布局
 *  每个子View 的大小大小都自己测量 同时调用   setMeasuredDimension() 告诉父View 自己测量的大小
 *  同时也保存自己测量的每个子View 的大小 并重写 onLayout 方法对每个子View进行布局
 */
class TagLayoutPro(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {


    //保存测量好的每个子View大小 使用 Rect 保存
    private val childBoundsRect = mutableListOf<Rect>()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //父View 测量的值
        val withSpecWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val withSpecWidth = MeasureSpec.getSize(widthMeasureSpec)


        //已经使用宽高
        var widthUsed = 0
        var heightUsed = 0


        //当前行已用宽度
        var lineCurrWidth = 0
        //当前行最大高度
        var lineMaxHeight = 0

        //自定义测量子View 分为三步
        //遍历每个子View 进行测量
        children.forEachIndexed { index, childView ->
            //1 获取 每个子 View 的 measuredWidthSpec  measuredHeightSpec
            //通过 父View 的 要去和 子View 自身的宽高测量子View
            //初始已经宽度为 0 按照最大来测量 处理折行问题
            measureChildWithMargins(childView,widthMeasureSpec,0,heightMeasureSpec,heightUsed)

            //已用宽度大于父View 限制宽度 并且不是无限制
            if(withSpecWidthMode != MeasureSpec.UNSPECIFIED && lineCurrWidth + childView.measuredWidth+childView.marginLeft+childView.marginRight > withSpecWidth){

                //新的一行开始高度
                heightUsed += lineMaxHeight
                //重置当前行宽高 并折行
                lineMaxHeight = 0
                lineCurrWidth = 0
                //折行重新测量
                measureChildWithMargins(childView,widthMeasureSpec,0,heightMeasureSpec,heightUsed)
            }


            //2 保存 对应的位置到  childBoundsRect list 集合中
            if (index >= childBoundsRect.size) {
                childBoundsRect.add(Rect())} //保证第一次取能去到 Rect对象

            val childRect = childBoundsRect[index]
            childRect.set(lineCurrWidth,
                heightUsed,
                lineCurrWidth+childView.measuredWidth,
                heightUsed+childView.measuredHeight)

            //更新当前行累计使用宽高
            lineCurrWidth += childView.measuredWidth+childView.marginLeft+childView.marginRight

            lineMaxHeight = Math.max(lineMaxHeight,childView.measuredHeight+childView.marginTop+childView.marginBottom)

            //每次测量更新是否有最大的宽度 也就是已经使用宽度
            widthUsed = Math.max(widthUsed,lineCurrWidth)

        }

        //3 最后计算当前布局 ViewGroup 总的宽高保存
        //最终算出整个ViewGroup 的宽高保存
        val realWith = widthUsed
        //总高度 等于使用高度加上当前行高度
        val realHeight = heightUsed + lineMaxHeight

        setMeasuredDimension(realWith,realHeight)
    }

    //自定义 布局
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //遍历每个子View 并布局
        children.forEachIndexed { index, childView ->
            val rect = childBoundsRect[index]
            childView.layout(rect.left,rect.top,rect.right,rect.bottom)
        }
    }

    //重新改方法保证测量中获取 LayoutParams 类型转换一致
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}