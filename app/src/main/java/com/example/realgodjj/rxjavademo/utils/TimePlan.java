package com.example.realgodjj.rxjavademo.utils;

import java.io.Serializable;
import java.util.Date;

public class TimePlan implements Serializable {
    //    private ImageView icon;
    private String theme;
    private String location;
    private String context;
    private boolean isAllDay;
    private Date date = null, startDateTime = null, endDateTime = null;

    public TimePlan() {

    }

    public TimePlan(String theme, String location, String context, boolean isAllDay, Date date) {
        this.theme = theme;
        this.location = location;
        this.context = context;
        this.isAllDay = isAllDay;
        this.date = date;
    }

    public TimePlan(String theme, String location, String context, boolean isAllDay, Date startDateTime, Date endDateTime) {
//        this.icon = icon;
        this.theme = theme;
        this.location = location;
        this.context = context;
        this.isAllDay = isAllDay;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

//    public ImageView getIcon() {
//        return icon;
//    }

//    public void setIcon(ImageView icon) {
//        this.icon = icon;
//    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
}
