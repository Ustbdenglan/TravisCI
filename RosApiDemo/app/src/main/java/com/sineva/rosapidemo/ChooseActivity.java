package com.sineva.rosapidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.roslibrary.ros.RosApiClient;


public class ChooseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }

    public void onMoveBaseClick(View view) {
        startActivity(new Intent(ChooseActivity.this, MoveBaseActivity.class));
    }

    public void onHeadClick(View view) {
        startActivity(new Intent(ChooseActivity.this, HeadActivity.class));
    }

    public void onBatteryClick(View view) {
        startActivity(new Intent(ChooseActivity.this, BatteryAndSonarActivity.class));
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

    public void onWheelStateClick(View view) {
        startActivity(new Intent(ChooseActivity.this, WheelStateActivity.class));
    }

    public void onMobileBaseControllerClick(View view) {
        startActivity(new Intent(ChooseActivity.this, MobileBaseControllerActivity.class));
    }

    public void onChestButtonClick(View view) {
        startActivity(new Intent(ChooseActivity.this, ChestButtonActivity.class));
    }

    public void onLedClick(View view) {
        startActivity(new Intent(ChooseActivity.this, LedActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RosApiClient.getRosApiClientInstance().shutDownClient();
    }
}
