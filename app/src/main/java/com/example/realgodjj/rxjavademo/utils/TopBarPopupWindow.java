package com.example.realgodjj.rxjavademo.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.realgodjj.rxjavademo.R;

public class TopBarPopupWindow extends PopupWindow {

    public  TopBarPopupWindow(Activity paramActivity, View.OnClickListener paramOnClickListener,
                         int paramInt1, int paramInt2){
        View mView = LayoutInflater.from(paramActivity).inflate(R.layout.popupwindow_menu, null);
        LinearLayout llAddTimePlan = mView.findViewById(R.id.ll_add_time_plan);
        LinearLayout llAddMemorialDay = mView.findViewById(R.id.ll_add_memorial_day);
        LinearLayout llEdit = mView.findViewById(R.id.ll_edit);
        if (paramOnClickListener != null){
            //设置点击监听
            llAddTimePlan.setOnClickListener(paramOnClickListener);
            llAddMemorialDay.setOnClickListener(paramOnClickListener);
            llEdit.setOnClickListener(paramOnClickListener);
            setContentView(mView);
            //设置宽度
            setWidth(paramInt1);
            //设置高度
            setHeight(paramInt2);
            //设置背景透明
            setBackgroundDrawable(new ColorDrawable(0));
        }
    }
}
