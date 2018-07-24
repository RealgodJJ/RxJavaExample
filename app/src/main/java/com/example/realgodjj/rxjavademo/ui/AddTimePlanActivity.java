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

import ch.ielse.view.SwitchView;

public class AddTimePlanActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button btCancel, btSave, btMore;
    private ImageView ivLocation, ivAllDay;
    private LinearLayout llStart, llEnd, llReminder;
    private EditText etTitle, etLocation, etAllDay, etContext;
    private SwitchView switchView;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_add_time_plan);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        super.initViews();
        toolbar = findViewById(R.id.activity_add_time_plan_top_bar);
        initToolBar(toolbar, "", false);

        btCancel = findViewById(R.id.bt_cancel_plan);
        btSave = findViewById(R.id.bt_add_plan);
        btMore = findViewById(R.id.bt_more);

        ivLocation = findViewById(R.id.iv_location);
        ivAllDay = findViewById(R.id.iv_all_day);

        etTitle = findViewById(R.id.et_plan_title);
        etLocation = findViewById(R.id.et_location);
        etAllDay = findViewById(R.id.et_all_day);
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

        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    public void initListeners() {
        super.initListeners();
        btCancel.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btMore.setOnClickListener(this);
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
                    setResult(1, intent);//requestCode=1
                    finish();
                    toast(R.string.add_plan_success);
                }
                break;
            case R.id.bt_more:
                llReminder.setVisibility(View.VISIBLE);
                btMore.setVisibility(View.GONE);
                break;
        }
    }
}
