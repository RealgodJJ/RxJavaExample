package com.example.realgodjj.rxjavademo.widget;

import android.app.Application;

import com.example.realgodjj.rxjavademo.ui.MainActivity;
import com.example.realgodjj.rxjavademo.utils.SharedPreferencesUtil;
import com.example.realgodjj.rxjavademo.utils.TimePlan;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static final String CONFIG = "config";//存放用户信息
    public static final String IS_FIRST_START = "is_first_start";//app是否首次安装
    public static List<TimePlan> planList = new ArrayList<>();
    public static SharedPreferencesUtil sharedPreferencesUtil = null;;
}
