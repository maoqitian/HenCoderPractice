package com.mao.viewpagetouchapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

/**
 *  author : maoqitian
 *  date : 2021/3/19 22:53
 *  description : ViewGroup 的触摸反馈
 */
class ColorViewPage(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    //手指按下位置
    var downX = 0f
    var downY = 0f

    private var downScrollX = 0f
    //是否正在滑动
    private var scrolling = false
    //惯性滑动器 帮助计算惯性滑动
    private val overScroller: OverScroller = OverScroller(context)

    //包含到用户界面中用于超时、大小和距离的标准常量的方法。
    val viewConfiguration = ViewConfiguration.get(context)

    //追踪手指在滑动过程中的速度，包括水平方向和竖直方向
    val velocityTracker = VelocityTracker.obtain()
    //最小速度
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    //最大速度
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    //超过翻页阈值
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //使用 ViewGroup 提供的现成方法测量 child
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //布局铺满全屏
        //child 上下左右位置
        var childLeft = 0
        var childTop = 0
        var childRight = width
        var childBottom = height
        for (child in children){
            //遍历并排布局
            child.layout(childLeft,childTop,childRight,childBottom)
            //更新左右位置
            childLeft += width
            childRight += width
        }


    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if(ev.actionMasked == MotionEvent.ACTION_DOWN){
            //重置回收
            velocityTracker.clear()
        }
        //开启速度追踪
        velocityTracker.addMovement(ev)
        var isIntercept = false
        when(ev.actionMasked){

            MotionEvent.ACTION_DOWN ->{
                scrolling = false
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE ->
                if (!scrolling){
                    val dx = downX - ev.x
                    //获取手指滑动的绝对值 判断当前ColorViewPage是否需要进行拦截点击事件
                    if(abs(dx) > pagingSlop ){
                        //超过阈值 进行拦截
                        scrolling = true
                        //父View 不要拦截事件
                        parent.requestDisallowInterceptTouchEvent(true)
                        isIntercept = true
                    }
                }
        }
        return isIntercept
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.actionMasked == MotionEvent.ACTION_DOWN){
            //重置回收
            velocityTracker.clear()
        }
        //开启速度追踪
        velocityTracker.addMovement(event)
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN ->{
                //记录按下位置
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE ->{
                //计算滑动距离
                val scrolldx = (downX - event.x + downScrollX).toInt().coerceAtLeast(0).coerceAtMost(width)
                //移动
                scrollTo(scrolldx,0)
            }
            MotionEvent.ACTION_UP ->{
                //手指抬起计算当前速度 并且继续滑动
                //计算时间间隔1000 ms
                velocityTracker.computeCurrentVelocity(1000,maxVelocity.toFloat())
                val velocityX = velocityTracker.xVelocity
                val scrollX = scrollX
                //目前只有两页
                val targetPage = if (abs(velocityX) < minVelocity) {
                    if (scrollX > width / 2) 1 else 0
                } else {
                    if (velocityX < 0) 1 else 0
                }
                val scrollDistance = if (targetPage == 1) width - scrollX else -scrollX
                overScroller.startScroll(getScrollX(), 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }

        return true
    }

    //进行重绘的时候回调用该方法
    override fun computeScroll() {
        if(overScroller.computeScrollOffset()){ //还没停止 继续绘制
            scrollTo(overScroller.currX,overScroller.currY)
            //继续触发重绘
            postInvalidateOnAnimation()
        }
    }
}