package com.mao.viewdragapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper

/**
 * @Description: 可拖拽的自定义 GridView  使用 ViewDragHelper 实现
 * 注重 ViewGroup 每个子 View 的拖拽
 * @author maoqitian
 * @date 2021/6/30 0030 15:50
 */

// 2列 3行
private const val COLUMNS = 2
private const val ROWS = 3

class DragHelperGridView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    val dragHelper = ViewDragHelper.create(this, DragHelperCallBack())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //根据父view 传递过来的宽高参数平均分配 子 View 的宽高
        val sizeSpecWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeSpecHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = sizeSpecWidth / COLUMNS
        val childHeight = sizeSpecHeight / ROWS
        //测量子View
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY))
        //保存当前View 宽高参数
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec)
    }

    //平均分配子 View 宽高
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft:Int
        var childTop:Int
        //固定宽高
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS

        for ((index , child ) in children.withIndex()){
            //计算对应的左上角坐标值
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(childLeft,childTop,childLeft+childWidth,childTop+childHeight)
        }
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        //自动计算便宜位置
        if (dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    // ViewDragHelper 回调
    inner class DragHelperCallBack : ViewDragHelper.Callback() {
        //尝试开始拖动
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        //返回 left 标识可以左右滑动
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }
        //返回 top 标识可以上下滑动
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        var capturedLeft = 0f
        var capturedTop = 0f
        //捕获拖拽
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
           // View 位置的初始化操作
           capturedChild.elevation = elevation+1
           capturedLeft = capturedChild.left.toFloat()
           capturedTop = capturedChild.top.toFloat()
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
        }
        //结束拖拽
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            //结束拖拽 设置 View 位置
            dragHelper.settleCapturedViewAt(capturedLeft.toInt(),capturedTop.toInt())
            //下一帧失效 刷新
            postInvalidateOnAnimation()
        }

    }
}