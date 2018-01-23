package com.sineva.rosapidemo;

import android.app.Application;
import android.content.Intent;

import com.roslibrary.ros.RosApiClient;
import com.sineva.rosapidemo.utilcode.util.SinevaUtils;

import org.litepal.LitePal;

/**
 * Created by Eligah on 2017/9/28.
 */

public class SinevaApplication extends Application {
    static RosApiClient mClient;
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        SinevaUtils.init(this);
        SinevaCrashHandler sinevaCrashHandler = SinevaCrashHandler.getInstance();
        sinevaCrashHandler.init(getApplicationContext());
        Intent serviceIntent = new Intent("android.appwidget.action.SLAM_APP_WIDGET_SERVICE");
        serviceIntent.setPackage(this.getPackageName());
        startService(serviceIntent);
        //AndroidNetworkUtils.startNetwork(this);
    }
    @Override
    public void onTerminate() {
        if(mClient != null)
            mClient.disconnect();
        super.onTerminate();
    }

    public static RosApiClient getRosClient() {
        return mClient;
    }

    public static void setRosClient(RosApiClient client) {
        mClient = client;
    }
}
