package com.mao.genericsproject;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;

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
}
