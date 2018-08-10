package com.example.realgodjj.rxjavademo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferencesUtil {
    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.Editor editor;
    private List<TimePlan> timePlanList;
    private String fileName = "";
    private int mode = 0;
//    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();

    @SuppressLint({"WrongConstant", "CommitPrefEdits"})
    public SharedPreferencesUtil(Context context, String fileName, List<TimePlan> timePlanList) {
        this.context = context;
        this.timePlanList = timePlanList;
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.fileName = fileName;
        mode = Context.MODE_PRIVATE;
//        LogUtil.d(TAG, " create SharedPreferencesUtil; name : " + this.fileName + "; mode : " + mode);
    }

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesUtil(Context context, String fileName, int mode) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(fileName, mode);
        this.editor = this.sharedPreferences.edit();
        this.fileName = fileName;
        this.mode = mode;
//        LogUtil.d(TAG, " create SharedPreferencesUtil; name : " + fileName + "; mode : " + mode);
    }

    public boolean putString(String key, String value) {
        editor.putString(key, value);
        //        LogUtil.d(TAG, "put key : " + key + ", value : " + value + " to file : " + fileName);
        return editor.commit();
    }

    public boolean putLong(String key, Long value) {
        editor.putLong(key, value);
        //        LogUtil.d(TAG, "put key : " + key + ", value : " + value + " to file : " + fileName);
        return editor.commit();
    }

    public boolean putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        //        LogUtil.d(TAG, "put key : " + key + ", value : " + value + " to file : " + fileName);
        return editor.commit();
    }

    public boolean remove(String key) {
        editor.remove(key);
        //        LogUtil.d(TAG, "remove key : " + key + " from file : " + fileName + " result: " + result);
        return editor.commit();
    }

    public boolean clear() {
        editor.clear();
        //        LogUtil.d(TAG, "clear file : " + fileName + " result: " + result);
        return editor.commit();
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, true);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "error");
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    /**
     * 保存List类型的数据
     *
     * @param key
     * @param list 当前时间计划列表
     */
    public <T> void setListValue(String key, List<T> list) {
        if (null == list || list.size() <= 0)
            return;
        Gson gson = new Gson();
        Type type = new TypeToken<List<T>>() {
        }.getType();
        String timePlanJson = gson.toJson(list, type);
        editor.clear();
        editor.putString(key, timePlanJson);
        editor.commit();
    }

    public <T> List<T> getListValue(String key, Class<T[]> clazz) {
        List<T> timePlanList = new ArrayList<T>();
        String timePlanJson = sharedPreferences.getString(key, null);
        if (timePlanJson == null) {
            return timePlanList;
        }
        T[] arr = new Gson().fromJson(timePlanJson, clazz);
        //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
        return new ArrayList(Arrays.asList(arr));
    }

//    public <T> List<T> getListValue(String key) {
//        List<T> timePlanList = new ArrayList<T>();
//        String timePlanJson = sharedPreferences.getString(key, null);
//        if (timePlanJson == null) {
//            return timePlanList;
//        }
//        Gson gson = new Gson();
//        timePlanList = gson.fromJson(timePlanJson, new TypeToken<List<T>>() {
//        }.getType());
//        return timePlanList;
//    }
}
