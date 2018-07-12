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
import android.widget.Toast;

import com.example.realgodjj.rxjavademo.Adapter.MyFragmentPagerAdapter;
import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.ui.Fragment.FirstFragment;
import com.example.realgodjj.rxjavademo.ui.Fragment.SecondFragment;
import com.example.realgodjj.rxjavademo.ui.Fragment.ThirdFragment;
import com.example.realgodjj.rxjavademo.utils.StatusBarUtils;
import com.example.realgodjj.rxjavademo.utils.TopBarPopupWindow;
import com.example.realgodjj.rxjavademo.widget.CommonPopupWindow;
import com.example.realgodjj.rxjavademo.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private CommonPopupWindow commonPopupWindow;
    private TopBarPopupWindow topBarPopupWindow;
    private NoScrollViewPager nsvpMain;
    private List<Fragment> fragmentList;
//    private Fragment[] fragments;

    //    private LinearLayout linearBottomUtil;
    private FrameLayout frameClock, frameAlarmClock, frameTimer;
    private ImageView buttonPageClock, buttonPageAlarmClock, buttonPageTimer;
    private TextView textTitle, textClock, textAlarmClock, textTimer;
    private Button buttonMenu;   //右上角菜单按钮

    private static final int PAGE_CLOCK = 0, PAGE_ALARM_CLOCK = 1, PAGE_TIMER = 2;
    private static boolean isQuit = false;
    private static int i = 1;
    //onResult的码
    private static final int addActivityRequestCodeOfPage0 = 0, addActivityRequestCodeOfPage1 = 1,
            addActivityRequestCodeOfPage2 = 2;
    private Timer timer = new Timer();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorPrimaryDark);
        initViews();
        setViewPagerEvent();
        initListeners();
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void initViews() {
        //顶部标题
        Toolbar toolbar = findViewById(R.id.top_bar);
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
        frameClock.setOnClickListener(this);
        frameAlarmClock.setOnClickListener(this);
        frameTimer.setOnClickListener(this);
        buttonPageClock.setOnClickListener(this);
        buttonPageAlarmClock.setOnClickListener(this);
        buttonPageTimer.setOnClickListener(this);
        buttonMenu.setOnClickListener(this);
    }

    private void menuDown() {
        toast("MENUDOWN!!!");
        if (topBarPopupWindow == null) {
            topBarPopupWindow = new TopBarPopupWindow(MainActivity.this, this,  360, 290);
            topBarPopupWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        topBarPopupWindow.dismiss();
                    }
                }
            });
        }
        topBarPopupWindow.setFocusable(true);
        topBarPopupWindow.showAsDropDown(buttonMenu, 0, 0);
        topBarPopupWindow.update();
        Log.e("TAG", "It's my Menu!!!");
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

    private void setButtonMenu() {
        if (i % 2 == 1) {
            buttonMenu.setBackgroundResource(R.drawable.menu_up);
            menuDown();
            i++;
        } else if (i % 2 == 0) {
            buttonMenu.setBackgroundResource(R.drawable.menu_down);
            i--;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == addActivityRequestCodeOfPage0) {
            setPageClock();
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
                Toast.makeText(getApplicationContext(), "再按一次回退键退出程序", Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void getChildView(View view, int layoutResId) {
//        //获得PopupWindow布局里的View
////        view = findViewById(R.id.top_bar);
//        switch (layoutResId) {
//            case R.layout.popupwindow_menu:
//                TextView addTimePlan = view.findViewById(R.id.tv_add_time_plan);
//                TextView addMemorialDay = view.findViewById(R.id.tv_add_memorial_day);
//                addTimePlan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.e("TAG", "ADDTIMEPLAN SUCCESS!!!!");
//                    }
//                });
//                addMemorialDay.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.e("TAG", "ADDMEMORIALDAY SUCESS!!!");
//                    }
//                });
//                break;
//        }
//    }
}

