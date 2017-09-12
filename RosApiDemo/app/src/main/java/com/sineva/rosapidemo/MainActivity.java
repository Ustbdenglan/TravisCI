package com.sineva.rosapidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        mRosApiClientInstance.initClient(mEtIp.getText().toString(), mEtPort.getText().toString());
        startActivity(intent);
    }
}
