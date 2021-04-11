package com.mao.genericsproject;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maoqitian
 * @Description:
 * @date 2021/4/9 0009 14:00
 */
class AppleArrayList<T> extends ArrayList {

    //上边界
    ArrayList<? extends View> fruitList = new ArrayList<TextView>();

    //下边界
    ArrayList<? super AppCompatTextView> fruitList2 = new ArrayList<TextView>();


    /**
     * 泛型方法 将 对应希望类型的 item 加入到 对应类型 list 集合中
     * @param item
     * @param list
     * @param <A>
     */
    public <A> void merge(A item, List<A> list){
          list.add(item);
    }
}
