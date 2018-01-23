package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;

import com.sineva.rosapidemo.R;

/**
 * Created by Eligah on 2017/10/11.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    public void onTourGuideClick(View view) {
        startActivity(new Intent(MainActivity.this, TourGuideActivity.class));
    }

    public void onAskSettingClick(View view) {
        startActivity(new Intent(MainActivity.this, AskSettingActivity.class));
    }

    public void onWelcomeSettingClick(View view) {
        startActivity(new Intent(MainActivity.this, WelcomeSettingActivity.class));
    }

    public void onBaseSettingClick(View view) {
        startActivity(new Intent(MainActivity.this, BaseSettingActivity.class));
    }

    public void onOperationClick(View view) {
        startActivity(new Intent(MainActivity.this, OperationActivity.class));
    }

    public void onHoldSpeak(View view) {

    }
}
