package com.sineva.rosapidemo.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.roslibrary.ros.message.AimrPowerState;
import com.roslibrary.ros.message.Button;
import com.roslibrary.ros.message.JointState;
import com.roslibrary.ros.message.Led;
import com.roslibrary.ros.message.Odometry;
import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class ChooseActivity extends BaseActivity {

    private TextView tvFrontSonarDistance;
    private TextView tvBatteryVoltage;
    private TextView tvBackSonarDistance;
    private ImageView mIvLedState;

    private TextView mTvButtonPushed;

    private boolean isButtonPressed;

    private ListView mLvButtonPressedTime;

    private List<String> mList;

    private ScrollView mScrollView;

    private TextView mTvLeftWheelVelocity;
    private TextView mTvRightWheelVelocity;

    private TextView mTvPositionX;
    private TextView mTvPositionY;
    private TextView mTvPositionZ;
    private TextView mTvOrientationX;
    private TextView mTvOrientationY;
    private TextView mTvOrientationZ;
    private TextView mTvOrientationW;

    private String[] mTopicArray = {"/aimr_power/state", "/aimr_power/btn_state", "/aimr_power/led_state", "/joint_states_throttle", "/mobile_base_controller/odom"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @Override
    protected void initView() {
        initCurrentView();
    }

    @Override
    protected void initData() {
        if (mRosApiClientInstance != null) {
            mRosApiClientInstance.subscribeTopic(mTopicArray);
        }

        if (mTimer != null) {
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showData();
                        }
                    });
                }
            }, 0, 50);
        }
    }

    private void initCurrentView() {
        tvBatteryVoltage = (TextView) findViewById(R.id.tv_battery_voltage);
        tvFrontSonarDistance = (TextView) findViewById(R.id.tv_front_sonar);
        tvBackSonarDistance = (TextView) findViewById(R.id.tv_back_sonar);

        mIvLedState = (ImageView) findViewById(R.id.iv_led);

        mTvButtonPushed = (TextView) findViewById(R.id.tv_chest_button);
        mLvButtonPressedTime = (ListView) findViewById(R.id.lv_button_pressed_time);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);

        mTvLeftWheelVelocity = (TextView) findViewById(R.id.tv_left_wheel_velocity);
        mTvRightWheelVelocity = (TextView) findViewById(R.id.tv_right_wheel_velocity);

        mTvPositionX = (TextView) findViewById(R.id.tv_positionx);
        mTvPositionY = (TextView) findViewById(R.id.tv_positiony);
        mTvPositionZ = (TextView) findViewById(R.id.tv_positionz);
        mTvOrientationX = (TextView) findViewById(R.id.tv_orientationx);
        mTvOrientationY = (TextView) findViewById(R.id.tv_orientationy);
        mTvOrientationZ = (TextView) findViewById(R.id.tv_orientationz);
        mTvOrientationW = (TextView) findViewById(R.id.tv_orientationw);
    }

    private void showData() {

        AimrPowerState aimrPowerState = mRosApiClientInstance.getAimrPowerState();
        if (null != aimrPowerState) {
            tvBatteryVoltage.setText(String.valueOf(
                    getBatteryLever(aimrPowerState.msg.data.discharge_voltage)) + "%");
            tvFrontSonarDistance.setText(String.valueOf(aimrPowerState.msg.data.sonar.get(1)) + "cm");
            tvBackSonarDistance.setText(String.valueOf(aimrPowerState.msg.data.sonar.get(3)) + "cm");
        }

        Led ledState = mRosApiClientInstance.getLedState();
        if (null != ledState) {

            int imageResource = 0;

            switch (ledState.msg.data) {
                case 1:
                    imageResource = R.mipmap.blue;
                    break;
                case 2:
                    imageResource = R.mipmap.red;
                    break;
                case 3:
                    imageResource = R.mipmap.green;
                    break;
            }
            mIvLedState.setImageResource(imageResource);

            Button chestButtonState = mRosApiClientInstance.getChestButtonState();
            if (null != chestButtonState) {
                isButtonPressed = chestButtonState.msg.data;
                if (isButtonPressed) {
                    ContentValues values = new ContentValues();
                    values.put("time", System.currentTimeMillis());
                    mWritableDatabase.insert("button", "", values);
                }

                mTvButtonPushed.setText(String.valueOf(isButtonPressed));
            }
        }

        JointState wheelState = mRosApiClientInstance.getWheelState();
        if (null != wheelState) {
            mTvLeftWheelVelocity.setText(String.valueOf(wheelState.msg.velocity.get(0)) + "m/s");
            mTvRightWheelVelocity.setText(String.valueOf(wheelState.msg.velocity.get(1)) + "m/s");
        }

        Odometry moveBaseData = mRosApiClientInstance.getMoveBaseData();
        if (null != moveBaseData) {
            mTvPositionX.setText("X : " + String.valueOf(moveBaseData.msg.pose.pose.position.x));
            mTvPositionY.setText("Y : " + String.valueOf(moveBaseData.msg.pose.pose.position.y));
            mTvPositionZ.setText("Z : " + String.valueOf(moveBaseData.msg.pose.pose.position.z));
            mTvOrientationX.setText("X : " + String.valueOf(moveBaseData.msg.pose.pose.orientation.x));
            mTvOrientationY.setText("Y : " + String.valueOf(moveBaseData.msg.pose.pose.orientation.y));
            mTvOrientationZ.setText("Z : " + String.valueOf(moveBaseData.msg.pose.pose.orientation.z));
            mTvOrientationW.setText("W : " + String.valueOf(moveBaseData.msg.pose.pose.orientation.w));
        }
    }

    public void onMoveBaseClick(View view) {
        startActivity(new Intent(ChooseActivity.this, BottomControlActivity.class));
    }

    public void onHeadClick(View view) {
        startActivity(new Intent(ChooseActivity.this, HeadControlActivity.class));
    }

    public void onLidarClick(View view) {
        startActivity(new Intent(ChooseActivity.this, LidarActivity.class));
    }

    public void onMapClick(View view) {
        startActivity(new Intent(ChooseActivity.this, MapActivity.class));
    }

    public void onCameraClick(View view) {
        startActivity(new Intent(ChooseActivity.this, CameraActivity.class));
    }


    private int getBatteryLever(double batteryVoltage) {
        int batteryLever = 0;
        if (batteryVoltage >= 28) {
            batteryLever = 100;
        } else if (batteryVoltage >= 27.5 && batteryVoltage < 28) {
            batteryLever = 90;
        } else if (batteryVoltage >= 27 && batteryVoltage < 27.5) {
            batteryLever = 80;
        } else if (batteryVoltage >= 26.5 && batteryVoltage < 27) {
            batteryLever = 70;
        } else if (batteryVoltage >= 26 && batteryVoltage < 26.5) {
            batteryLever = 60;
        } else if (batteryVoltage >= 25 && batteryVoltage < 26) {
            batteryLever = 50;
        } else if (batteryVoltage >= 24 && batteryVoltage < 25) {
            batteryLever = 40;
        } else if (batteryVoltage >= 22.3 && batteryVoltage < 24) {
            batteryLever = 30;
        } else if (batteryVoltage >= 21.5 && batteryVoltage < 22.3) {
            batteryLever = 20;
        } else if (batteryVoltage >= 21 && batteryVoltage < 21.5) {
            batteryLever = 15;
        } else if (batteryVoltage >= 20.5 && batteryVoltage < 21) {
            batteryLever = 10;
        } else if (batteryVoltage >= 20.3 && batteryVoltage < 20.5) {
            batteryLever = 5;
        } else {
            //电量不足，即将关机
        }
        return batteryLever;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mRosApiClientInstance != null) {
            mRosApiClientInstance.unSubscribeTopic(mTopicArray);
            mRosApiClientInstance.shutDownClient();
        }

        if (mWritableDatabase != null) {
            mWritableDatabase.close();
        }
    }

    @Override
    public void onBackPressed() {
        if (mLvButtonPressedTime.getVisibility() == View.VISIBLE) {
            mLvButtonPressedTime.setVisibility(View.GONE);
            mScrollView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    public void onQueryClick(View view) {
        mList = new ArrayList<>();
        Cursor cursor = mWritableDatabase.query("button", new String[]{"time"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            long time = cursor.getLong(cursor.getColumnIndex("time"));
            mList.add(CommonUtils.parseTime(time));
        }
        mLvButtonPressedTime.setVisibility(View.VISIBLE);
        mScrollView.setVisibility(View.GONE);
        mLvButtonPressedTime.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int position) {
                return mList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder = null;

                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(ChooseActivity.this).inflate(R.layout.item_button_pressed_time, null);
                    holder.tvButtonPressedTime = convertView.findViewById(R.id.tv_button_pressed_time);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.tvButtonPressedTime.setText(mList.get(position));

                return convertView;
            }
        });
    }

    static class ViewHolder {
        public TextView tvButtonPressedTime;
    }
}
