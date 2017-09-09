package com.sineva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.roslibrary.ros.entity.PublishEvent;
import com.sineva.EventInterface;
import com.sineva.implementation.BatteryAndSanorClient;

import de.greenrobot.event.EventBus;


public class BatteryAndSonarActivity extends Activity implements EventInterface {

    private TextView tvFrontSonarDistance;
    private TextView tvBatteryVoltage;
    private TextView tvBackSonarDistance;
    private BatteryAndSanorClient mBatteryAndSanorClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_and_sonar);

        Intent intent = getIntent();

        mBatteryAndSanorClient = BatteryAndSanorClient.getBatteryAndSanorClientInstance(intent.getStringExtra("ip"), intent.getStringExtra("port"), this);
        mBatteryAndSanorClient.initClient();
        EventBus.getDefault().register(this);

        tvBatteryVoltage = (TextView) findViewById(R.id.tv_battery_voltage);
        tvFrontSonarDistance = (TextView) findViewById(R.id.tv_front_sonar);
        tvBackSonarDistance = (TextView) findViewById(R.id.tv_back_sonar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mBatteryAndSanorClient.shutDownClient();
    }

    @Override
    public void onEvent(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String batteryVoltage = mBatteryAndSanorClient.getBatteryVoltage();
                tvBatteryVoltage.setText(batteryVoltage);
                String frontSonarDistance = mBatteryAndSanorClient.getFrontSonarDistance();
                tvFrontSonarDistance.setText(frontSonarDistance);
                String backSonarDistance = mBatteryAndSanorClient.getBackSonarDistance();
                tvBackSonarDistance.setText(backSonarDistance);
            }
        });
    }

    @Override
    public void onEvent(PublishEvent publishEvent) {

    }
}
