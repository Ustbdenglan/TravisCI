package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.view.BottomMenuDialog;
import com.sineva.rosapidemo.view.SeekBarPopupWindow;
import com.sineva.rosapidemo.view.ToolbarControl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sin on 2017/10/12.
 */

public class BaseSettingActivity extends BaseActivity {

    private final String TAG = "BaseSettingActivity";
    @BindView(R.id.toorbar)
    ToolbarControl toorbar;
    @BindView(R.id.lin_bg_img)
    LinearLayout linBgImg;
    @BindView(R.id.lin_wifi_set)
    LinearLayout linWifiSet;
    @BindView(R.id.lin_charge_img_set)
    LinearLayout linChargeImgSet;
    @BindView(R.id.lin_led_set)
    LinearLayout linLedSet;
    @BindView(R.id.lin_move_speed)
    LinearLayout linMoveSpeed;
    @BindView(R.id.lin_rotate_speed)
    LinearLayout linRotateSpeed;
    @BindView(R.id.lin_broadcast_speed)
    LinearLayout linBroadcastSpeed;
    @BindView(R.id.lin_broadcast_volume)
    LinearLayout linBroadcastVolume;
    @BindView(R.id.lin_broadcast_tone)
    LinearLayout linBroadcastType;
    @BindView(R.id.lin_wakeup_time)
    LinearLayout linWakeupTime;
    @BindView(R.id.lin_coder_mode)
    LinearLayout linCoderMode;
    private static final int RESULT=100;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_setting;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.lin_bg_img, R.id.lin_wifi_set, R.id.lin_charge_img_set, R.id.lin_led_set, R.id.lin_move_speed, R.id.lin_rotate_speed, R.id.lin_broadcast_speed, R.id.lin_broadcast_volume, R.id.lin_broadcast_tone, R.id.lin_wakeup_time, R.id.lin_coder_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_bg_img:
                chooseBgImg();
                break;
            case R.id.lin_wifi_set:
                startActivity(new Intent(BaseSettingActivity.this,WifiSetActivity.class));
                break;
            case R.id.lin_charge_img_set:
                startActivity(new Intent(BaseSettingActivity.this,ChargeImgSetActivity.class));
                break;
            case R.id.lin_led_set:
                startActivity(new Intent(BaseSettingActivity.this,LedImgSetActivity.class));
                break;
            case R.id.lin_move_speed:
                openPop(view);
                break;
            case R.id.lin_rotate_speed:
                openPop(view);
                break;
            case R.id.lin_broadcast_speed:
                openPop(view);
                break;
            case R.id.lin_broadcast_volume:
                openPop(view);
                break;
            case R.id.lin_broadcast_tone :
                new BottomMenuDialog.BottomMenuBuilder()
                        .addItem("男声", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                        }).addItem("女声", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                        }).addItem("娃娃声", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                        }).addItem("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                        .build()
                        .show(getSupportFragmentManager());

                break;
            case R.id.lin_wakeup_time:
                new BottomMenuDialog.BottomMenuBuilder()
                        .addItem("5min", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).addItem("10min", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).addItem("15min", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).addItem("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .build()
                        .show(getSupportFragmentManager());

                break;
            case R.id.lin_coder_mode:
                startActivity(new Intent(BaseSettingActivity.this,DevModeActivity.class));
                break;
        }
    }

    private void openPop(View view) {
        SeekBarPopupWindow seekBarPopupWindow = new SeekBarPopupWindow(BaseSettingActivity.this,view, new SeekBarPopupWindow.OnSeekBarProgressChangeLintener() {
            @Override
            public int onSeekBarProgressChange(int progress, View view) {
                Log.e(TAG, progress + "===="+view.getId());
                return progress;
            }

            @Override
            public int onSeekBarProgressEnd(int progress, View view) {
                Log.e(TAG, progress + "----"+view.getId());
                return progress;
            }

        });
        setBackgroundAlpha(0.5f);
        seekBarPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.e(TAG, "onDismiss: qqqqqqq");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                setBackgroundAlpha(1.0f);
            }
        });
        seekBarPopupWindow.showAtLocation(findViewById(R.id.lin_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 打开相册选择图片
     */
    private void chooseBgImg() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT);
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
       /* WindowManager.LayoutParams lp = BaseSettingActivity.this.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);


        Activity mActivity = (Activity) mContext;*/
        Window window = BaseSettingActivity.this.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        //这里设置透明度
        windowParams.dimAmount = 0.5f;
        windowParams.alpha = bgAlpha;

        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        window.setAttributes(windowParams);
    }
}
