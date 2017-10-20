package com.sineva.rosapidemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.roslibrary.ros.RosApiClient;
import com.sineva.rosapidemo.R;

public class LoadInActivity extends Activity {

    private EditText mEtIp;
    private EditText mEtPort;
    public RosApiClient mRosApiClientInstance;
    private ImageView mIvIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_loadin);

        mEtIp = findViewById(R.id.et_ip);
        mEtPort = findViewById(R.id.et_port);
        mIvIcon = findViewById(R.id.iv_icon);

        rotateAnim();
    }


    public void onConnectClick(View view) {
        Intent intent = new Intent(LoadInActivity.this, ChooseActivity.class);
        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();
        String ip = mEtIp.getText().toString();
        String port = mEtPort.getText().toString();
        boolean isConnectSuccess = mRosApiClientInstance.initClient(ip, port, this.getApplicationContext());

        SharedPreferences mSharedPreferences = getSharedPreferences("ConnectIpAndPort", 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("ip", ip);
        mEditor.putString("port", port);
        mEditor.commit();

        String connectResult = "";
        if (isConnectSuccess) {
            connectResult = "Connect ROS success";
            startActivity(intent);
        } else {
            connectResult = "Connect ROS fail";
        }
        Toast.makeText(this, connectResult, Toast.LENGTH_SHORT).show();
    }

    public void rotateAnim() {
        Animation animation = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set);
        mIvIcon.setAnimation(animation);
        animation.start();
    }

    public void onMainClick(View view) {
        startActivity(new Intent(LoadInActivity.this,MainActivity.class));
    }
}
