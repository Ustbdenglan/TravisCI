package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sineva.rosapidemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class InMotionActivity extends BaseActivity {

    @BindView(R.id.img_home)
    ImageView imgHome;
    @BindView(R.id.tv_mileage_info)
    TextView tvMileageInfo;
    @BindView(R.id.tv_move_speed_info)
    TextView tvMoveSpeedInfo;
    @BindView(R.id.tv_ultrasonic_info)
    TextView tvUltrasonicInfo;
    @BindView(R.id.tv_distance_info)
    TextView tvDistanceInfo;
    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.wake_up)
    LinearLayout wakeUp;
    @BindView(R.id.handle)
    LinearLayout handle;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_in_motion;
    }

    @Override
    protected void initView() {
       // imgStart.setVisibility(View.GONE);
    }

    @OnClick({R.id.rl_root,R.id.img_start, R.id.wake_up, R.id.handle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_root:
                Toast.makeText(InMotionActivity.this,"停止",Toast.LENGTH_SHORT).show();
                imgStart.setVisibility(View.VISIBLE);
            case R.id.img_start:
                Toast.makeText(InMotionActivity.this,"继续",Toast.LENGTH_SHORT).show();
                imgStart.setVisibility(View.GONE);
                break;
            case R.id.wake_up:
                Toast.makeText(InMotionActivity.this,"唤醒语音",Toast.LENGTH_SHORT).show();
                break;
            case R.id.handle:
                startActivity(new Intent(InMotionActivity.this,Handle2Activity.class));
                break;
        }
    }
}
