package com.mao.customviewanimation.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * @Description: 测试硬件加速和软件加速 OnDraw 方法是否会调用 打印堆栈
 *
 *  @JvmOverloads 注解 constructor 相当于自动生成以下三个构造方法
 *
 *  public TestSoftHardView(Context context) {
     this(context, null);
     }

    public TestSoftHardView(Context context, AttributeSet attrs) {
     this(context, attrs, 0);
     }

    public TestSoftHardView(Context context, AttributeSet attrs, int defStyleAttr) {
     super(context, attrs, defStyleAttr);
     }
 *
 *
 * @author maoqitian
 * @date 2021/7/26 0026 10:17
 */
class TestSoftHardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs,defStyleAttr) {


    init {
        setLayerType(LAYER_TYPE_SOFTWARE,null)
    }

    override fun onDraw(canvas: Canvas?) {
        RuntimeException().run { Log.e("TestView", toString(), this) }
    }
}