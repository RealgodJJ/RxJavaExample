package com.example.realgodjj.rxjavademo.ui.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.base.BaseSubscriber;
import com.example.realgodjj.rxjavademo.widget.TimeKeeperView;

import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FirstFragment extends Fragment {
    private TimeKeeperView timeKeeperView;
    private String timeShow;
    private TextView textTimeShow;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss,yyyy/MM/dd  E", Locale.getDefault());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        makeTime();
//        new Thread(new TimeThread()).start();
        return view;
    }

    private void initView(View view) {
        textTimeShow = view.findViewById(R.id.tv_show_time_and_date);
        timeKeeperView = view.findViewById(R.id.image_clock_inner);
//        textHistory = view.findViewById(R.id.tv_history_of_today);
    }

    @SuppressLint("CheckResult")
    private void makeTime() {
        Flowable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Long>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        super.onSubscribe(s);
                        subList.add(s);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Calendar calendar = Calendar.getInstance();
                        String timeStr = simpleDateFormat.format(calendar.getTime());
                        String[] times = timeStr.split(",");
                        timeShow = times[0] + "\n" + times[1];
                        textTimeShow.setText(timeShow);
                        timeKeeperView.setCalendar(calendar);
                        setTextTimeShowStyle();
                    }
                });
    }

    private void setTextTimeShowStyle() {
        Spannable spannable = new SpannableString(timeShow);
        spannable.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, 8,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(200), 0, 8,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 9, 19,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(100), 9, 19,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 19, 23,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(100), 19, 23,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textTimeShow.setText(spannable);
    }
}

//    private Calendar cal;
////    private static String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
////            "星期六"};
////    private Subscription mSubscription;
////    private String hour, minute, second, year, month, date, dayOfWeek;
////    private static final int TIMEMODE = 1;

    //    @SuppressLint("HandlerLeak")
//    Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case TIMEMODE:
//                    request(128);
//                    setTextTimeShowStyle();
//                    timeKeeperView.setCalendar(cal);
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };
//
//    class TimeThread implements Runnable {
//        public void run() {
//            while (!Thread.currentThread().isInterrupted()) {
//                Message message = new Message();
//                message.what = TIMEMODE;
//                // 发送消息
//                mHandler.sendMessage(message);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }
//    }

    //    private void request(long n) {
//        mSubscription.request(n);
//    }

//    @SuppressLint("CheckResult")
//    private void makeTime() {
//        Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
//                try {
//                    int i = 0;
//                    while (true) {
//                        setTimeAndDate();
////                        Log.e(TAG, "===========================================================");
//                        emitter.onNext(String.valueOf(i) + ". " + timeShow);
////                        Log.d(TAG, "current requested: " + emitter.requested());
//                        Thread.sleep(1000);
//                        i++;
//                    }
//                } catch (Exception e) {
//                    emitter.onError(e);
//                }
//            }
//        }, BackpressureStrategy.ERROR)
//                .subscribeOn(Schedulers.io())   //上游发送在io进程
//                .observeOn(AndroidSchedulers.mainThread())  //下游在安卓主进程进行接收（时钟结果显示）
//                .sample(1, TimeUnit.SECONDS)
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        Log.d(TAG, "onSubscribe");
//                        mSubscription = s;
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.d(TAG, "onNext: " + s);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Log.w(TAG, "onError: ", t);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete");
//                    }
//                });
//    }

    //设置时间+日期
//    private void setTimeAndDate() {
//        cal = Calendar.getInstance();
//        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        if (cal.get(Calendar.AM_PM) == 0 && cal.get(Calendar.HOUR) < 10)
//            hour = "0" + String.valueOf(cal.get(Calendar.HOUR));
//        else if (cal.get(Calendar.HOUR) >= 10 &&(cal.get(Calendar.HOUR)) < 12)
//            hour = String.valueOf(cal.get(Calendar.HOUR));
//        else
//            hour = String.valueOf(cal.get(Calendar.HOUR) + 12);
//        if (cal.get(Calendar.MINUTE) < 10)
//            minute = "0" + String.valueOf(cal.get(Calendar.MINUTE));
//        else
//            minute = String.valueOf(cal.get(Calendar.MINUTE));
//        if (cal.get(Calendar.SECOND) < 10)
//            second = "0" + String.valueOf(cal.get(Calendar.SECOND));
//        else
//            second = String.valueOf(cal.get(Calendar.SECOND));
//        year = String.valueOf(cal.get(Calendar.YEAR));
//        if (cal.get(Calendar.MONTH) < 10)
//            month = "0" + String.valueOf(cal.get(Calendar.MONTH));
//        else
//            month = String.valueOf(cal.get(Calendar.MONTH));
//        if (cal.get(Calendar.DATE) < 10)
//            date = "0" + String.valueOf(cal.get(Calendar.DATE));
//        else
//            date = String.valueOf(cal.get(Calendar.DATE));
//        dayOfWeek = String.valueOf(weekOfDays[cal.get(Calendar.DAY_OF_WEEK) - 1]);
//        timeShow = hour + ":" + minute + ":" + second + "\n" + year + "/" + month + "/" + date + " "
//                + dayOfWeek;
//    }

