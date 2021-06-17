package com.mao.materialedittext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import dp

/**
 *  author : maoqitian
 *  date : 2021/3/13 15:13
 *  description : 自定义显示 hint 提示的 EditText
 */
class MaterialEditText(context: Context,attributeSet: AttributeSet) :
    AppCompatEditText(context,attributeSet){

    //是否显示 label 标识
    private var floatingLabelShown = false
    //显示隐藏百分比
    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    //画笔
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //提示文字大小
    var HINT_TEXT_SIZE = 12.dp

    //文字 margin
    var TEXT_MARGIN = 8.dp

    //label 文字位置
    private val HORIZONTAL_OFFSET = 5.dp
    private val VERTICAL_OFFSET = 23.dp

    //延迟并只加载一次
    val animatorShow by lazy {
        ObjectAnimator.ofFloat(this,"floatingLabelFraction",0f,1f)
    }

    //是否使用 悬浮label
    var useFloatLabel = false
        set(value) {
            if(field != value){
                //值有变化才进行padding 设置
                field = value
                if(field){
                    setPadding(paddingLeft,
                        (paddingTop + HINT_TEXT_SIZE+TEXT_MARGIN).toInt(),paddingRight,paddingBottom)
                }else{
                    setPadding(paddingLeft,
                        (paddingTop - HINT_TEXT_SIZE-TEXT_MARGIN).toInt(),paddingRight,paddingBottom)
                }
            }
        }

    init {
        paint.textSize = HINT_TEXT_SIZE

        //遍历获取所有的属性值
        for(i in 0 until  attributeSet.attributeCount){
            println("Attrs：Key ${attributeSet.getAttributeName(i)}, value : ${attributeSet.getAttributeValue(i)}")
        }

        //通过 typedArray 获取自定义属性值
        var typeArray = context.obtainStyledAttributes(attributeSet, intArrayOf(R.attr.useFloatLabel))

        useFloatLabel = typeArray.getBoolean(0,true);

        typeArray.recycle()

    }


    // 监听输入文字变化
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        //有输入的文字
        if(floatingLabelShown && text.isNullOrEmpty()){
            floatingLabelShown = false
            animatorShow.reverse()
        }else if(!floatingLabelShown && !text.isNullOrEmpty()){
            floatingLabelShown = true
            animatorShow.start()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //通过改变透明度来改变显示效果
          paint.alpha = (floatingLabelFraction * 0xff).toInt()
        //动态计算高度 根据动画
        val currentval = VERTICAL_OFFSET + VERTICAL_OFFSET * (1- floatingLabelFraction)
        if(!hint.isNullOrEmpty() && useFloatLabel){
            //需要时才显示
            canvas.drawText(hint.toString(),HORIZONTAL_OFFSET,currentval,paint)
        }
    }

}