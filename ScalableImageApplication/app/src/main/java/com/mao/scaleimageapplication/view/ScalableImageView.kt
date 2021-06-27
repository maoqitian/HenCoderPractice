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
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.mao.scaleimageapplication.R
import dp


/**
 * @Description: 利用系统提供的监听器实现可手势缩放双向滑动的 图片加载浏览自定义View
 * 手势双击监听 GestureDetector OnDoubleTapListener
 * 捏撑监听 ScaleGestureDetector
 * @author maoqitian
 * @date 2021/3/16 0016 16:04
 */

//增加放大系数 让放大状态可以上下左右都可以有空间滑动
const val EXTRA_SCALE_FRACTION = 1.5F

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs){

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
    val gestureDetector = GestureDetectorCompat(context,TapGestureListener())

    //捏撑手势监听 （放大缩小）
    val scaleGestureDetector = ScaleGestureDetector(context,MaoScaleGestureListener())

    //是否放大
    var isScaleMax = false

    //动画缩放比 修改为手指间距缩放比
    var currentScaleFraction = 0f
       set(value) {
           field = value
           invalidate()
       }

    private val  scaleAnimation :ObjectAnimator = ObjectAnimator.ofFloat(this,"currentScaleFraction",minScale,maxScale)
    /* by lazy {
        ObjectAnimator.ofFloat(this,"currentScaleFraction",minScale,maxScale)
            //双击的时候获取偏移位置修正 所以不用在进行修正
            .apply {
            doOnEnd {
                //动画缩回原来大小 执行结束 修正偏移值
                if(!isScaleMax){
                    offsetX = 0f
                    offsetX = 0f
                }
            }
        }
    }
*/
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
            //增加放大系数 让放大状态可以上下左右都可以有空间滑动
            maxScale = height / bitmapImage.height.toFloat() * EXTRA_SCALE_FRACTION
        }else {
            minScale = height / bitmapImage.height.toFloat()
            maxScale = width / bitmapImage.width.toFloat() * EXTRA_SCALE_FRACTION
        }

        //初始化当前百分比
        currentScaleFraction = minScale
        //还需更新 动画中的缩放比值
        scaleAnimation.setFloatValues(minScale,maxScale)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //逆向思路
        // 缩放系数 当前值减去最小值 / 最大值 减去最小值
        val scaleFraction = (currentScaleFraction - minScale)/(maxScale - minScale)
        //使用 translate 来进行移动
        canvas.translate(offsetX * scaleFraction,offsetY * scaleFraction)
        //参照动画 TypeEvaluator 中的算法实现 初始值 减去 （最大值 - 初始值）* 变化值（Fraction）
        //val scale = minScale + (maxScale - minScale) * scaleFraction
        //直接修改为 手指间缩放比 当前系数
        canvas.scale(currentScaleFraction,currentScaleFraction,width/2f,height/2f)
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
        //使用 捏撑 和 手机点击 gestureDetector 接管 事件分发
        scaleGestureDetector.onTouchEvent(event)
        if(!scaleGestureDetector.isInProgress){
            //如果没有开始捏撑 则让双击缩放滑动生效
            gestureDetector.onTouchEvent(event)
        }
        return true
    }


    //处理偏移
    //保证偏移在固定放大区域而不至于可以无限偏移出屏幕
    //修正放大缩小的空白区域
    private fun fixBlankOffset() {
        //同时还需限制边界值 上下左右边界 应为 图的宽高 减去 View 宽度 除2 (偏移修正)
        offsetX = offsetX.coerceAtMost((bitmapImage.width * maxScale - width) / 2) //谁小用谁
        offsetX = offsetX.coerceAtLeast(-(bitmapImage.width * maxScale - width) / 2) //谁大用谁
        offsetY = offsetY.coerceAtMost((bitmapImage.height * maxScale - height) / 2) //谁小用谁
        offsetY = offsetY.coerceAtLeast(-(bitmapImage.height * maxScale - height) / 2) //谁大用谁
    }

    //执行惯性偏移计算的 Runnable
    inner class ScrollOffsetRunnable :Runnable{
        override fun run() {
            //在Runnable 中处理 惯性滑动计算器的掐表
            if (overScroller.computeScrollOffset()){ //掐表  overScroller.computeScrollOffset() 返回 false 表示动画结束
                //正在惯性滑动 更新偏移值
                offsetX = overScroller.currX.toFloat()
                offsetY = overScroller.currY.toFloat()
                invalidate()

                //不断继续计算
                //ViewCompat.postOnAnimation 优化下一次 vsync 刷新执行
                ViewCompat.postOnAnimation(this@ScalableImageView,this)
            }
        }
    }

    /**
     * 手势监听和双击监听
     */
    inner class TapGestureListener : GestureDetector.SimpleOnGestureListener(){
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
            if(isScaleMax){
                //最大 最小 惯性偏移 为 图片的大小 减去 view 的大小除以 2
                overScroller.fling(
                    offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(bitmapImage.width*maxScale - width)/2).toInt(),
                    ((bitmapImage.width*maxScale - width)/2).toInt(),
                    (-(bitmapImage.height*maxScale - height)/2).toInt(),
                    ((bitmapImage.height*maxScale - height)/2).toInt()
                )

                ViewCompat.postOnAnimation(this@ScalableImageView,ScrollOffsetRunnable())
            }
            return true
        }

        //滑动 实际为 onMove
        override fun onScroll(
            downEvent: MotionEvent?, //第一次触发
            currentEvent: MotionEvent?, //当前触发
            distanceX: Float, //上一个位置减去现在位置 手指移动 x
            distanceY: Float  //上一个位置减去现在位置 手指移动 y
        ): Boolean {
            if(isScaleMax){ //放大状态才能移动
                //获取偏移值
                offsetX -= distanceX
                offsetY -= distanceY
                //修正空白区域 处理偏移
                fixBlankOffset()
                invalidate()
            }
            return true
        }

        //长按
        override fun onLongPress(e: MotionEvent?) {
        }

        //连续按两次触发
        override fun onDoubleTap(event: MotionEvent): Boolean {
            isScaleMax = !isScaleMax

            if (isScaleMax){ //如果是双击变大
                //根据手指触摸的位置来进行放大偏移
                //偏移值为 当前点击位置 减去图片位置除 2 * （1 - maxScale / minScale）

                offsetX = (event.x - width/2f ) * (1 - maxScale / minScale)
                offsetY = (event.y - height/2f ) * (1 - maxScale / minScale)
                //修正空白区域
                fixBlankOffset()

                scaleAnimation.start()
            }else{
                scaleAnimation.reverse()
                //否则双击变小复原
                //直接变化 转变不自然 可以根据 scaleFraction 来进行变化 并添加动画监听使其复原为 0f 值
                /*offsetX = 0f
                offsetY = 0f
                */
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

    /**
     * 捏撑手势监听 放大缩小图片
     */
    inner class MaoScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener{

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            //修正初始偏移 让其跟随手指移动
            offsetX = (detector.focusX - width/2f ) * (1 - maxScale / minScale)
            offsetY = (detector.focusY - height/2f ) * (1 - maxScale / minScale)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }

        /**
         * 返回 true 标识消费 则 detector.scaleFactor 为与上一状态的比值
         * 返回 false 标识不消费 则 detector.scaleFactor 为与初始状态的比值
         */
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // detector.scaleFactor 放缩系数：手指之间的间距起始值 与 移动之后 终点值间距的比
            // 原始值 乘 系数
            var tempCurrentScaleFraction = currentScaleFraction * detector.scaleFactor
            //限制 currentScaleFraction 在 大小范围 是否与上次值比较,防止只要一捏撑滑动就缩放
            if(tempCurrentScaleFraction < minScale || tempCurrentScaleFraction>maxScale){
                return false
            }else{
                currentScaleFraction *= detector.scaleFactor
                return true
            }
        }

    }

}