package com.sineva.rosapidemo;

import android.os.Bundle;
import android.widget.TextView;

import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.WheelState;

import java.util.Timer;
import java.util.TimerTask;


public class WheelStateActivity extends MainActivity {

    private TextView mTvLeftWheelVelocity;
    private TextView mTvRightWheelVelocity;

    private RosApiClient mRosApiClientInstance;

    private WheelState mWheelState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_state);

        mTvLeftWheelVelocity = (TextView) findViewById(R.id.tv_left_wheel_velocity);
        mTvRightWheelVelocity = (TextView) findViewById(R.id.tv_right_wheel_velocity);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showVelocity();
            }
        }, 3000, 50);
    }

    private void showVelocity() {
        mWheelState = mRosApiClientInstance.getWheelState();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvLeftWheelVelocity.setText(String.valueOf(mWheelState.msg.velocity.get(0)));
                mTvRightWheelVelocity.setText(String.valueOf(mWheelState.msg.velocity.get(1)));
            }
        });
    }
}
