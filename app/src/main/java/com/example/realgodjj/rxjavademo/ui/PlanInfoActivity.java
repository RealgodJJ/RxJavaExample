package com.example.realgodjj.rxjavademo.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.utils.TimePlan;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PlanInfoActivity extends BaseActivity implements View.OnClickListener {

    private Button btReturn, btEdit;
    private LinearLayout llDay, llStart, llEnd;
    private EditText etPlanTheme, etLocation, etDay, etStartTime, etEndTime, etContext;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_plan_info);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        super.initViews();
        Toolbar toolbar = findViewById(R.id.activity_plan_info_top_bar);
        initToolBar(toolbar, "", false);

        llDay = findViewById(R.id.ll_day);
        llStart = findViewById(R.id.ll_start);
        llEnd = findViewById(R.id.ll_end);

        btReturn = findViewById(R.id.bt_return);
        btEdit = findViewById(R.id.bt_edit);

        etPlanTheme = findViewById(R.id.et_plan_theme);
        etLocation = findViewById(R.id.et_location);
        etDay = findViewById(R.id.et_day);
        etStartTime = findViewById(R.id.et_start);
        etEndTime = findViewById(R.id.et_end);
        etContext = findViewById(R.id.et_context);

        //拉取时间计划的相关信息
        Intent intent = getIntent();
        TimePlan timePlan = (TimePlan) intent.getSerializableExtra("timePlan");
        etPlanTheme.setText(timePlan.getTheme() != null ? timePlan.getTheme() : "");
        etLocation.setText(timePlan.getLocation() != null ? timePlan.getLocation() : "");
        if (timePlan.isAllDay()) {
            llDay.setVisibility(View.VISIBLE);
            llStart.setVisibility(View.GONE);
            llEnd.setVisibility(View.GONE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            etDay.setText(timePlan.getDate() != null ? simpleDateFormat.format(timePlan.getDate()) : "");
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            etStartTime.setText(timePlan.getStartDateTime() != null ?
                    simpleDateFormat.format(timePlan.getStartDateTime()) : "");
            etEndTime.setText(timePlan.getStartDateTime() != null ?
                    simpleDateFormat.format(timePlan.getEndDateTime()) : "");
        }
        etContext.setText(timePlan.getContext() != null ? timePlan.getContext() : "");
    }

    @Override
    public void initListeners() {
        super.initListeners();
        btReturn.setOnClickListener(this);
        btEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;

            case R.id.bt_edit:
                etPlanTheme.setEnabled(true);
                etLocation.setEnabled(true);
                etDay.setEnabled(true);
                etStartTime.setEnabled(true);
                etEndTime.setEnabled(true);
                etContext.setEnabled(true);
                break;
        }
    }
}
