package com.sineva.rosapidemo.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import com.sineva.rosapidemo.utils.Utils;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        initCurrentView();

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
        mLvButtonPressedTime = findViewById(R.id.lv_button_pressed_time);
        mScrollView = findViewById(R.id.scrollView);

        mTvLeftWheelVelocity = (TextView) findViewById(R.id.tv_left_wheel_velocity);
        mTvRightWheelVelocity = (TextView) findViewById(R.id.tv_right_wheel_velocity);

        mTvPositionX = findViewById(R.id.tv_positionx);
        mTvPositionY = findViewById(R.id.tv_positiony);
        mTvPositionZ = findViewById(R.id.tv_positionz);
        mTvOrientationX = findViewById(R.id.tv_orientationx);
        mTvOrientationY = findViewById(R.id.tv_orientationy);
        mTvOrientationZ = findViewById(R.id.tv_orientationz);
        mTvOrientationW = findViewById(R.id.tv_orientationw);
    }

    private void showData() {

        AimrPowerState aimrPowerState = mRosApiClientInstance.getAimrPowerState();
        if (null != aimrPowerState) {
            tvBatteryVoltage.setText(String.valueOf(aimrPowerState.msg.data.discharge_voltage) + "V");
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
        startActivity(new Intent(ChooseActivity.this, MoveBaseActivity.class));
    }

    public void onHeadClick(View view) {
        startActivity(new Intent(ChooseActivity.this, HeadActivity.class));
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
            mList.add(Utils.parseTime(time));
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
