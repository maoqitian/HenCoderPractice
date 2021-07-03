package com.mao.viewdragapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

/**
 * @Description: 可拖拽的自定义 GridView  OnDragListener 方式 注重内容的移动
 * ⽤户的「拖起 -> 放下」操作，重在内容的移动。可以附加拖拽数据
 * @author maoqitian
 * @date 2021/6/30 0030 15:50
 */

// 2列 3行
private const val COLUMNS = 2
private const val ROWS = 3

class DragListenerGridView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {


    //拖动监听
    val dragListener: OnDragListener = MaoDragListener()

    private var draggedView: View? = null
    private var orderedChildren: MutableList<View> = ArrayList()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //根据父view 传递过来的宽高参数平均分配 子 View 的宽高
        val sizeSpecWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeSpecHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = sizeSpecWidth / COLUMNS
        val childHeight = sizeSpecHeight / ROWS
        //测量子View
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY))
        //保存当前View 宽高参数
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec)
    }

    //平均分配子 View 宽高
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft:Int
        var childTop:Int
        //固定宽高
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS

        for ((index , child ) in children.withIndex()){
            //计算对应的左上角坐标值
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            //所有子 View 摆放左上角
            child.layout(0,0,childWidth,childHeight)
            //平移对应距离保持平均分配
            child.translationX = childLeft.toFloat()
            child.translationY = childTop.toFloat()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        //遍历子View 给每个子View 设置拖拽监听
        for (child in children){
            //拖拽初始化位置保存
            orderedChildren.add(child)
            //设置长按监听开启拖拽
            child.setOnLongClickListener { view ->
                draggedView = view
                //第三个参数为本地参数 传入在 拖拽监听中可以获取到
                //子 View 开启拖拽
                view.startDrag(null, DragShadowBuilder(view),view,0)
                false
            }
            //设置子View 拖拽监听
            child.setOnDragListener(dragListener)
        }
    }


    internal inner class MaoDragListener : OnDragListener {
        //拖动回调
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when(event.action){
                //拖拽开始
                DragEvent.ACTION_DRAG_STARTED -> if (event.localState === v){
                    //如果拖拽View 是当前正常回调处理的 View 隐藏原本显示的
                    v.visibility = View.INVISIBLE
                }
                //拖拽到对应的区域
                DragEvent.ACTION_DRAG_ENTERED -> if (event.localState !== v){
                    //如果拖拽 View 对应进入不是当前正常回调处理的 View 自动排序 并加入动画
                    sort(v)
                }
                //拖拽结束
                DragEvent.ACTION_DRAG_ENDED -> if (event.localState === v){
                    //如果拖拽View 是当前正常回调处理的 View 恢复显示
                    v.visibility = View.VISIBLE
                }

            }
            return true
        }

    }


    private fun sort(targetView: View) {
        var dragIndex = -1
        var targetIndex = -1

        for ((index ,child ) in orderedChildren.withIndex()){
            if(targetView === child){
                targetIndex = index
            }else if(draggedView === child){
                dragIndex = index
            }
        }

        orderedChildren.removeAt(dragIndex)
        orderedChildren.add(targetIndex,draggedView!!)
        //重新摆放 添加平移动画
        var childLeft:Int
        var childTop:Int
        //固定宽高
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS

        for ((index , child ) in orderedChildren.withIndex()){
            //计算对应的左上角坐标值
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight

            //平移动画
            child.animate()
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat())
                .duration = 200
        }
    }

}