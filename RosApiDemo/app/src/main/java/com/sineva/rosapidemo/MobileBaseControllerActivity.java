package com.sineva.rosapidemo;

import android.app.Activity;
import android.os.Bundle;

import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.MobileBaseController;

import java.util.Timer;
import java.util.TimerTask;


public class MobileBaseControllerActivity extends Activity {

    private RosApiClient mRosApiClientInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_base_controller);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showData();
            }
        }, 3000, 500);
    }

    private void showData() {
        MobileBaseController moveBaseData = mRosApiClientInstance.getMoveBaseData();
    }
}
