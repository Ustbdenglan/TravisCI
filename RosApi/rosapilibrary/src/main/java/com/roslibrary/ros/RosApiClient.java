package com.roslibrary.ros;

import android.util.Log;

import com.google.gson.Gson;
import com.roslibrary.ros.entity.PublishEvent;
import com.roslibrary.ros.message.AimrPowerState;
import com.roslibrary.ros.message.ButtonState;
import com.roslibrary.ros.message.LaserScan;
import com.roslibrary.ros.message.LedState;
import com.roslibrary.ros.message.MobileBaseController;
import com.roslibrary.ros.message.WheelState;
import com.roslibrary.ros.rosbridge.ROSBridgeClient;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by Eligah on 2017/9/4.
 */

public class RosApiClient {

    private static RosApiClient mRosApiClient;

    public ROSBridgeClient mClient;

    private Gson mGson = new Gson();

    public static final int DELAY = 0;
    public static final int PERIOD = 500;

    private Double mMoveBase_Def_linearSpeed = 0.3;
    private Double mMoveBase_Def_angularSpeed = 0.3;
    private Double mHead_Def_linearSpeed = 5.0;
    private Double mHead_Def_angularSpeed = 30.0;

    private String mMapData;
    private String mImageRawCompressedData;
    private String mMoveBaseMsg;

    private AimrPowerState mAimrPowerState;
    private LaserScan mLaserData;
    private MobileBaseController mMobileBaseController;
    private WheelState mWheelState;
    private LedState mLedState;
    private ButtonState mButtonState;

    private Timer mTimer;
    private String mHeadMsg;
    private String mResult;

    public static RosApiClient getRosApiClientInstance() {
        if (mRosApiClient == null) {
            synchronized (RosApiClient.class) {
                if (mRosApiClient == null) {
                    mRosApiClient = new RosApiClient();
                }
            }
        }
        return mRosApiClient;
    }

    public String initClient(String ip, String port) {
        Log.e(Constants.TAG, "IP = " + ip + ", PORT = " + port);
        return connect(ip, port);
    }

    //connect the websocket
    private String connect(String ip, String port) {
        mClient = new ROSBridgeClient("ws://" + ip + ":" + port);
        mClient.connect(new com.roslibrary.ros.ROSClient.ConnectionStatusListener() {
            @Override
            public void onConnect() {
                mClient.send(Constants.SUBSCRIBE_BATTERY_AND_SONAR_TOPIC);
                mClient.send(Constants.SUBSCRIBE_LIDAR_TOPIC);
                mClient.send(Constants.SUBSCRIBE_MAP_TOPIC);
                mClient.send(Constants.SUBSCRIBE_KEY_TOPIC);
                mClient.send(Constants.SUBSCRIBE_ODOMETER_TOPIC);
                mClient.send(Constants.SUBSCRIBE_WHEEL_SPEED_TOPIC);
                mClient.send(Constants.SUBSCRIBE_CAMERA_TOPIC);
                mClient.send(Constants.SUBSCRIBE_LED_TOPIC);
                EventBus.getDefault().register(RosApiClient.this);
                mResult = "Connect ROS success";
                Log.e(Constants.TAG, "Connect ROS success");
            }

            @Override
            public void onDisconnect(boolean normal, String reason, int code) {
                Log.e(Constants.TAG, "ROS disconnect");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                Log.e(Constants.TAG, "ROS communication error");
            }
        });
        return mResult;
    }

    //receive the data from rosbridge
    public void onEvent(PublishEvent message) {
        switch (message.name) {
            case "/scan":
                mLaserData = mGson.fromJson(message.originMsg, LaserScan.class);
                break;
            case "/aimr_power/state":
                String msgBatteryAndSanorData = message.originMsg.replaceAll("\\\\n", ",").replaceAll("\\\\", "").replace(": \",", ":").replaceAll("\\{,", "{").replaceAll("\\,\\}\\\"\\}", "\\}\\}");
                mAimrPowerState = mGson.fromJson(msgBatteryAndSanorData, AimrPowerState.class);
                break;
            case "/map":
                mMapData = message.msg;
                break;
            case "/mobile_base_controller/odom":
                mMobileBaseController = mGson.fromJson(message.originMsg, MobileBaseController.class);
                break;
            case "/usb_cam/image_raw/compressed":
                mImageRawCompressedData = message.msg;
                break;
            case "/aimr_power/btn_state":
                mButtonState = mGson.fromJson(message.originMsg, ButtonState.class);
                break;
            case "/aimr_power/led_state":
                mLedState = mGson.fromJson(message.originMsg, LedState.class);
                break;
            case "/joint_states_throttle":
                mWheelState = mGson.fromJson(message.originMsg, WheelState.class);
                break;
        }
    }

    public AimrPowerState getAimrPowerState() {
        return mAimrPowerState;
    }

