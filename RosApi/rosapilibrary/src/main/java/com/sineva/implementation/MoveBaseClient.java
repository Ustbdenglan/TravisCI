package com.sineva.implementation;

import android.content.Context;

import com.sineva.Constants;
import com.sineva.ROSClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Eligah on 2017/9/4.
 */

public class MoveBaseClient extends ROSClient {

    public static final int DELAY = 0;
    public static final int PERIOD = 500;
    private static MoveBaseClient mMoveBaseClient;

    private static Context mContext;

    private Double mDef_linearSpeed = 0.3;
    private Double mDef_angularSpeed = 0.3;

    private static String mIp;
    private static String mPort;

    private Timer mTimer;
    private String mMsg;

    private MoveBaseClient() {
        super(mIp, mPort, mContext);
    }

    public static MoveBaseClient getMoveBaseClientInstance(String ip, String port, Context context) {
        mContext = context;
        mIp = ip;
        mPort = port;
        if (mMoveBaseClient == null) {
            synchronized (MoveBaseClient.class) {
                if (mMoveBaseClient == null) {
                    mMoveBaseClient = new MoveBaseClient();
                }
            }
        }
        return mMoveBaseClient;
    }

    public void forward() {
        stopMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeLinearMessageAndSend(String.valueOf(mDef_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void backward() {
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
                changeAngularMessageAndSend(String.valueOf(mDef_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void right() {
        stopMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeAngularMessageAndSend("-" + String.valueOf(mDef_angularSpeed));
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
        mClient.send("{\"op\":\"publish\",\"topic\":\"/joy_teleop/cmd_vel_base\",\"msg\":{" + msg + "}}");
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
