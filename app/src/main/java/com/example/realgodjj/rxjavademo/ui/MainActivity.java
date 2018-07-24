package com.example.realgodjj.rxjavademo.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.realgodjj.rxjavademo.Adapter.MyFragmentPagerAdapter;
import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.ui.Fragment.FirstFragment;
import com.example.realgodjj.rxjavademo.ui.Fragment.SecondFragment;
import com.example.realgodjj.rxjavademo.ui.Fragment.ThirdFragment;
import com.example.realgodjj.rxjavademo.widget.CommonPopupWindow;
import com.example.realgodjj.rxjavademo.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    //    private PopupWindow topBarPopupWindow;
    private CommonPopupWindow commonPopupWindow;
    private NoScrollViewPager nsvpMain;
    private List<Fragment> fragmentList;
    //    private Fragment[] fragments;

    private FrameLayout frameClock, frameAlarmClock, frameTimer;
    private ImageView buttonPageClock, buttonPageAlarmClock, buttonPageTimer;
    private TextView textTitle, textClock, textAlarmClock, textTimer;
    private Button buttonMenu;   //右上角菜单按钮

    private static final int PAGE_CLOCK = 0, PAGE_ALARM_CLOCK = 1, PAGE_TIMER = 2;
    private static boolean isQuit = false;
    //onResult的码
    private static final int addActivityRequestCodeOfPage0 = 0, addActivityRequestCodeOfPage1 = 1,
            addActivityRequestCodeOfPage2 = 2;
    private Timer timer = new Timer();

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_main);
        initViews();
        setViewPagerEvent();
        initListeners();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void initViews() {
        super.initViews();
        //顶部标题
        toolbar = findViewById(R.id.activity_main_top_bar);
        initToolBar(toolbar, "", false);
        textTitle = findViewById(R.id.tv_top_title);

        //底部菜单栏布局
        frameClock = findViewById(R.id.fl_page_clock);
        frameAlarmClock = findViewById(R.id.fl_page_alarm_clock);
        frameTimer = findViewById(R.id.fl_page_timer);

        //底部菜单栏图形按钮
        buttonPageClock = findViewById(R.id.iv_page_clock);
        buttonPageAlarmClock = findViewById(R.id.iv_page_alarm_clock);
        buttonPageTimer = findViewById(R.id.iv_page_timer);

        //底部菜单栏文字设置
        textClock = findViewById(R.id.tv_page_clock);
        textAlarmClock = findViewById(R.id.tv_page_alarm_clock);
        textTimer = findViewById(R.id.tv_page_timer);

        //页面翻滚设置
        nsvpMain = findViewById(R.id.vp_main);

        //添加右上角菜单栏按钮
        buttonMenu = findViewById(R.id.bt_menu);

        fragmentList = new ArrayList<Fragment>();
//        fragments = new Fragment[]{new FirstFragment(), new SecondFragment()};


        fragmentList.add(new FirstFragment());
        fragmentList.add(new SecondFragment());
        fragmentList.add(new ThirdFragment());
        nsvpMain.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
    }

    @Override
    public void initListeners() {
        super.initListeners();
        frameClock.setOnClickListener(this);
        frameAlarmClock.setOnClickListener(this);
        frameTimer.setOnClickListener(this);
        buttonPageClock.setOnClickListener(this);
        buttonPageAlarmClock.setOnClickListener(this);
        buttonPageTimer.setOnClickListener(this);
        buttonMenu.setOnClickListener(this);
    }

    private void setViewPagerEvent() {
        nsvpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("Scrolled", "success");
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("Selected", "success");
                int currentItem = nsvpMain.getCurrentItem();
                switch (currentItem) {
                    case PAGE_CLOCK:
                        setPageClock();
                        break;

                    case PAGE_ALARM_CLOCK:
                        setPageAlarmClock();
                        break;

                    case PAGE_TIMER:
                        setPageTimer();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("ScrollStateChanged", "success");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_page_clock:
                setPageClock();
                break;

            case R.id.iv_page_alarm_clock:
                setPageAlarmClock();
                break;

            case R.id.iv_page_timer:
                setPageTimer();
                break;

            case R.id.fl_page_clock:
                setPageClock();
                break;

            case R.id.fl_page_alarm_clock:
                setPageAlarmClock();
                break;

            case R.id.fl_page_timer:
                setPageTimer();
                break;

            case R.id.bt_menu:
                setButtonMenu();
                break;

            default:
                break;
        }
    }

    public void setButtonMenu() {
        if (nsvpMain.getCurrentItem() == 0) {
            commonPopupWindow = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.popupwindow_menu)
                    .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setAnimationStyle(R.style.anim_down)
                    .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                        @Override
                        public void getChildView(View view, int layoutResId) {
                            switch (layoutResId) {
                                case R.layout.popupwindow_menu:
                                    TextView addTimePlan = view.findViewById(R.id.tv_add_time_plan);
                                    TextView addMemorialDay = view.findViewById(R.id.tv_add_memorial_day);
                                    addTimePlan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(MainActivity.this, AddTimePlanActivity.class);
                                            startActivityForResult(intent, 0);
                                            commonPopupWindow.dismiss();
                                        }
                                    });
                                    addMemorialDay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.e("TAG", "ADDMEMORIALDAY SUCESS!!!");
                                        }
                                    });
                                    break;
                            }
                        }
                    })
                    .setOutsideTouchable(true)
                    .create();
        } else if (nsvpMain.getCurrentItem() == 1) {
            toast(R.string.alarm_clock);
        } else {
            toast(R.string.timer);
        }

        commonPopupWindow.showAsDropDown(buttonMenu, -150, 24);
        commonPopupWindow.update();
    }

    private void setPageClock() {
        nsvpMain.setCurrentItem(0);
        buttonPageClock.setBackgroundResource(R.drawable.clock_open);
        frameClock.setBackgroundResource(R.color.bottom_util_color_selected);
        buttonPageAlarmClock.setBackgroundResource(R.drawable.alarm_clock_close);
        frameAlarmClock.setBackgroundResource(R.color.second_blue);
        buttonPageTimer.setBackgroundResource(R.drawable.timer_close);
        frameTimer.setBackgroundResource(R.color.second_blue);
        textTitle.setText(R.string.clock);
        textClock.setTextColor(getResources().getColor(R.color.white));
        textAlarmClock.setTextColor(getResources().getColor(R.color.black));
        textTimer.setTextColor(getResources().getColor(R.color.black));
    }

    private void setPageAlarmClock() {
        nsvpMain.setCurrentItem(1);
        buttonPageAlarmClock.setBackgroundResource(R.drawable.alarm_clock_open);
        frameAlarmClock.setBackgroundResource(R.color.bottom_util_color_selected);
        buttonPageClock.setBackgroundResource(R.drawable.clock_close);
        frameClock.setBackgroundResource(R.color.second_blue);
        buttonPageTimer.setBackgroundResource(R.drawable.timer_close);
        frameTimer.setBackgroundResource(R.color.second_blue);
        textTitle.setText(R.string.alarm_clock);
        textAlarmClock.setTextColor(getResources().getColor(R.color.white));
        textClock.setTextColor(getResources().getColor(R.color.black));
        textTimer.setTextColor(getResources().getColor(R.color.black));
    }

    private void setPageTimer() {
        nsvpMain.setCurrentItem(2);
        buttonPageTimer.setBackgroundResource(R.drawable.timer_open);
        frameTimer.setBackgroundResource(R.color.bottom_util_color_selected);
        buttonPageClock.setBackgroundResource(R.drawable.clock_close);
        frameClock.setBackgroundResource(R.color.second_blue);
        buttonPageAlarmClock.setBackgroundResource(R.drawable.alarm_clock_close);
        frameAlarmClock.setBackgroundResource(R.color.second_blue);
        textTitle.setText(R.string.timer);
        textTimer.setTextColor(getResources().getColor(R.color.white));
        textClock.setTextColor(getResources().getColor(R.color.black));
        textAlarmClock.setTextColor(getResources().getColor(R.color.black));
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == addActivityRequestCodeOfPage0) {
            setPageClock();
            if (resultCode == 1) {
                String title = data.getStringExtra("title");
                String location = data.getStringExtra("location");
                String context = data.getStringExtra("context");
                Log.e("TAG", title);
                Log.e("TAG", location);
                Log.e("TAG", context);
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                ((FirstFragment)fragmentList.get(0)).updataUI(title + location + context);
            }
        } else if (requestCode == addActivityRequestCodeOfPage1) {
            setPageAlarmClock();
        } else if (requestCode == addActivityRequestCodeOfPage2) {
            setPageTimer();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isQuit) {
                isQuit = true;
                toast(R.string.key_again_exit);
                TimerTask task;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                finish();
                System.exit(0);
            }
        }
        return true;
    }
}

