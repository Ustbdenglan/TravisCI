package com.sineva.rosapidemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.SetConstants;
import com.sineva.rosapidemo.utilcode.util.SPUtils;
import com.sineva.rosapidemo.view.ConfirmDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class HandleActivity extends BaseActivity {

    @BindView(R.id.lin_begin_guide)
    LinearLayout linBeginGuide;
    @BindView(R.id.lin_begin_guest)
    LinearLayout linBeginGuest;
    @BindView(R.id.lin_self_recharge)
    LinearLayout linSelfRecharge;
    @BindView(R.id.lin_lidar)
    LinearLayout linLadar;
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
    @BindView(R.id.nine_layout)
    LinearLayout nineLayout;
    @BindView(R.id.lin_single_control)
    LinearLayout linSingleControl;
    @BindView(R.id.lin_led_test)
    LinearLayout linLedTest;
    @BindView(R.id.iv_led_color_test)
    ImageView ivLedColorTest;
    @BindView(R.id.tv_led_color_test)
    TextView tvLedColorTest;
    @BindView(R.id.lin_target_check)
    LinearLayout linTargetCheck;
    @BindView(R.id.lin_devmode)
    LinearLayout linDevMode;
    private boolean isDecMode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_handle;
    }

    @Override
    protected void initView() {
        isDecMode = SPUtils.getInstance().getBoolean(SetConstants.IS_DEV_MODE);
        if(isDecMode){
            linDevMode.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.lin_begin_guide, R.id.lin_begin_guest, R.id.lin_self_recharge, R.id.lin_lidar, R.id.lin_head_control, R.id.lin_bottom_control, R.id.lin_voice_reset, R.id.lin_map_display, R.id.lin_shutdown, R.id.nine_layout, R.id.lin_single_control, R.id.lin_led_test, R.id.lin_target_check, R.id.lin_devmode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_begin_guide:
                startActivity(new Intent(HandleActivity.this,InitialPointActivity.class));
                break;
            case R.id.lin_begin_guest:
                startActivity(new Intent(HandleActivity.this,InitialPointActivity.class));
                break;
            case R.id.lin_self_recharge:
                startActivity(new Intent(HandleActivity.this,ChargeActivity.class));
                break;
            case R.id.lin_lidar:
                startActivity(new Intent(HandleActivity.this,LidarActivity.class));
                break;
            case R.id.lin_head_control:
                startActivity(new Intent(HandleActivity.this,HeadControlActivity.class));
                break;
            case R.id.lin_bottom_control:
                startActivity(new Intent(HandleActivity.this,BottomControlActivity.class));
                break;
            case R.id.lin_voice_reset:
                Toast.makeText(HandleActivity.this,"语音复位",Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_map_display:
                startActivity(new Intent(HandleActivity.this,MapActivity.class));
                break;
            case R.id.lin_shutdown:
                new ConfirmDialog(this)
                        .setClickListener("您确定要关机吗？", new ConfirmDialog.OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                Toast.makeText(HandleActivity.this,"开始关机",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.lin_single_control:
                startActivity(new Intent(HandleActivity.this,SingleControlActivity.class));
                break;
            case R.id.lin_led_test:
                ledColorTest();
                break;
            case R.id.lin_target_check:
                startActivity(new Intent(HandleActivity.this,TargetCheckActivity.class));
                break;
        }
    }

    int count=0;
    /**
     * 灯带测试
     */
    private void ledColorTest() {
        int[] colorArr={Color.BLUE,Color.YELLOW,Color.GREEN,Color.RED};
        if (count==colorArr.length){
            count=0;
        }
        ivLedColorTest.setImageDrawable(getTintDrawable(HandleActivity.this, R.drawable.icon_led, colorArr[count]));
        tvLedColorTest.setTextColor(colorArr[count]);
        count++;

    }

    private Drawable getTintDrawable(Context context, int image, int color) {
        Drawable drawable = ContextCompat.getDrawable(context, image);
        Drawable.ConstantState constantState = drawable.getConstantState();
        Drawable newDrawable = DrawableCompat.wrap(constantState == null ? drawable : constantState.newDrawable()).mutate();
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }
}
