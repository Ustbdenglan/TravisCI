package com.sineva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class ChooseActivity extends Activity {

    private String mIp;
    private String mPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        mIp = getIntent().getStringExtra("ip");
        mPort = getIntent().getStringExtra("port");
    }

    public void onMoveBaseClick(View view) {
        Intent movebaseIntent = new Intent(ChooseActivity.this, MoveBaseActivity.class);
        movebaseIntent.putExtra("ip", mIp);
        movebaseIntent.putExtra("port", mPort);
        startActivity(movebaseIntent);
    }

    public void onHeadClick(View view) {
        Intent headIntent = new Intent(ChooseActivity.this, HeadActivity.class);
        headIntent.putExtra("ip", mIp);
        headIntent.putExtra("port", mPort);
        startActivity(headIntent);
    }

    public void onBatteryClick(View view) {
        Intent batteryAndSonarIntent = new Intent(ChooseActivity.this, BatteryAndSonarActivity.class);
        batteryAndSonarIntent.putExtra("ip", mIp);
        batteryAndSonarIntent.putExtra("port", mPort);
        startActivity(batteryAndSonarIntent);
    }

    public void onLidarClick(View view) {
        Intent lidarIntent = new Intent(ChooseActivity.this, LidarActivity.class);
        lidarIntent.putExtra("ip", mIp);
        lidarIntent.putExtra("port", mPort);
        startActivity(lidarIntent);
    }

    public void onMapClick(View view) {
        Intent mapIntent = new Intent(ChooseActivity.this, MapActivity.class);
        mapIntent.putExtra("ip", mIp);
        mapIntent.putExtra("port", mPort);
        startActivity(mapIntent);
    }

    public void onCameraClick(View view) {
        Intent cameraIntent = new Intent(ChooseActivity.this, CameraActivity.class);
        cameraIntent.putExtra("ip", mIp);
        cameraIntent.putExtra("port", mPort);
        startActivity(cameraIntent);
    }
}
