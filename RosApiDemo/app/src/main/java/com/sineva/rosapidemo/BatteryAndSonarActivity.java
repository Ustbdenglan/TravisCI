package com.sineva.rosapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.roslibrary.ros.Constants;
import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.AimrPowerState;

import java.util.Timer;
import java.util.TimerTask;


public class BatteryAndSonarActivity extends Activity {

    private TextView tvFrontSonarDistance;
    private TextView tvBatteryVoltage;
    private TextView tvBackSonarDistance;

    private RosApiClient mRosApiClientInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_and_sonar);

        tvBatteryVoltage = (TextView) findViewById(R.id.tv_battery_voltage);
        tvFrontSonarDistance = (TextView) findViewById(R.id.tv_front_sonar);
        tvBackSonarDistance = (TextView) findViewById(R.id.tv_back_sonar);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                    }
                });
            }
        }, 0, 500);
    }

    private void showData() {
        AimrPowerState aimrPowerState = mRosApiClientInstance.getAimrPowerState();
        if (null != aimrPowerState) {
            tvBatteryVoltage.setText(String.valueOf(aimrPowerState.msg.data.discharge_voltage));
            tvFrontSonarDistance.setText(String.valueOf(aimrPowerState.msg.data.sonar.get(1)));
            tvBackSonarDistance.setText(String.valueOf(aimrPowerState.msg.data.sonar.get(3)));
        }
    }
}
