package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.roslibrary.ros.RosApiClient;
import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.SinevaApplication;

import butterknife.BindView;

public class LoadInActivity extends BaseActivity {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.et_port)
    EditText etPort;

    public RosApiClient mRosApiClientInstance;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_loadin;
    }

    @Override
    protected void initView() {
        rotateAnim();
    }

    @Override
    protected void initData() {
        login();
    }

    public void onConnectClick(View view) {
        Intent intent = new Intent(LoadInActivity.this, ChooseActivity.class);
        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();
        String ip = etIp.getText().toString();
        String port = etPort.getText().toString();
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


    private void login() {
        Intent intent = new Intent(LoadInActivity.this, ChooseActivity.class);
        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();
        String ip = etIp.getText().toString();
        String port = etPort.getText().toString();
        boolean isConnectSuccess = mRosApiClientInstance.initClient(ip, port, this.getApplicationContext());

        SharedPreferences mSharedPreferences = getSharedPreferences("ConnectIpAndPort", 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("ip", ip);
        mEditor.putString("port", port);
        mEditor.commit();

        String connectResult = "";
        if (isConnectSuccess) {
            SinevaApplication.setRosClient(mRosApiClientInstance);
            connectResult = "Connect ROS success";
            startActivity(intent);
        } else {
            connectResult = "Connect ROS fail";
        }
        Toast.makeText(this, connectResult, Toast.LENGTH_SHORT).show();
    }

    public void rotateAnim() {
        Animation animation = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set);
        ivIcon.setAnimation(animation);
        animation.start();
    }

    public void onMainClick(View view) {
        startActivity(new Intent(LoadInActivity.this, MainActivity.class));
    }
}
