package com.mao.customviewanimation

import android.animation.*
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.mao.customviewanimation.view.NBAStarEvaluator
import dp
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 属性动画本质为不断改变 view 的属性值重绘 view 来显示变化 达到动画效果
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * ViewPropertyAnimator
         * 使用view 提供的 ViewPropertyAnimator 来实现属性动画，但是只能限定ViewPropertyAnimator提供的属性方法
         */
       /* view.animate()
            .translationX(20f)
            .translationY(30f)
            .setDuration(2000)
            .setStartDelay(1000)
            .start()*/

        /**
         * ObjectAnimator 指定特定 属性变化
         */
        val circleobjectAnimator = ObjectAnimator.ofFloat(circleView,"radiusCircle",200f)
        circleobjectAnimator.startDelay = 1500
        circleobjectAnimator.duration = 2000
        circleobjectAnimator.start()

        /**
         * AnimatorSet 属性动画组合
         */

        //图片几何动画变化
        val topFlipAnimator = ObjectAnimator.ofFloat(cameraView,"topFlip",60f)
        topFlipAnimator.startDelay = 1000
        topFlipAnimator.duration  = 1500

        val flipRotateAnimator = ObjectAnimator.ofFloat(cameraView,"flipRotate",360f)
        flipRotateAnimator.startDelay = 100
        flipRotateAnimator.duration  = 1500

        val bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView,"bottomFlip",-60f)
        bottomFlipAnimator.startDelay = 100
        bottomFlipAnimator.duration  = 1500


        val animatorSet = AnimatorSet()
        //顺序执行
        animatorSet.playSequentially(topFlipAnimator,bottomFlipAnimator,flipRotateAnimator)
        animatorSet.start()


        /**
         * PropertyValuesHolder
         * 也可使用 PropertyValuesHolder 来对动画细分 也能完成 AnimatorSet动画集合的效果
         */

       /* val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip",60f)
        val flipRotateHolder = PropertyValuesHolder.ofFloat("flipRotate",360f)
        val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip",-60f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(view,topFlipHolder,flipRotateHolder,bottomFlipHolder)
        animator.duration = 3000
        animator.startDelay= 500
        animator.start()*/


        /**
         * Keyframe 关键帧
         * 细分动画细节
         */
        /*val translationLength = 200.dp
        val keyframe1 = Keyframe.ofFloat(0f,0f) //开始位置
        val keyframe2 = Keyframe.ofFloat(0.1f,0.1f * translationLength) //匀速
        val keyframe3 = Keyframe.ofFloat(0.3f,0.6f * translationLength ) //加速
        val keyframe4 = Keyframe.ofFloat(0.9f,0.7f*translationLength) //减速
        val keyframe5 = Keyframe.ofFloat(1f,1f*translationLength)
        val keyframeHolder = PropertyValuesHolder.ofKeyframe("translationX",keyframe1,keyframe2,keyframe3,keyframe4,keyframe5)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view,keyframeHolder)
        objectAnimator.duration = 3000
        objectAnimator.startDelay= 500
        objectAnimator.start()*/

        /**
         * Interpolator 插值器 设置动画速度曲线
         * AccelerateDecelerateInterpolator
         * AccelerateInterpolator
         * DecelerateInterpolator
         * LinearInterpolator
         */
        /*val flipAnimator = ObjectAnimator.ofFloat(view,"translationX",150f)
        flipAnimator.interpolator = AccelerateDecelerateInterpolator()
        flipAnimator.interpolator = AccelerateInterpolator()
        flipAnimator.interpolator = DecelerateInterpolator()
        flipAnimator.interpolator = LinearInterpolator()
        flipAnimator.duration = 1500
        flipAnimator.startDelay= 500
        flipAnimator.start()*/
        /**
         * 自定义 TypeEvaluator 各种类型都可以
         * ⽤于设置动画完成度到属性具体值的计算公式
         */
        val pointAnimator = ObjectAnimator.ofObject(pointView,"pointF",PointEvaluator(),PointF(80.dp,160.dp))
        pointAnimator.duration = 3000
        pointAnimator.startDelay= 500
        pointAnimator.start()

         val objectAnimator = ObjectAnimator.ofObject(textAnimView,"NBAStarName",NBAStarEvaluator(),"文斯卡特")
        objectAnimator.duration = 5000
        objectAnimator.startDelay= 500
        objectAnimator.start()

        /**
         * Listener
         */
        objectAnimator.addListener(object:Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
    }
}