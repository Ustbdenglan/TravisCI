package com.sineva.rosapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.LedState;

import java.util.Timer;
import java.util.TimerTask;


public class LedActivity extends Activity {

    private TextView tvLedState;

    private RosApiClient mRosApiClientInstance;
    private LedState ledState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);

        tvLedState = (TextView) findViewById(R.id.tv_led);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showData();
            }
        }, 0, 50);
    }

    private void showData() {
        ledState = mRosApiClientInstance.getLedState();
        if (null != ledState) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvLedState.setText("LedState = " + ledState.msg.data);
                }
            });
        }
    }
}
