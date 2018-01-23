package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sineva.rosapidemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class Handle2Activity extends BaseActivity {

    @BindView(R.id.lin_stop)
    LinearLayout linStop;
    @BindView(R.id.lin_head_control)
    LinearLayout linHeadControl;
    @BindView(R.id.lin_bottom_control)
    LinearLayout linBottomControl;
    @BindView(R.id.lin_voice_reset)
    LinearLayout linVoiceReset;
    @BindView(R.id.lin_map_display)
    LinearLayout linMapDisplay;
    @BindView(R.id.lin_shutdown)
    LinearLayout linShutdown;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_handle2;
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.lin_stop, R.id.lin_head_control, R.id.lin_bottom_control, R.id.lin_voice_reset, R.id.lin_map_display, R.id.lin_shutdown})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_stop:
                Toast.makeText(Handle2Activity.this,"停止",Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_head_control:
                startActivity(new Intent(Handle2Activity.this,HeadControlActivity.class));
                break;
            case R.id.lin_bottom_control:
                startActivity(new Intent(Handle2Activity.this,BottomControlActivity.class));
                break;
            case R.id.lin_voice_reset:
                Toast.makeText(Handle2Activity.this,"语音复位",Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_map_display:
                startActivity(new Intent(Handle2Activity.this,MapActivity.class));
                break;
            case R.id.lin_shutdown:
                Toast.makeText(Handle2Activity.this,"关机",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
