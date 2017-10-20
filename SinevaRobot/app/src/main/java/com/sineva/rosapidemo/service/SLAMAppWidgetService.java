package com.sineva.rosapidemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.AimrPowerState;
import com.roslibrary.ros.message.Odometry;
import com.sineva.rosapidemo.Utils;

/**
 * Created by Eligah on 2017/9/29.
 */

public class SLAMAppWidgetService extends Service {
    private static final String TAG = "SlamAppWidgetService";

    private final String ACTION_UPDATE_ALL = "com.sineva.widget.UPDATE_ALL";

    private static final int UPDATE_TIME = 1000;

    private UpdateThread mUpdateThread;

    private Context mContext;

    private int count = 0;

    private AimrPowerState mAimrPowerState;

    private RosApiClient mRosApiClientInstance;

    private Odometry mMoveBaseData;

    private boolean mIsConnected = false;

    private RosbridgeReceiver mRosDisconnectReceiver;

    @Override
    public void onCreate() {

        mUpdateThread = new UpdateThread();
        mUpdateThread.start();

        mContext = this.getApplicationContext();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (mUpdateThread != null) {
            mUpdateThread.interrupt();
        }

        this.unregisterReceiver(mRosDisconnectReceiver);

        Intent serviceIntent = new Intent("android.appwidget.action.SLAM_APP_WIDGET_SERVICE");
        serviceIntent.setPackage(this.getPackageName());
        startService(serviceIntent);

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sineva.rosbridge_disconnected");
        mRosDisconnectReceiver = new RosbridgeReceiver();
        this.registerReceiver(mRosDisconnectReceiver, filter);

        return START_STICKY;
    }

    private class UpdateThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                count = 0;
                while (true) {
                    Log.d(TAG, "run ... count:" + count);
                    count++;

                    if (!mIsConnected) {
                        if (Utils.isNetWorkDataAvailable(SLAMAppWidgetService.this)) {
                            mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

                            SharedPreferences mSharedPreferences = getSharedPreferences("ConnectIpAndPort", 0);
                            String ip = mSharedPreferences.getString("ip", null);
                            String port = mSharedPreferences.getString("port", null);
                            if (ip != null && port != null) {
                                mIsConnected = mRosApiClientInstance.initClient(ip, port, SLAMAppWidgetService.this.getApplicationContext());

                                try {
                                    mRosApiClientInstance.subscribeTopic(new String[]{"/mobile_base_controller/odom", "/aimr_power/state"});
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    Intent updateIntent = new Intent(ACTION_UPDATE_ALL);
                    if (null != mRosApiClientInstance) {
                        mAimrPowerState = mRosApiClientInstance.getAimrPowerState();
                        mMoveBaseData = mRosApiClientInstance.getMoveBaseData();

                        if (mAimrPowerState != null) {
                            updateIntent.putExtra("battery_voltage", "Battery Voltage : " + String.valueOf(mAimrPowerState.msg.data.discharge_voltage) + "V");
                        }

                        if (mMoveBaseData != null) {
                            double x = mMoveBaseData.msg.twist.twist.linear.x;
                            x = Utils.formatDouble(x);
                            updateIntent.putExtra("linear_speed", "Linear Speed : " + String.valueOf(x) + "m/s");
                            double z = mMoveBaseData.msg.twist.twist.angular.z;
                            z = Utils.formatDouble(z);
                            updateIntent.putExtra("angular_speed", "Angular Speed : " + String.valueOf(z) + "m/s");
                        }
                    }
                    mContext.sendBroadcast(updateIntent);

                    Thread.sleep(UPDATE_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class RosbridgeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mIsConnected = false;
        }
    }

}
