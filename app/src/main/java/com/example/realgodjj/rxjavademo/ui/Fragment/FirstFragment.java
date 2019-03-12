package com.example.realgodjj.rxjavademo.ui.Fragment;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.realgodjj.rxjavademo.Adapter.MyRecycleViewAdapter;
import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.base.BaseSubscriber;
import com.example.realgodjj.rxjavademo.ui.PlanInfoActivity;
import com.example.realgodjj.rxjavademo.utils.SharedPreferencesUtil;
import com.example.realgodjj.rxjavademo.utils.TimePlan;
import com.example.realgodjj.rxjavademo.App;
import com.example.realgodjj.rxjavademo.widget.TimeKeeperView;

import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FirstFragment extends Fragment implements MyRecycleViewAdapter.OnRecycleViewItemListener {
    private TimeKeeperView timeKeeperView;
    private String timeShow;
    private TextView tvTimeShow;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss,yyyy/MM/dd  E", Locale.getDefault());
    private RecyclerView myRecyclerView;
    private MyRecycleViewAdapter myRecycleViewAdapter;
//    private TimePlan timePlanItem;//代表选中的时间计划选项

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        initListeners();
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
        myRecycleViewAdapter = new MyRecycleViewAdapter(App.timePlanList);
        myRecyclerView.setAdapter(myRecycleViewAdapter);
    }

    private void initListeners() {
        myRecycleViewAdapter.setOnRecycleViewItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        //实现页面切换并显示相关的备忘信息
        Toast.makeText(getContext(), getString(R.string.task, position), Toast.LENGTH_SHORT).show();
//        timePlanItem = myRecycleViewAdapter.getItem(position);
        Intent intent = new Intent();
        //使用TimePlan继承Serializable，将点击选中的TimePlan传输过去
        intent.putExtra("timePlan", myRecycleViewAdapter.getItem(position));
        intent.setClass(Objects.requireNonNull(getContext()), PlanInfoActivity.class);
        startActivity(intent);
    }

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
        spannable.setSpan(new AbsoluteSizeSpan(130), 0, 8,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 9, 19,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(50), 9, 19,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 19, 23,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(50), 19, 23,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvTimeShow.setText(spannable);
    }

    public void updateUI(TimePlan timePlan, SharedPreferencesUtil sharedPreferencesUtil) {
        App.timePlanList.add(timePlan);
        //至刷新单独一项条目
        myRecycleViewAdapter.notifyItemChanged(App.timePlanList.indexOf(timePlan));
        sharedPreferencesUtil.setListValue("timePlanList", App.timePlanList);
    }

    public List<TimePlan> refreshUI(SharedPreferencesUtil sharedPreferencesUtil) {
        return sharedPreferencesUtil.getListValue("timePlanList", TimePlan[].class);
    }
}

