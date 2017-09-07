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

public class CameraClient extends ROSClient {

    private static CameraClient mCameraClient;

    private static Context mContext;

    private static String mIp;
    private static String mPort;

    private Bitmap mBitmap;

    private CameraClient() {
        super(mIp, mPort, mContext);
    }

    public static CameraClient getCameraClientInstance(String ip, String port, Context context) {
        mContext = context;
        mIp = ip;
        mPort = port;
        if (mCameraClient == null) {
            synchronized (CameraClient.class) {
                if (mCameraClient == null) {
                    mCameraClient = new CameraClient();
                }
            }
        }
        return mCameraClient;
    }

    public void initClient() {
        mClient.send(Constants.SUBSCRIBE_CAMERA_TOPIC);
        EventBus.getDefault().register(this);
    }

    public Bitmap getCameraData() {
        return mBitmap;
    }

    public void shutDownClient() {
        mClient.send(Constants.UNSUBSCRIBE_CAMERA_TOPIC);
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(PublishEvent msgCameraData) {
        mBitmap = Utils.parseCameraData(msgCameraData);
    }
}
