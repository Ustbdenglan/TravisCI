package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sineva.rosapidemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ChargeActivity extends BaseActivity {

    @BindView(R.id.iv_charge_img_bg)
    ImageView ivChargeImgBg;
    @BindView(R.id.handle)
    LinearLayout handle;
    @BindView(R.id.tv_wake_up)
    TextView tvWakeUp;
    @BindView(R.id.wake_up)
    LinearLayout wakeUp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.iv_charge_img_bg, R.id.handle, R.id.wake_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_charge_img_bg:
                break;
            case R.id.handle:
                startActivity(new Intent(ChargeActivity.this,Handle2Activity.class));
                break;
            case R.id.wake_up:
                Toast.makeText(ChargeActivity.this,"充电中",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
