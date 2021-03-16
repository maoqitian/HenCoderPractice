package com.mao.scaleimageapplication.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.mao.scaleimageapplication.R
import dp


/**
 * @Description: 可缩放双向滑动的 图片加载浏览自定义View
 * @author maoqitian
 * @date 2021/3/16 0016 16:04
 */

//增加放大系数
const val EXTRA_SCALE_FRACTION = 1.5F

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs),GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener,Runnable {

    var IMAGE_SIZE = 200.dp.toInt()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //图片起始坐标
    var startX = 0f
    var startY = 0f

    //缩放比
    var minScale = 0f
    var maxScale = 0f

    //滑动 偏移 X Y 值
    var offsetX = 0f
    var offsetY = 0f

    //加载的图片
    private val bitmapImage = getBitmap(IMAGE_SIZE)


    //添加手势监听
    val gestureDetector = GestureDetectorCompat(context,this)

    //是否放大
    var isScaleMax = false

    //动画缩放比
    var scaleFraction = 0f
       set(value) {
           field = value
           invalidate()
       }

    private val  scaleAnimation :ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this,"scaleFraction",0f,1f)
    }

    //惯性滑动器 帮助计算惯性滑动
    val overScroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //屏幕宽度 - 减去图片宽度 除2 保证图片中心显示
        startX = ((width - IMAGE_SIZE)/2).toFloat()
        startY = ((height - IMAGE_SIZE)/2).toFloat()

        //根据图片与屏幕宽高比值计算 缩放比
        if (bitmapImage.width/bitmapImage.height.toFloat() > width/height.toFloat()){
            //图片宽高比 大于屏幕宽高比
            minScale = width / bitmapImage.width.toFloat()
            //增加放大系数
            maxScale = height / bitmapImage.height.toFloat() * EXTRA_SCALE_FRACTION
        }else {
            minScale = height / bitmapImage.height.toFloat()
            maxScale = width / bitmapImage.width.toFloat() * EXTRA_SCALE_FRACTION
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //逆向思路
        //使用 translate 来进行移动
        canvas.translate(offsetX,offsetY)
        //参照动画 TypeEvaluator 中的算法实现 初始值 减去 （最大值 - 初始值）* 变化值（Fraction）
        val scale = minScale + (maxScale - minScale) * scaleFraction
        canvas.scale(scale,scale,width/2f,height/2f)
        canvas.drawBitmap(bitmapImage,startX,startY,paint)
    }

    //根据指定的宽度加载 bitmap
    private fun getBitmap(width : Int): Bitmap {
        val options = BitmapFactory.Options()
        //只读取尺寸
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,
            R.drawable.avatar,options)
        options.inJustDecodeBounds = false
        //获取原本尺寸比
        options.inDensity = options.outWidth
        //根据输入宽度加载
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,
            R.drawable.avatar,options)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //使用gestureDetector 接管 事件分发
        return gestureDetector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {

    }
    //相当于 onclick 不开启长按 单击触发 开启长按 判断时间间隔是否超过才触发
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    //返回 true 消费事件
    override fun onDown(e: MotionEvent?): Boolean {

        return true
    }

    //快速滑动 松手 调用 （惯性滑动）
    override fun onFling(
        downEvent: MotionEvent?,
        currentEvent: MotionEvent?,
        velocityX: Float, //横向纵向滑动的速率，单位时间内滑动距离
        velocityY: Float
    ): Boolean {
        //选择 offsetX 和 offsetY 作为惯性滑动的起始坐标， 当他们为 0f 则表示当前图片在中心点没有偏移

        //最大 最小 惯性偏移 为 图片的大小 减去 view 的大小除以 2
        overScroller.fling(
            offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
            (-(bitmapImage.width*maxScale - width)/2).toInt(),
            ((bitmapImage.width*maxScale - width)/2).toInt(),
            (-(bitmapImage.height*maxScale - height)/2).toInt(),
            ((bitmapImage.height*maxScale - height)/2).toInt()
        )

        ViewCompat.postOnAnimation(this,this)

        return true
    }

    override fun run() {
        //在Runnable 中处理 惯性滑动计算器的掐表
        if (overScroller.computeScrollOffset()){ //掐表  overScroller.computeScrollOffset() 返回 false 表示动画结束
            //正在惯性滑动 更新偏移值
            offsetX = overScroller.currX.toFloat()
            offsetY = overScroller.currY.toFloat()
            invalidate()

            //不断继续计算
            //ViewCompat.postOnAnimation 优化下一次 vsync 刷新执行
            ViewCompat.postOnAnimation(this,this)
        }
    }

    //滑动 实际为 onMove
    override fun onScroll(
        downEvent: MotionEvent?, //第一次触发
        currentEvent: MotionEvent?, //当前触发
        distanceX: Float, //上一个位置减去现在位置 手指移动 x
        distanceY: Float  //上一个位置减去现在位置 手指移动 y
    ): Boolean {
        if(isScaleMax){ //放大状态才能移动
            offsetX -= distanceX
            //同时还需限制边界值 上下左右边界 应为 图的宽高 减去 View 宽度 除2 (偏移修正)
            offsetX = offsetX.coerceAtMost((bitmapImage.width * maxScale - width) / 2) //谁小用谁
            offsetX = offsetX.coerceAtLeast(-(bitmapImage.width * maxScale - width) / 2) //谁大用谁
            offsetY -= distanceY
            offsetY = offsetY.coerceAtMost((bitmapImage.height * maxScale - height) / 2) //谁小用谁
            offsetY = offsetY.coerceAtLeast(-(bitmapImage.height * maxScale - height) / 2) //谁大用谁
            invalidate()
        }
        return true
    }

    //长按
    override fun onLongPress(e: MotionEvent?) {
    }

    //连续按两次触发
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        isScaleMax = !isScaleMax

        if (isScaleMax){
            //如果是双击变大
            scaleAnimation.start()
        }else{
            scaleAnimation.reverse()
        }
        //使用刷新 值刷新就行 这里不必再调用 invalidate
        //invalidate()
        return true
    }

    //双击之后的后续事件接收
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return true
    }
    //不是双击才触发 单击确认 （确保不是双击）
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return true
    }

}