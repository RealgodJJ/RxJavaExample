package com.example.realgodjj.rxjavademo.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    private TextView tvTimeShow, tvGetText;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss,yyyy/MM/dd  E", Locale.getDefault());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        makeTime();
        return view;
    }

    private void initView(View view) {
        tvTimeShow = view.findViewById(R.id.tv_show_time_and_date);
        timeKeeperView = view.findViewById(R.id.image_clock_inner);
        tvGetText = view.findViewById(R.id.tv_get_text);
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
                        tvTimeShow.setText(timeShow);
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
        tvTimeShow.setText(spannable);
    }

    public void updataUI(String info) {
        tvGetText.setText(info);
    }
}

