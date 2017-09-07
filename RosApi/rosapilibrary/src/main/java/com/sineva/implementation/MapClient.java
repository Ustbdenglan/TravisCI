package com.sineva.implementation;

import android.content.Context;
import android.graphics.Bitmap;

import com.roslibrary.ros.entity.PublishEvent;
import com.sineva.Constants;
import com.sineva.ROSClient;
import com.sineva.utils.Utils;

import de.greenrobot.event.EventBus;

/**
 * Created by Eligah on 2017/9/4.
 */

public class MapClient extends ROSClient {

    private static MapClient mMapClient;

    private static Context mContext;

    private static String mIp;
    private static String mPort;

    private String mMapData;
    private boolean mIsBitmapCreated = false;
    private Bitmap mBitmap;

    private MapClient() {
        super(mIp, mPort, mContext);
    }

    public static MapClient getMapClientInstance(String ip, String port, Context context) {
        mContext = context;
        mIp = ip;
        mPort = port;
        if (mMapClient == null) {
            synchronized (MapClient.class) {
                if (mMapClient == null) {
                    mMapClient = new MapClient();
                }
            }
        }
        return mMapClient;
    }

    public void initClient() {
        mClient.send(Constants.SUBSCRIBE_MAP_TOPIC);
        EventBus.getDefault().register(this);
    }

    public Bitmap getMapData() {
        return mBitmap;
    }

    public void shutDownClient() {
        mClient.send(Constants.UNSUBSCRIBE_MAP_TOPIC);
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(PublishEvent msgMapdata) {
        mBitmap = Utils.parseMapData(msgMapdata);
    }
}
