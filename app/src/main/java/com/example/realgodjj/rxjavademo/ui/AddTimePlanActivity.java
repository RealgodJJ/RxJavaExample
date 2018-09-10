package com.example.realgodjj.rxjavademo.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.base.BaseSubscriber;
import com.example.realgodjj.rxjavademo.App;
import com.example.realgodjj.rxjavademo.utils.MaxInputTextWatcher;
import com.example.realgodjj.rxjavademo.utils.TimePlan;
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
    private Button btCancel, btSave, btDay, btStartTime, btEndTime, btMore;
    private ImageView ivLocation, ivAllDay;
    private LinearLayout llDay, llStart, llEnd, llReminder;
    private EditText etTheme, etLocation, etAllDay, etDay, etStartTime, etEndTime, etContext;
    private SwitchView switchView;
    private String startTime;
    private String endTime;

    private static Calendar chooseDate, startDateTime, endDateTime;
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
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                        if (!isValidEndDate()) {//时间不合法，只校正结束的时间
                            startTime = etStartTime.getText().toString();
                            //结束时间比开始时间多一个小时
                            try {
                                startDateTime.setTime(sdf.parse(startTime));
                                endDateTime.setTime(sdf.parse(startTime));
                                endDateTime.add(Calendar.HOUR, 1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            etStartTime.setText(startTime);
                            etEndTime.setText(sdf.format(endDateTime.getTime()));
                        } else {//时间合法则正常赋予时间
                            startTime = etStartTime.getText().toString();
                            endTime = etEndTime.getText().toString();
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
        btDay = findViewById(R.id.bt_day);
        btStartTime = findViewById(R.id.bt_start_time);
        btEndTime = findViewById(R.id.bt_end_time);
        btMore = findViewById(R.id.bt_more);

        ivLocation = findViewById(R.id.iv_location);
        ivAllDay = findViewById(R.id.iv_all_day);

        etTheme = findViewById(R.id.et_plan_theme);
        etLocation = findViewById(R.id.et_location);
        etAllDay = findViewById(R.id.et_all_day);
        etDay = findViewById(R.id.et_day);
        etStartTime = findViewById(R.id.et_start);
        etEndTime = findViewById(R.id.et_end);
        etContext = findViewById(R.id.et_context);

        switchView = findViewById(R.id.sv_all_day);
        llDay = findViewById(R.id.ll_day);
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
        btDay.setOnClickListener(this);
        btStartTime.setOnClickListener(this);
        btEndTime.setOnClickListener(this);
        btMore.setOnClickListener(this);
        switchView.setOnClickListener(this);
        etTheme.addTextChangedListener(new MaxInputTextWatcher(this, etTheme, 6));
        etLocation.addTextChangedListener(new MaxInputTextWatcher(this, etLocation, 8));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_cancel_plan:
                finish();
                break;

            case R.id.bt_add_plan:
                if (etTheme.getText().toString().equals(""))
                    toast(R.string.null_title);
                else if (etLocation.getText().toString().equals(""))
                    toast(R.string.null_location);
                else if (etContext.getText().toString().equals(""))
                    toast(R.string.null_context);
                else {
                    //TODO
                    Intent intent = new Intent();
                    intent.putExtra("theme", etTheme.getText().toString())
                            .putExtra("location", etLocation.getText().toString())
                            .putExtra("context", etContext.getText().toString())
                            .putExtra("isAllDay", switchView.isOpened());
                    SimpleDateFormat simpleDateFormat;
                    if (!switchView.isOpened()) {    //如果处于选择时段状态，需要传递开始和结束时间
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                        try {
                            intent.putExtra("startTime", simpleDateFormat.parse(etStartTime.getText().toString()))
                                    .putExtra("endTime", simpleDateFormat.parse(etEndTime.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            intent.putExtra("date", simpleDateFormat.parse(etDay.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    setResult(1, intent);//requestCode=1
                    finish();
                    toast(R.string.add_plan_success);
                }
                break;

//            case R.id.bt_add_plan:
//                if (etTheme.getText().toString().equals(""))
//                    toast(R.string.null_title);
//                else if (etLocation.getText().toString().equals(""))
//                    toast(R.string.null_location);
//                else if (etContext.getText().toString().equals(""))
//                    toast(R.string.null_context);
//                else {
//                    Intent intent = new Intent();
//                    String theme = etTheme.getText().toString();
//                    String location = etLocation.getText().toString();
//                    String context = etContext.getText().toString();
//                    TimePlan timePlan = null;
//                    SimpleDateFormat simpleDateFormat;
//                    if (switchView.isOpened()) {
//                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//                        try {
//                            Date date = simpleDateFormat.parse(etDay.getText().toString());
//                            timePlan = new TimePlan(theme, location, context,
//                                    switchView.isOpened(), date);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.getDefault());
//                        try {
//                            Date startTime = simpleDateFormat.parse(etStartTime.getText().toString());
//                            Date endTime = simpleDateFormat.parse(etEndTime.getText().toString());
//                            timePlan = new TimePlan(theme, location, context,
//                                    switchView.isOpened(), startTime, endTime);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    intent.putExtra("timePlan", timePlan);
//                    setResult(1, intent);//requestCode=1
//                    finish();
//                    toast(R.string.add_plan_success);
//                }
//                break;

            case R.id.sv_all_day:
                boolean isOpened = switchView.isOpened();
                if (isOpened) {
                    ivAllDay.setImageResource(R.drawable.all_day_light);
                    etAllDay.setText(R.string.all_day);
                    llDay.setVisibility(View.VISIBLE);
                    llStart.setVisibility(View.GONE);
                    llEnd.setVisibility(View.GONE);
                } else {
                    ivAllDay.setImageResource(R.drawable.all_day_dark);
                    etAllDay.setText(R.string.not_all_day);
                    llDay.setVisibility(View.GONE);
                    llStart.setVisibility(View.VISIBLE);
                    llEnd.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.bt_day:
                setDay();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.HOUR, 1);
        String nowTime = sdf.format(startCalendar.getTime());
        String nowTime1 = sdf1.format(startCalendar.getTime());
        setChooseDate(startCalendar);
        setStartDateTime(startCalendar);
        setEndDateTime(endCalendar);
        etDay.setText(nowTime1);
        etStartTime.setText(nowTime);
        etEndTime.setText(sdf.format(endCalendar.getTime()));
    }

    private void setDay() {
        CustomDatePicker customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                etDay.setText(time);
            }
        }, "2010-01-01", "2050-12-31");
        customDatePicker1.showSpecificTime(false);
        customDatePicker1.setIsLoop(true);
        customDatePicker1.show(etDay.getText().toString());
        App.isDate = true;
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
        customDatePicker1.setIsLoop(true);
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
//        customDatePicker2.setmBackTime(new CustomDatePicker.BackTime() {
//            @Override
//            public void onBackTime(Date backTime) {
//
//            }
//        });
        customDatePicker2.showSpecificTime(true);
        customDatePicker2.setIsLoop(true);
        customDatePicker2.show(etEndTime.getText().toString());
        App.isBeginTime = false;
    }

    public static Calendar getChooseDate() {
        return chooseDate;
    }

    public static void setChooseDate(Calendar chooseDate) {
        AddTimePlanActivity.chooseDate = chooseDate;
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
