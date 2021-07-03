package com.mao.viewpageapplication.view

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
 * @Description: ViewGroup 触摸反馈逻辑练习 Viewpager 只支持两页
 * @author maoqitian
 * @date 2021/6/30 0030 10:20
 */
class ViewPager(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {



    //手指按下位置
    var downX = 0f
    var downY = 0f

    //包含到用户界面中用于超时、大小和距离的标准常量的方法。
    val viewConfiguration = ViewConfiguration.get(context)

    //追踪手指在滑动过程中的速度，包括水平方向和竖直方向
    //速度跟踪器
    val velocityTracker = VelocityTracker.obtain()
    //最小速度
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    //最大速度
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    //超过翻页阈值
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop

    //viewpager 是否正在滑动
    var isScrolling = false

    private var downScrollX = 0f
    //是否正在滑动
    private var scrolling = false
    //惯性滑动器 帮助计算惯性滑动
    private val overScroller: OverScroller = OverScroller(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //子 View 的宽高使用父 View 的宽高参数，相当于铺满父View
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //目前练习只支持两个 子View 所以直接指定摆放位置
        //子View 大小就是父View 大小
        var childLeft = 0
        var childTop = 0
        var childRight = width
        var childBottom = height

        for (child in children){
            child.layout(childLeft,childTop,childRight,childBottom)
            //继续指定下一个子View 左右边界
            childLeft += width
            childRight += width
        }

    }

    /**
     * onInterceptTouchEvent 和  onTouchEvent 中的代码类似
     * 外部触摸滑动 可能触控到子 View 则会先调用 onInterceptTouchEvent 判断是否拦截
     * 如果只触碰到 ViewPager 也就是 ViewGroup 则直接调用自身的 onTouchEvent
     * ViewGroup 需要保证对应的触摸滑动效果一样则 两个方法中代码处理逻辑类似
     */

    //只在滑动过程中判断是否为左右滑动的阈值才进行拦截，保证能左右滑动
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN){
            //如果是按下事件 清空之前拖拽
            velocityTracker.clear()
        }

        //开启速度追踪
        velocityTracker.addMovement(ev)
        //是否拦截
        var isIntercept = false

        when(ev.actionMasked){
            MotionEvent.ACTION_DOWN ->{
                isScrolling = false
                //记录ViewGroup 被按下位置
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE ->{
                //移动判断水平方向绝对值 大于水平移动阈值 则告诉父View 不必拦截事件 子View 则为 CANCEL 事件
                var dx = downX - ev.x
                if(abs(dx) > pagingSlop){
                    isScrolling = true
                    parent.requestDisallowInterceptTouchEvent(true)
                    isIntercept = true

                }
            }
        }

        return isIntercept
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN){
            //如果是按下事件 清空之前拖拽
            velocityTracker.clear()
        }
        //开启速度追踪
        velocityTracker.addMovement(event)
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN ->{
                isScrolling = false
                //记录ViewGroup 被按下位置
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE ->{
                //计算移动距离 并限制移动上下限
                val dx = (downX - event.x + downScrollX).toInt().coerceAtLeast(0).coerceAtMost(width)
                //滑动
                scrollTo(dx,0)
            }
            MotionEvent.ACTION_UP ->{
                //滑动手指抬起计算速度
                velocityTracker.computeCurrentVelocity(1000,maxVelocity.toFloat())

                //获取计算的速度
                val xVelocity = velocityTracker.xVelocity
                // x 方向滑动距离
                val scrollX = scrollX

                //获取当前滑动之后显示第几页
                val targetPage = if(abs(xVelocity) < minVelocity){
                    if (scrollX > width /2) 1 else 0
                }else{
                    //速度小于零
                    if (xVelocity < 0) 1 else 0
                }
                //滑动目标距离
                val scrollDis =if (targetPage == 1 ) width -scrollX else -scrollX
                //滑动
                overScroller.startScroll(getScrollX(),0,scrollDis,0)
                //标记下一帧无效
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    //进行重绘的时候回调用该方法 里面判断还是没滑动完继续触发重绘
    override fun computeScroll() {
        if(overScroller.computeScrollOffset()){
            scrollTo(overScroller.currX,overScroller.currY)
            //继续触发重绘
            postInvalidateOnAnimation()
        }
    }

}