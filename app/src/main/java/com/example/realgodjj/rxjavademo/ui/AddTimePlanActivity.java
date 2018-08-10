package com.example.realgodjj.rxjavademo.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.base.BaseSubscriber;
import com.example.realgodjj.rxjavademo.App;
import com.example.realgodjj.rxjavademo.widget.CustomDatePicker;

import org.reactivestreams.Subscription;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ch.ielse.view.SwitchView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddTimePlanActivity extends BaseActivity implements View.OnClickListener {
    private Button btCancel, btSave, btStartTime, btEndTime, btMore;
    private ImageView ivLocation, ivAllDay;
    private LinearLayout llStart, llEnd, llReminder;
    private EditText etTitle, etLocation, etAllDay, etStartTime, etEndTime, etContext;
    private SwitchView switchView;
    private String nowTime, startTime, endTime;

    private static Calendar startDateTime, endDateTime;
    private static boolean isValidEndDate = true;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_add_time_plan);
        initViews();
        initListeners();
        initDatePicker();
        judgeTime();
    }

    //调整校验到正确的时间
    private void judgeTime() {
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
                        if (!isValidEndDate()) {//时间不合法，只校正结束的时间
                            startTime = etStartTime.getText().toString();
                            String endDate = startTime.split(" ")[0];
                            String endHour;
                            if (Integer.parseInt(startTime.split(" ")[1].split(":")[0]) == 23) {
                                endHour = "00";
                                //TODO:day plus one
                            } else if (Integer.parseInt(startTime.split(" ")[1].split(":")[0]) + 1 < 10)
                                endHour = "0" + String.valueOf(Integer.parseInt(startTime.split(" ")[1].split(":")[0]) + 1);
                            else
                                endHour = String.valueOf(Integer.parseInt(startTime.split(" ")[1].split(":")[0]) + 1);
                            String endMinute = startTime.split(" ")[1].split(":")[1];
                            endTime = endDate + " " + endHour + ":" + endMinute;

                            //String turn into Calendar
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                            try {
                                startDateTime.setTime(sdf.parse(startTime));
                                endDateTime.setTime(sdf.parse(endTime));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            etStartTime.setText(startTime);
                            etEndTime.setText(endTime);
                        } else {//时间合法则正常赋予时间
                            startTime = etStartTime.getText().toString();
                            endTime = etEndTime.getText().toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                            try {
                                startDateTime.setTime(sdf.parse(startTime));
                                endDateTime.setTime(sdf.parse(endTime));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            etStartTime.setText(startTime);
                            etEndTime.setText(endTime);
                        }
                    }
                });
    }


    @Override
    public void initViews() {
        super.initViews();
        Toolbar toolbar = findViewById(R.id.activity_add_time_plan_top_bar);
        initToolBar(toolbar, "", false);

        btCancel = findViewById(R.id.bt_cancel_plan);
        btSave = findViewById(R.id.bt_add_plan);
        btStartTime = findViewById(R.id.bt_start_time);
        btEndTime = findViewById(R.id.bt_end_time);
        btMore = findViewById(R.id.bt_more);

        ivLocation = findViewById(R.id.iv_location);
        ivAllDay = findViewById(R.id.iv_all_day);

        etTitle = findViewById(R.id.et_plan_title);
        etLocation = findViewById(R.id.et_location);
        etAllDay = findViewById(R.id.et_all_day);
        etStartTime = findViewById(R.id.et_start);
        etEndTime = findViewById(R.id.et_end);
        etContext = findViewById(R.id.et_context);

        switchView = findViewById(R.id.sv_all_day);
        llStart = findViewById(R.id.ll_start);
        llEnd = findViewById(R.id.ll_end);
        llReminder = findViewById(R.id.ll_reminder);

        etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(""))
                    ivLocation.setImageResource(R.drawable.location_light);
                else
                    ivLocation.setImageResource(R.drawable.location_dark);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initListeners() {
        super.initListeners();
        btCancel.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btStartTime.setOnClickListener(this);
        btEndTime.setOnClickListener(this);
        btMore.setOnClickListener(this);
        switchView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_cancel_plan:
                finish();
                break;

            case R.id.bt_add_plan:
                if (etTitle.getText().toString().equals(""))
                    toast(R.string.null_title);
                else if (etLocation.getText().toString().equals(""))
                    toast(R.string.null_location);
                else if (etContext.getText().toString().equals(""))
                    toast(R.string.null_context);
                else {
                    Intent intent = new Intent();
                    intent.putExtra("title", etTitle.getText().toString())
                            .putExtra("location", etLocation.getText().toString())
                            .putExtra("context", etContext.getText().toString())
                            .putExtra("isAllDay", switchView.isOpened());
                    if (!switchView.isOpened()) {    //如果处于选择时段状态，需要传递开始和结束时间
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                        try {
                            intent.putExtra("startTime", simpleDateFormat.parse(etStartTime.getText().toString()))
                                    .putExtra("endTime", simpleDateFormat.parse(etEndTime.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    setResult(1, intent);//requestCode=1
                    finish();
                    toast(R.string.add_plan_success);
                }
                break;

            case R.id.sv_all_day:
                boolean isOpened = switchView.isOpened();
                if (isOpened) {
                    ivAllDay.setImageResource(R.drawable.all_day_light);
                    etAllDay.setText(R.string.all_day);
                    llStart.setVisibility(View.GONE);
                    llEnd.setVisibility(View.GONE);
                } else {
                    ivAllDay.setImageResource(R.drawable.all_day_dark);
                    etAllDay.setText(R.string.not_all_day);
                    llStart.setVisibility(View.VISIBLE);
                    llEnd.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.bt_start_time:
                setStartTime();
                break;

            case R.id.bt_end_time:
                setEndTime();
                break;

            case R.id.bt_more:
                llReminder.setVisibility(View.VISIBLE);
                btMore.setVisibility(View.GONE);
                break;
        }
    }

    //初始化开始时间为当前时间，并将结束时间暂时定为当前时间1小时后的时间
    private void initDatePicker() {
        Calendar startCalendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        nowTime = simpleDateFormat.format(startCalendar.getTime());
        String startDate = nowTime.split(" ")[0];
        String endHour;
        if (Integer.parseInt(nowTime.split(" ")[1].split(":")[0]) + 1 < 10)
            endHour = "0" + String.valueOf(Integer.parseInt(nowTime.split(" ")[1].split(":")[0]) + 1);
        else if (Integer.parseInt(nowTime.split(" ")[1].split(":")[0]) == 23)
            endHour = "00";
        else
            endHour = String.valueOf(Integer.parseInt(nowTime.split(" ")[1].split(":")[0]) + 1);
        String startMinute = nowTime.split(" ")[1].split(":")[1];
        endTime = startDate + " " + endHour + ":" + startMinute;
        etStartTime.setText(nowTime);
        etEndTime.setText(endTime);

        Calendar endCalendar = Calendar.getInstance();
        try {
            endCalendar.setTime(simpleDateFormat.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setStartDateTime(startCalendar);
        setEndDateTime(endCalendar);

    }

    private void setStartTime() {
        CustomDatePicker customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etStartTime.setText(time);
            }
        }, "2010-01-01 00:00", "2050-12-31 23:59");
//        customDatePicker1.setmBackTime(new CustomDatePicker.BackTime() {
//            @Override
//            public void onBackTime(Date backTime) {
//
//            }
//        });
        customDatePicker1.showSpecificTime(true);
        customDatePicker1.setIsLoop(false);
        customDatePicker1.show(etStartTime.getText().toString());
        App.isBeginTime = true;
    }

    private void setEndTime() {
        CustomDatePicker customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etEndTime.setText(time);
            }
        }, "2010-01-01 00:00", "2050-12-31 23:59");
        customDatePicker2.setmBackTime(new CustomDatePicker.BackTime() {
            @Override
            public void onBackTime(Date backTime) {

            }
        });
        customDatePicker2.showSpecificTime(true);
        customDatePicker2.setIsLoop(false);
        customDatePicker2.show(etEndTime.getText().toString());
        App.isBeginTime = false;
    }

    public static Calendar getStartDateTime() {
        return startDateTime;
    }

    public static void setStartDateTime(Calendar start) {
        startDateTime = start;
    }

    public static Calendar getEndDateTime() {
        return endDateTime;
    }

    public static void setEndDateTime(Calendar end) {
        endDateTime = end;
    }

    public static boolean isValidEndDate() {
        return isValidEndDate;
    }

    public static void setIsValidEndDate(boolean isValidEndDate) {
        AddTimePlanActivity.isValidEndDate = isValidEndDate;
    }
}
