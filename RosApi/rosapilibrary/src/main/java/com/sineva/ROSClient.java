package com.sineva;

import android.content.Context;
import android.util.Log;

import com.roslibrary.ros.rosbridge.ROSBridgeClient;

/**
 * Created by Eligah on 2017/9/4.
 */

public class ROSClient {

    public ROSBridgeClient mClient;

    public ROSClient(String ip, String port, Context context) {
        connect(ip, port, context);
    }

    private void connect(String ip, String port, final Context context) {
        mClient = new ROSBridgeClient("ws://" + ip + ":" + port);
        mClient.connect(new com.roslibrary.ros.ROSClient.ConnectionStatusListener() {
            @Override
            public void onConnect() {
                Log.e(Constants.TAG, "Connect ROS success");
            }

            @Override
            public void onDisconnect(boolean normal, String reason, int code) {
                Log.e(Constants.TAG, "ROS disconnect");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                Log.e(Constants.TAG, "ROS communication error");
            }
        });
    }
}
