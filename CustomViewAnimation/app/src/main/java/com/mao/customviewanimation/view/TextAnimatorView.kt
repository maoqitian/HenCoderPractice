package com.mao.customviewanimation.view

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import dp

/**
 *  author : maoqitian
 *  date : 2021/3/11 21:39
 *  description : 文字动画 view 绘制
 */

private val NBAAllStars = listOf(
    "迈克尔乔丹",
    "沙奎奥尼尔",
    "比尔拉塞尔",
    "特雷西麦克格雷迪",
    "艾伦艾弗森",
    "勒布朗詹姆斯",
    "科比布莱恩特",
    "克里斯保罗",
    "德怀恩韦德",
    "克里斯波什",
    "卡梅隆安东尼",
    "詹姆斯哈登",
    "凯文杜兰特",
    "凯里欧文",
    "卡哇伊莱昂纳德",
    "安东尼戴维斯",
    "保罗乔治",
    "达米安利拉德",
    "本西蒙斯",
    "扬尼斯阿昆托博",
    "卢卡东契奇",
    "杰森塔图姆",
    "杰伦布朗",
    "凯文加内特",
    "蒂姆邓肯",
    "托尼帕克",
    "吉诺比利",
    "保罗皮尔斯",
    "文斯卡特"
)
class TextAnimatorView (context: Context, attr: AttributeSet) : View(context,attr){

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.holo_red_light)
        textSize = 30.dp
        textAlign = Paint.Align.CENTER

    }

    var NBAStarName = "maoqitian"
    set(value) {
        field = value
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(NBAStarName,width/2f,height/2f,paint)
    }
}

class NBAStarEvaluator : TypeEvaluator<String>{
    override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
        //通过 数组 index 变化 来 改变字符串 index
        val startIndex = NBAAllStars.indexOf(startValue)
        val endIndex = NBAAllStars.indexOf(endValue)

        val currentIndex = startIndex + (endIndex-startIndex)*fraction

        return NBAAllStars[currentIndex.toInt()]

    }

}