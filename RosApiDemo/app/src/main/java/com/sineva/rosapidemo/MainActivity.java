package com.sineva.rosapidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.roslibrary.ros.Constants;
import com.roslibrary.ros.RosApiClient;

public class MainActivity extends Activity {

    private EditText mEtIp;
    private EditText mEtPort;
    public RosApiClient mRosApiClientInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtIp = findViewById(R.id.et_ip);
        mEtPort = findViewById(R.id.et_port);
    }


    public void onConnectClick(View view) {
        Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();
        String[] topicArray = {"/aimr_power/state", "/rgbd/rgb/image_raw/compressed", "/scan", "/map", "/mobile_base_controller/odom", "/aimr_power/btn_state", "/aimr_power/led_state", "/joint_states_throttle"};
        boolean isConnectSuccess = mRosApiClientInstance.initClient(mEtIp.getText().toString(), mEtPort.getText().toString(), topicArray);
        Log.e(Constants.TAG, isConnectSuccess + "");
        String connectResult = "";
        if (isConnectSuccess) {
            connectResult = "Connect ROS success";
            startActivity(intent);
        } else {
            connectResult = "Connect ROS fail";
        }
        Toast.makeText(this, connectResult, Toast.LENGTH_SHORT).show();
    }
}
