package com.sineva.implementation;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sineva.Constants;
import com.sineva.ROSClient;
import com.sineva.entity.BatteryAndSonarState;

import de.greenrobot.event.EventBus;

/**
 * Created by Eligah on 2017/9/4.
 */

public class BatteryAndSanorClient extends ROSClient {

    private static BatteryAndSanorClient mBattertAndSanorClient;

    private static Context mContext;

    private static String mIp;
    private static String mPort;

    private final int mBackSonarDistanceIndex = 3;
    private final int mFrontSonarDistanceIndex = 1;

    private Gson mGson = new Gson();

    private String mBatteryVoltage;
    private String mFrontSonarDistance;
    private String mBackSonarDistance;

    private BatteryAndSanorClient() {
        super(mIp, mPort, mContext);
    }

    public static BatteryAndSanorClient getBatteryAndSanorClientInstance(String ip, String port, Context context) {
        mContext = context;
        mIp = ip;
        mPort = port;
        if (mBattertAndSanorClient == null) {
            synchronized (BatteryAndSanorClient.class) {
                if (mBattertAndSanorClient == null) {
                    mBattertAndSanorClient = new BatteryAndSanorClient();
                }
            }
        }
        return mBattertAndSanorClient;
    }

    public void initClient() {
        mClient.send(Constants.SUBSCRIBE_BATTERY_AND_SONAR_TOPIC);
        EventBus.getDefault().register(this);
    }

    public String getBatteryVoltage() {
        return mBatteryVoltage;
    }

    public String getFrontSonarDistance() {
        return mFrontSonarDistance;
    }

    public String getBackSonarDistance() {
        return mBackSonarDistance;
    }

    public void shutDownClient() {
        mClient.send(Constants.UNSUBSCRIBE_BATTERY_AND_SONAR_TOPIC);
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(String msgBatteryAndSanorData) {

        msgBatteryAndSanorData = msgBatteryAndSanorData.replaceAll("\\\\n", ",").replaceAll("\\\\", "").replace(": \",", ":").replaceAll("\\{,", "{").replaceAll("\\,\\}\\\"\\}", "\\}\\}");

        BatteryAndSonarState batteryAndSonarState = mGson.fromJson(msgBatteryAndSanorData, BatteryAndSonarState.class);
        mBatteryVoltage = String.valueOf(batteryAndSonarState.msg.data.discharge_voltage);
        mFrontSonarDistance = String.valueOf(batteryAndSonarState.msg.data.sonar.get(mFrontSonarDistanceIndex));
        mBackSonarDistance = String.valueOf(batteryAndSonarState.msg.data.sonar.get(mBackSonarDistanceIndex));
        Log.e(Constants.TAG, "mBatteryVoltage = " + mBatteryVoltage + ",mFrontSonarDistance = " + mFrontSonarDistance + ",mBackSonarDistance = " + mBackSonarDistance);
    }
}
