package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.view.ToolbarControl;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.lin_guide)
    LinearLayout linGuide;
    @BindView(R.id.lin_guest)
    LinearLayout linGuest;
    @BindView(R.id.lin_aq)
    LinearLayout linAq;
    @BindView(R.id.lin_map)
    LinearLayout linMap;
    @BindView(R.id.lin_basic)
    LinearLayout linBasic;
    @BindView(R.id.toolbar)
    ToolbarControl toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        toolbar.setBackButtonOnClickListerner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @OnClick({R.id.lin_guide, R.id.lin_guest, R.id.lin_aq, R.id.lin_map, R.id.lin_basic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_guide:
                startActivity(new Intent(SettingActivity.this, GuideSettingActivity.class));
                break;
            case R.id.lin_guest:
                startActivity(new Intent(SettingActivity.this, GuestSettingActivity.class));
                break;
            case R.id.lin_aq:
                startActivity(new Intent(SettingActivity.this, AskSettingActivity.class));
                break;
            case R.id.lin_map:
                startActivity(new Intent(SettingActivity.this, MapSettingActivity.class));
                break;
            case R.id.lin_basic:
                startActivity(new Intent(SettingActivity.this, BaseSettingActivity.class));
                break;
        }
    }

}
