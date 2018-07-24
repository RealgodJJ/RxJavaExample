package com.example.realgodjj.rxjavademo.ui.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.realgodjj.rxjavademo.Adapter.MyRecycleViewAdapter;
import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.base.BaseSubscriber;
import com.example.realgodjj.rxjavademo.utils.TimePlan;
import com.example.realgodjj.rxjavademo.widget.TimeKeeperView;

import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FirstFragment extends Fragment {
    private TimeKeeperView timeKeeperView;
    private String timeShow;
    private TextView tvTimeShow;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss,yyyy/MM/dd  E", Locale.getDefault());
    private RecyclerView myRecyclerView;
    private MyRecycleViewAdapter myRecycleViewAdapter;
    private List<TimePlan> planList;

    //PlanList中的控件
//    private TextView tvPlanTitle, tvPlanContext, tvPlanlocation;
//    private ImageView ivIcon;

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
        myRecyclerView = view.findViewById(R.id.rv_list_time_plan);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //设置默认动画效果
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //初始化并设置适配器view
        planList = new ArrayList<>();
        myRecycleViewAdapter = new MyRecycleViewAdapter(planList);
        myRecyclerView.setAdapter(myRecycleViewAdapter);
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
        spannable.setSpan(new AbsoluteSizeSpan(160), 0, 8,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 9, 19,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(60), 9, 19,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 19, 23,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(60), 19, 23,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvTimeShow.setText(spannable);
    }

    public void updataUI(TimePlan timePlan) {
        //TODO:Add a recycleView
        planList.add(timePlan);
//        MyRecycleViewAdapter.ViewHolder.tvPlanTitle.setText(timePlan.getTitle());
//        MyRecycleViewAdapter.ViewHolder.tvPlanLocation.setText(timePlan.getLocation());
//        MyRecycleViewAdapter.ViewHolder.tvPlanTitle.setText(timePlan.getContext());
//        tvPlanTitle.setText(timePlan.getTitle());
//        tvPlanlocation.setText(timePlan.getLocation());
//        tvPlanContext.setText(timePlan.getContext());
    }
}

