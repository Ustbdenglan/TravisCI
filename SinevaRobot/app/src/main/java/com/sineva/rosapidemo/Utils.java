package com.sineva.rosapidemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.roslibrary.ros.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eligah on 2017/9/28.
 */

public class Utils {
    public static String parseTime(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return format.format(date);
    }

    public static double formatDouble(double dou) {
        return (double) (Math.round(dou * 10000) / 10000.0);
    }

    public static boolean isNetworkAvailable(final Context context) {
        boolean hasWifoCon = false;
        boolean hasMobileCon = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfos = cm.getAllNetworkInfo();
        for (NetworkInfo net : netInfos) {

            String type = net.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                Log.d(Constants.TAG, "get Wifi connection");
                if (net.isConnected()) {
                    hasWifoCon = true;
                }
            }

            if (type.equalsIgnoreCase("MOBILE")) {
                Log.d(Constants.TAG, "get Mobile connection");
                if (net.isConnected()) {
                    hasMobileCon = true;
                }
            }
        }
        return hasWifoCon || hasMobileCon;

    }

    public static boolean isNetWorkDataAvailable(final Context context) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process pingProcess = runtime.exec("/system/bin/ping -c 1 www.baidu.com");
            int exitCode = pingProcess.waitFor();
            return (exitCode == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
