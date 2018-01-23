package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sineva.rosapidemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.handle)
    LinearLayout handle;
    @BindView(R.id.wake_up)
    LinearLayout wakeUp;
    @BindView(R.id.setting)
    LinearLayout setting;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.handle, R.id.wake_up, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.handle:
                startActivity(new Intent(HomeActivity.this, HandleActivity.class));
                break;
            case R.id.wake_up:
                Toast.makeText(this,"语音唤醒",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                break;
        }
    }
}
