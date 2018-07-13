package com.example.realgodjj.rxjavademo.ui;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.realgodjj.rxjavademo.R;

public class AddTimePlanActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button btCancel, btSave;

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

    }

    @Override
    public void initListeners() {
        super.initListeners();
        btCancel.setOnClickListener(this);
        btSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.bt_cancel_plan:
                finish();
                Log.e("TAG", "FUCKKKKKKKKKKKKKKKKKK!");
                break;
            case R.id.bt_add_plan:
//                toast("");
                Toast.makeText(this, R.string.add_plan_success, Toast.LENGTH_SHORT).show();
        }
    }
}