    public LaserScan getLaserScanData() {
        return mLaserData;
    }

    public String getMapData() {
        return mMapData;
    }

    public MobileBaseController getMoveBaseData() {
        return mMobileBaseController;
    }

    public String getCameraData() {
        return mImageRawCompressedData;
    }

    public ButtonState getChestButtonState() {
        return mButtonState;
    }

    public LedState getLedState() {
        return mLedState;
    }

    public WheelState getWheelState() {
        return mWheelState;
    }

    //control the robot's wheel
    public void robotForward() {
        stopMoveBaseMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeMoveBaseLinearMessageAndSend(String.valueOf(mMoveBase_Def_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void robotBackward() {
        stopMoveBaseMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeMoveBaseLinearMessageAndSend("-" + String.valueOf(mMoveBase_Def_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void robotTurnLeft() {
        stopMoveBaseMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeMoveBaseAngularMessageAndSend(String.valueOf(mMoveBase_Def_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void robotTurnRight() {
        stopMoveBaseMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeMoveBaseAngularMessageAndSend("-" + String.valueOf(mMoveBase_Def_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void stopMoveBaseMovemet() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mMoveBaseMsg = Constants.STOP_MESSAGE;
        sendMoveBaseMsgToTopic(mMoveBaseMsg);
    }

    public void setMoveBaseRollSpeed(Double rollSpeed) {
        mMoveBase_Def_angularSpeed = rollSpeed;
    }

    public void setMoveBaseCabrageSpeed(Double cabrageSpeed) {
        mMoveBase_Def_linearSpeed = cabrageSpeed;
    }

    private void sendMoveBaseMsgToTopic(String msg) {
        mClient.send("{\"op\":\"publish\",\"topic\":\"/joy_teleop/cmd_vel_base\",\"msg\":{" + msg + "}}");
    }

    private void changeMoveBaseLinearMessageAndSend(String strLinearSpeed) {
        mMoveBaseMsg = "\"linear\":{\"x\":" + strLinearSpeed + ",\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
        sendMoveBaseMsgToTopic(mMoveBaseMsg);
    }

    private void changeMoveBaseAngularMessageAndSend(String strAngularSpeed) {
        mMoveBaseMsg = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + strAngularSpeed + "}";
        sendMoveBaseMsgToTopic(mMoveBaseMsg);
    }

    //control the robot's head
    public void headUp() {
        stopHeadMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeHeadLinearMessageAndSend(String.valueOf(mHead_Def_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void headDown() {
        stopHeadMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeHeadLinearMessageAndSend("-" + String.valueOf(mHead_Def_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void headTurnLeft() {
        stopHeadMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeHeadAngularMessageAndSend("-" + String.valueOf(mHead_Def_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void headTurnRight() {
        stopHeadMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeHeadAngularMessageAndSend(String.valueOf(mHead_Def_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void stopHeadMovemet() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mHeadMsg = Constants.STOP_MESSAGE;
        sendHeadMsgToTopic(mHeadMsg);
    }

    public void setHeadRollSpeed(Double rollSpeed) {
        mHead_Def_angularSpeed = rollSpeed;
    }

    public void setHeadCabrageSpeed(Double cabrageSpeed) {
        mHead_Def_linearSpeed = cabrageSpeed;
    }

    private void sendHeadMsgToTopic(String msg) {
        mClient.send("{\"op\":\"publish\",\"topic\":\"/joy_teleop/cmd_vel_head\",\"msg\":{" + msg + "}}");
    }

    private void changeHeadLinearMessageAndSend(String strLinearSpeed) {
        mHeadMsg = "\"linear\":{\"x\":" + strLinearSpeed + ",\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
        sendHeadMsgToTopic(mHeadMsg);
    }

    private void changeHeadAngularMessageAndSend(String strAngularSpeed) {
        mHeadMsg = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + strAngularSpeed + "}";
        sendHeadMsgToTopic(mHeadMsg);
    }

    //unsubscribe topics
    public void shutDownClient() {
        mClient.send(Constants.UNSUBSCRIBE_BATTERY_AND_SONAR_TOPIC);
        mClient.send(Constants.UNSUBSCRIBE_CAMERA_TOPIC);
        mClient.send(Constants.UNSUBSCRIBE_LIDAR_TOPIC);
        mClient.send(Constants.UNSUBSCRIBE_MAP_TOPIC);
        mClient.send(Constants.UNSUBSCRIBE_ODOMETER_TOPIC);
        mClient.send(Constants.UNSUBSCRIBE_KEY_TOPIC);
        mClient.send(Constants.UNSUBSCRIBE_WHEEL_SPEED_TOPIC);
        mClient.send(Constants.UNSUBSCRIBE_LED_TOPIC);
        mClient.disconnect();
    }
}
