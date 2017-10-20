package com.sineva.rosapidemo.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sineva.rosapidemo.R;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Eligah on 2017/9/29.
 */

public class RobotControllerWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "WidgetProvider";

    private boolean DEBUG = false;

    private final Intent EXAMPLE_SERVICE_INTENT = new Intent("android.appwidget.action.SLAM_APP_WIDGET_SERVICE");

    private final String ACTION_UPDATE_ALL = "com.sineva.widget.UPDATE_ALL";

    private static Set idsSet = new HashSet();
    private String battery_voltage;
    private String linear_speed;
    private String angular_speed;

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        battery_voltage = intent.getStringExtra("battery_voltage");
        linear_speed = intent.getStringExtra("linear_speed");
        angular_speed = intent.getStringExtra("angular_speed");
        Log.d(TAG, "OnReceive:Action: " + action);
        if (ACTION_UPDATE_ALL.equals(action)) {
            updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        Log.d(TAG, "onEnabled");

        EXAMPLE_SERVICE_INTENT.setPackage(context.getPackageName());
        context.startService(EXAMPLE_SERVICE_INTENT);
        super.onEnabled(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Log.d(TAG, "onAppWidgetOptionsChanged");

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate(): appWidgetIds.length=" + appWidgetIds.length);

        for (int appWidgetId : appWidgetIds) {
            idsSet.add(Integer.valueOf(appWidgetId));
        }
        prtSet();
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(TAG, "onDisabled");

        context.stopService(EXAMPLE_SERVICE_INTENT);
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d(TAG, "onDeleted(): appWidgetIds.length=" + appWidgetIds.length);

        for (int appWidgetId : appWidgetIds) {
            idsSet.remove(Integer.valueOf(appWidgetId));
        }
        prtSet();
        super.onDeleted(context, appWidgetIds);
    }

    private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, Set set) {

        Log.d(TAG, "updateAllAppWidgets(): size=" + set.size());

        int appID;
        Iterator it = set.iterator();

        while (it.hasNext()) {
            appID = ((Integer) it.next()).intValue();

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.robotcontroller_widget);
            remoteView.setTextViewText(R.id.tv_battery_voltage, battery_voltage);
            remoteView.setTextViewText(R.id.tv_linear, linear_speed);
            remoteView.setTextViewText(R.id.tv_angular, angular_speed);

            appWidgetManager.updateAppWidget(appID, remoteView);
        }
    }

    private void prtSet() {
        if (DEBUG) {
            int index = 0;
            int size = idsSet.size();
            Iterator it = idsSet.iterator();
            Log.d(TAG, "total:" + size);
            while (it.hasNext()) {
                Log.d(TAG, index + " -- " + ((Integer) it.next()).intValue());
            }
        }
    }
}
