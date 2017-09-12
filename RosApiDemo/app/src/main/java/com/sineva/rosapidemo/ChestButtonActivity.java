package com.sineva.rosapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.ChestButton;

import java.util.Timer;
import java.util.TimerTask;


public class ChestButtonActivity extends Activity {

    private TextView tvButtonPushed;

    private RosApiClient mRosApiClientInstance;

    private int i = 0;

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
        }, 3000, 10);
    }

    private void showData() {
        ChestButton chestButtonState = mRosApiClientInstance.getChestButtonState();
        boolean close_press = chestButtonState.msg.data.close_press;
        if (close_press) {
            i++;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvButtonPushed.setText("ButtonPressed" + i);
                }
            });
        }
    }
}
