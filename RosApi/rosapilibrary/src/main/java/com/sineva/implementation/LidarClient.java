package com.sineva.implementation;

import android.content.Context;

import com.google.gson.Gson;
import com.sineva.Constants;
import com.sineva.ROSClient;
import com.sineva.entity.LidarData;

import de.greenrobot.event.EventBus;

/**
 * Created by Eligah on 2017/9/4.
 */

public class LidarClient extends ROSClient {

    private static LidarClient mLidarClient;

    private static Context mContext;

    private static String mIp;
    private static String mPort;

    private Gson mGson = new Gson();
    private LidarData mLidarData;

    private LidarClient() {
        super(mIp, mPort, mContext);
    }

    public static LidarClient getLidarClientInstance(String ip, String port, Context context) {
        mContext = context;
        mIp = ip;
        mPort = port;
        if (mLidarClient == null) {
            synchronized (LidarClient.class) {
                if (mLidarClient == null) {
                    mLidarClient = new LidarClient();
                }
            }
        }
        return mLidarClient;
    }

    public void initClient() {
        mClient.send(Constants.SUBSCRIBE_LIDAR_TOPIC);
        EventBus.getDefault().register(this);
    }

    public LidarData getLidarData() {
        return mLidarData;
    }

    public void shutDownClient() {
        mClient.send(Constants.UNSUBSCRIBE_LIDAR_TOPIC);
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(String msgLidarData) {
        mLidarData = mGson.fromJson(msgLidarData, LidarData.class);
    }
}
