package com.example.realgodjj.rxjavademo.utils;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimePlan{
//    private ImageView icon;
    private String title;
    private String location;
    private String context;
    private boolean isAllDay;
    private Date dateTime;

    public TimePlan(String title, String location, String context, boolean isAllDay) {
        this.title = title;
        this.location = location;
        this.context = context;
        this.isAllDay = isAllDay;
        this.dateTime = dateTime;
    }

    public TimePlan(ImageView icon, String title, String location, String context, boolean isAllDay) {
//        this.icon = icon;
        this.title = title;
        this.location = location;
        this.context = context;
        this.isAllDay = isAllDay;
//        this.dateTime = dateTime;
    }

//    public ImageView getIcon() {
//        return icon;
//    }

//    public void setIcon(ImageView icon) {
//        this.icon = icon;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
