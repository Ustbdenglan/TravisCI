package com.sineva.rosapidemo;

import android.app.Application;
import android.content.Intent;

/**
 * Created by Eligah on 2017/9/28.
 */

public class SinevaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SinevaCrashHandler sinevaCrashHandler = SinevaCrashHandler.getInstance();
        sinevaCrashHandler.init(getApplicationContext());
        Intent serviceIntent = new Intent("android.appwidget.action.SLAM_APP_WIDGET_SERVICE");
        serviceIntent.setPackage(this.getPackageName());
        startService(serviceIntent);
    }
}
