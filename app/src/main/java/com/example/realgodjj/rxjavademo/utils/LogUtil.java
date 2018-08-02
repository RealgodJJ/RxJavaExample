package com.example.realgodjj.rxjavademo.utils;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.LinkedList;

/**
 * log信息打印工具
 */
public final class LogUtil {
    private static boolean logAble;
    private static String logTag;
    private static Builder builder = null;
    private static int logCount;
    private static LogCallBack logCallBack = null;

    private static LinkedList<LogMsgModel> logMsgList = new LinkedList<>();

    public static class LogMsgModel {
        private final int level;
        private final String msg;

        LogMsgModel(int level, String msg) {
            this.level = level;
            this.msg = msg;
        }

        public int getLevel() {
            return level;
        }

        public String getMsg() {
            return msg;
        }
    }

    public static final class Builder {
        boolean logAble;
        String logTag;
        int logCount;

        public Builder() {
            this.logAble = true;
            this.logTag = LogUtil.class.getSimpleName();
            this.logCount = 20;
        }

        public Builder setLogAble(boolean logAble) {
            this.logAble = logAble;
            return this;
        }

        public Builder setLogTag(@NonNull String logTag) {
            this.logTag = logTag;
            return this;
        }

        public Builder setLogCount(@IntRange(from = 1) int logCount) {
            this.logCount = logCount;
            return this;
        }

        public void build() {
            LogUtil.builder = this;
            LogUtil.logTag = builder.logTag;
            LogUtil.logCount = builder.logCount;
            LogUtil.logAble = builder.logAble;
        }
    }

    public static void i(Object msg) {
        log(logTag, msg, Log.INFO);
    }

    public static void i(String tag, Object msg) {
        log(tag, msg, Log.INFO);
    }

    public static void d(Object msg) {
        log(logTag, msg, Log.DEBUG);
    }

    public static void d(String tag, Object msg) {
        log(tag, msg, Log.DEBUG);
    }

    public static void w(Object msg) {
        log(logTag, msg, Log.WARN);
    }

    public static void w(String tag, Object msg) {
        log(tag, msg, Log.WARN);
    }

    public static void e(Object msg) {
        log(logTag, msg, Log.ERROR);
    }

    public static void e(String tag, Object msg) {
        log(tag, msg, Log.ERROR);
    }

    private static void log(String tag, Object obj, int level) {
        if (null == builder) {
            throw new NullPointerException("LogUitl Builder is null ! ");
        }
        if (null == logTag || logTag.isEmpty()) {
            throw new IllegalStateException("tag cant not be null or empty ! ");
        }
        if (logMsgList.size() > logCount) {
            logMsgList.removeFirst();
        }
        String msg = String.valueOf(obj);
        LogMsgModel item = new LogMsgModel(level, msg);
        logMsgList.addLast(item);
        if (null != logCallBack) {
            logCallBack.logPrint(item);
        }

        if (!logAble) {
            return;
        }
        int length = msg.length();
        if (length > 4000) {
            int i = 0;
            while (i < length) {
                if (i + 4000 < length) {
                    Log.println(level, tag, msg.substring(i, i + 4000));
                } else {
                    Log.println(level, tag, msg.substring(i, length));
                }
                i += 4000;
            }
        } else {
            Log.println(level, tag, msg);
        }

    }

    public static void setLogCallBack(LogCallBack logCallBack) {
        LogUtil.logCallBack = logCallBack;
    }

    public static LinkedList<LogMsgModel> getLogMsgList() {
        return logMsgList;
    }

    public interface LogCallBack {
        void logPrint(LogMsgModel item);
    }
}
