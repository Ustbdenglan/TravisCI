package com.sineva.implementation;

import android.content.Context;

import com.sineva.Constants;
import com.sineva.ROSClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Eligah on 2017/9/4.
 */

public class HeadClient extends ROSClient {

    public static final int PERIOD = 500;
    public static final int DELAY = 0;
    private static HeadClient mHeadClient;

    private static Context mContext;

    private Double mDef_linearSpeed = 5.0;
    private Double mDef_angularSpeed = 30.0;

    private static String mIp;
    private static String mPort;

    private String mMsg;
    private Timer mTimer;

    private HeadClient() {
        super(mIp, mPort, mContext);
    }

    public static HeadClient getHeadClientInstance(String ip, String port, Context context) {
        mContext = context;
        mIp = ip;
        mPort = port;
        if (mHeadClient == null) {
            synchronized (HeadClient.class) {
                if (mHeadClient == null) {
                    mHeadClient = new HeadClient();
                }
            }
        }
        return mHeadClient;
    }

    public void up() {
        stopMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeLinearMessageAndSend(String.valueOf(mDef_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void down() {
        stopMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeLinearMessageAndSend("-" + String.valueOf(mDef_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void left() {
        stopMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeAngularMessageAndSend("-" + String.valueOf(mDef_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void right() {
        stopMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeAngularMessageAndSend(String.valueOf(mDef_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void stopMovemet() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mMsg = Constants.STOP_MESSAGE;
        sendMsgToTopic(mMsg);
    }

    public void setMaxRollSpeed(Double maxRollSpeed) {
        mDef_angularSpeed = maxRollSpeed;
    }

    public void setMaxCabrageSpeed(Double maxCabrageSpeed) {
        mDef_linearSpeed = maxCabrageSpeed;
    }

    private void sendMsgToTopic(String msg) {
        mClient.send("{\"op\":\"publish\",\"topic\":\"/joy_teleop/cmd_vel_head\",\"msg\":{" + msg + "}}");
    }

    private void changeLinearMessageAndSend(String strLinearSpeed) {
        mMsg = "\"linear\":{\"x\":" + strLinearSpeed + ",\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
        sendMsgToTopic(mMsg);
    }

    private void changeAngularMessageAndSend(String strAngularSpeed) {
        mMsg = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + strAngularSpeed + "}";
        sendMsgToTopic(mMsg);
    }
}
