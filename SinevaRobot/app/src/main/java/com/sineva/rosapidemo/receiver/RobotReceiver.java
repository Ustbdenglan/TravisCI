package com.sineva.rosapidemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Eligah on 2017/9/30.
 */

public class RobotReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent("android.appwidget.action.SLAM_APP_WIDGET_SERVICE");
        serviceIntent.setPackage(context.getPackageName());
        context.startService(serviceIntent);
    }
}
