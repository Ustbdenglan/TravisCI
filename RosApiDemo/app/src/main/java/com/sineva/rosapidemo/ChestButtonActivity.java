package com.sineva.rosapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.Button;

import java.util.Timer;
import java.util.TimerTask;


public class ChestButtonActivity extends Activity {

    private TextView tvButtonPushed;

    private RosApiClient mRosApiClientInstance;

    private boolean isButtonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest_button);

        tvButtonPushed = (TextView) findViewById(R.id.tv_chest_button);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showData();
            }
        }, 0, 50);
    }

    private void showData() {
        Button chestButtonState = mRosApiClientInstance.getChestButtonState();
        if (null != chestButtonState) {
            isButtonPressed = chestButtonState.msg.data;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvButtonPushed.setText("ButtonPressed : " + isButtonPressed);
                }
            });
        }
    }
}
