package com.example.realgodjj.rxjavademo;

import android.app.Application;

import com.example.realgodjj.rxjavademo.utils.TimePlan;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static final String CONFIG = "config";//存放用户信息
    public static final String IS_FIRST_START = "is_first_start";//app是否首次安装
    public static List<TimePlan> timePlanList = new ArrayList<>();
    public static boolean isDate = false;//是否为设定全天的选择器
    public static boolean isBeginTime = false;//是否为设定开始时间的时间选择器
}
