package com.mao.viewdragapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import kotlinx.android.synthetic.main.drag_left_right.view.*
import kotlin.math.abs

/**
 *  author : maoqitian
 *  date : 2021/3/20 23:07
 *  description : 使用 ViewDragHelper 实现 左右拖动自动停靠的滑动控件
 */
class DragLeftRightLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    //拖动监听回调
   private val dragListener :ViewDragHelper.Callback = DragCallback()
   private val dragHelper = ViewDragHelper.create(this,dragListener)
   private val  viewConfiguration = ViewConfiguration.get(context)


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        //接管拦截事件
        return dragHelper.shouldInterceptTouchEvent(ev)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        //接管触摸事件
        dragHelper.processTouchEvent(event)
        return true
    }

    //重绘的时候回调用该方法
    override fun computeScroll() {
        if (dragHelper.continueSettling(true)){
            //滑动还没停止 持续刷新
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }


    internal inner class DragCallback : ViewDragHelper.Callback() {

        //尝试捕获拖动
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {

            return child === dragLeftRightView
        }


        //只让左右滑动
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }
        //实现该方法可以上下滑动
        /*override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }*/

        //当释放拖动view 时候
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            //判断阈值
            if(abs(xvel) > viewConfiguration.scaledMinimumFlingVelocity){
                if(xvel > 0){
                    //如果大于 0
                    //移动到右边
                    dragHelper.settleCapturedViewAt(width-releasedChild.width,0)
                }else{
                    //回到原来位置
                    dragHelper.settleCapturedViewAt(0,0)
                }
            }else{
                if(releasedChild.left < width - releasedChild.right){
                    //左边还是小于
                    //回到原来位置
                    dragHelper.settleCapturedViewAt(0,0)
                }else{

                    //移动到右边
                    dragHelper.settleCapturedViewAt(width-releasedChild.width,0)
                }
            }
            //刷新
            postInvalidateOnAnimation()
        }
    }

}