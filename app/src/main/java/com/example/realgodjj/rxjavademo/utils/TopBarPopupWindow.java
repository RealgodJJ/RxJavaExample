package com.example.realgodjj.rxjavademo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.ui.AddTimePlanActivity;
import com.example.realgodjj.rxjavademo.ui.MainActivity;


public class TopBarPopupWindow extends PopupWindow {
    private View mView;

    /**
     * @param paramActivity 首选界面
     * @param paramOnClickListener 设置监听事件
     * @param paramInt1 popupwindow宽度
     * @param paramInt2 popupwindow高度
     * @param focusable 是否将控件设为焦点
     */
    @SuppressLint("InflateParams")
    public TopBarPopupWindow(final Activity paramActivity, View.OnClickListener paramOnClickListener,
                             int paramInt1, int paramInt2, boolean focusable) {
        super(paramActivity);
        mView = LayoutInflater.from(paramActivity).inflate(R.layout.popupwindow_menu, null);
        TextView tvAddTimePlan = mView.findViewById(R.id.tv_add_time_plan);
        TextView tvAddMemorialDay = mView.findViewById(R.id.tv_add_memorial_day);
        TextView tvEdit = mView.findViewById(R.id.tv_edit);
        if (paramOnClickListener != null) {
            setContentView(mView);
            //设置宽度
            setWidth(paramInt1);
            //设置高度
            setHeight(paramInt2);
            setFocusable(focusable);
        }

        tvAddTimePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramActivity.startActivity(new Intent(paramActivity, AddTimePlanActivity.class));
            }
        });

        tvAddMemorialDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
