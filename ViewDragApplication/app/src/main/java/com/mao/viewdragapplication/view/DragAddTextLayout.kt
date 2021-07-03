package com.mao.viewdragapplication.view

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.drag_add_text.view.*

/**
 *  author : maoqitian
 *  date : 2021/3/21 10:20
 *  description : 拖拽到固定区域添加文字效果  OnDragListener 方式 注重内容的移动。可以附加拖拽数据
 */
class DragAddTextLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    //长按监听 开启拖拽
    private val dragStarter = OnLongClickListener {
        val dragImageData = ClipData.newPlainText("imageName",it.contentDescription)
        ViewCompat.startDragAndDrop(it,dragImageData, DragShadowBuilder(it),null,0)
    }
    //拖拽监听
    private val dragListener :OnDragListener = DragAddTexListener()

    inner class DragAddTexListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when(event.action){
                //在区域落下
                DragEvent.ACTION_DROP ->
                    if(v is LinearLayout){
                        val textView = TextView(context)
                        textView.textSize = 16f
                        textView.text = event.clipData.getItemAt(0).text
                        v.addView(textView)
                        Toast.makeText(context,"拖拽添加${event.clipData.getItemAt(0).text}",Toast.LENGTH_SHORT).show()
                    }
            }

            return true
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        //布局加载完成添加拖拽监听和长按监听
        zoroView.setOnLongClickListener(dragStarter)
        googleView.setOnLongClickListener(dragStarter)
        addTextLayout.setOnDragListener(dragListener)
    }

}