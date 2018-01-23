package com.sineva.rosapidemo.activity;

import android.os.Bundle;

import com.sineva.rosapidemo.R;

/**
 * Created by Eligah on 2017/10/12.
 */

public class ChooseSceneActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_scene);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }
}
