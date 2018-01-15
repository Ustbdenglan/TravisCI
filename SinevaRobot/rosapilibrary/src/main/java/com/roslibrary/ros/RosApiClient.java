package com.roslibrary.ros;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.roslibrary.ros.entity.PublishEvent;
import com.roslibrary.ros.message.AimrPowerState;
import com.roslibrary.ros.message.Button;
import com.roslibrary.ros.message.CompressedImage;
import com.roslibrary.ros.message.JointState;
import com.roslibrary.ros.message.LaserScan;
import com.roslibrary.ros.message.Led;
import com.roslibrary.ros.message.OccupancyGrid;
import com.roslibrary.ros.message.Odometry;
import com.roslibrary.ros.rosbridge.ROSBridgeClient;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by Eligah on 2017/9/4.
 */

public class RosApiClient {

    private static RosApiClient mRosApiClient;
    private static final String TAG = "ROSBridgeClient";
    private String topic_msg;
    private String serv_msg;

    public ROSBridgeClient mClient;

    private Gson mGson = new Gson();

    public static final int DELAY = 0;
    public static final int PERIOD = 500;



    private Double mMoveBase_Def_linearSpeed = 0.3;
    private Double mMoveBase_Def_angularSpeed = 0.3;
    private Double mHead_Def_linearSpeed = 5.0;
    private Double mHead_Def_angularSpeed = 30.0;

    private String mMoveBaseMsg;

    private AimrPowerState mAimrPowerState;
    private CompressedImage mImageRawCompressedData;
    private LaserScan mLaserData;
    private Odometry mOdometry;
    private JointState mJointState;
    private Led mLed;
    private Button mButton;
    private OccupancyGrid mMapData;

    private Timer mTimer;
    private String mHeadMsg;
    private String mResponseData;

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

    public boolean initClient(String ip, String port,Context context) {
        Log.e(Constants.TAG, "IP = " + ip + ", PORT = " + port);
        return connect(ip, port, context);
    }

    //connect the websocket
    private boolean connect(String ip, String port, final Context context) {
        mClient = new ROSBridgeClient("ws://" + ip + ":" + port);
        boolean isConnect = mClient.connect(new ROSClient.ConnectionStatusListener() {
            @Override
            public void onConnect() {
                EventBus.getDefault().register(RosApiClient.this);

                Log.e(Constants.TAG, "Connect ROS success");
            }

            @Override
            public void onDisconnect(boolean normal, String reason, int code) {
                Intent intent = new Intent();
                intent.setAction(Constants.ROSBRIDGE_DISCONNECTED);
                context.sendBroadcast(intent);
                Log.e(Constants.TAG, "ROS disconnect");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                Log.e(Constants.TAG, "ROS communication error");
            }
        });
        return isConnect;
    }

    //关闭连接
    public void disconnect() {
        mClient.disconnect();
    }



    public void subscribeTopic(String[] topicArray) {
        for(int i = 0; i < topicArray.length; ++i) {
            this.mClient.send("{\"op\":\"subscribe\",\"topic\":\"" + topicArray[i] + "\"}");
        }

    }

    public void unSubscribeTopic(String[] topicArray) {
        for(int i = 0; i < topicArray.length; ++i) {
            this.mClient.send("{\"op\":\"unsubscribe\",\"topic\":\"" + topicArray[i] + "\"}");
        }

    }

    /**
     * 订阅主题
     * @param detailName        /chatter
     *  demo:                    {"op":"subscribe","topic":"/chatter"}
     */

    public void subScribeTopic(String detailName) {
        topic_msg = "";

        topic_msg ="{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}";
        Log.e(TAG, "subscribe topic msg:" + topic_msg);
        mClient.send(topic_msg);


    }

    /**
     * 取消订阅主题
     * @param detailName    /chatter
     *
     *                      {"op":"unsubscribe","topic":"/chatter"}
     */

    public void unSubscribeTopic(String detailName) {

        topic_msg="{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}";
        Log.e(TAG, "subscribe topic msg:" +topic_msg);
        mClient.send(topic_msg);


    }


    /**
     * 发布主题
     * @param detailName     /syscommand
     * @param data
     *
     * demo:   {"op":"publish","topic":"/syscommand","msg":{"data":"abc"}}
     */
    public void publishTopic(String detailName,String data) {
        serv_msg="{\"op\":\"publish\",\"topic\":\"" + detailName + "\",\"msg\":{" + data + "}}";
        Log.e(TAG,"publish topic msg:" + serv_msg);
        mClient.send(serv_msg);

    }

    /**
     * 调用服务
     * @param detailName     /add_two_ints
     * @param data
     *
     * demo:  {"op":"call_service","service":"/add_two_ints","args":["a":1,"b":2]}
     */

    public void callService(String detailName,String data) {
        serv_msg = "{\"op\":\"call_service\",\"service\":\"" + detailName + "\",\"args\":["+data+"]}";
        Log.e(TAG,"send service msg:" + serv_msg);
        mClient.send(serv_msg);
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
                mMapData = mGson.fromJson(message.originMsg, OccupancyGrid.class);
                break;
            case "/mobile_base_controller/odom":
                mOdometry = mGson.fromJson(message.originMsg, Odometry.class);
                break;
            case "/rgbd/rgb/image_raw/compressed":
                mImageRawCompressedData = mGson.fromJson(message.originMsg, CompressedImage.class);
                break;
            case "/aimr_power/btn_state":
                mButton = mGson.fromJson(message.originMsg, Button.class);
                break;
            case "/aimr_power/led_state":
                mLed = mGson.fromJson(message.originMsg, Led.class);
                break;
            case "/joint_states":
            case "/joint_states_throttle":
                mJointState = mGson.fromJson(message.originMsg, JointState.class);
                break;
            default:
                mResponseData = message.originMsg;
                break;
        }
    }

    public AimrPowerState getAimrPowerState() {
        return mAimrPowerState;
    }

    public LaserScan getLaserScanData() {
        return mLaserData;
    }

    public OccupancyGrid getMapData() {
        return mMapData;
    }

    public Odometry getMoveBaseData() {
        return mOdometry;
    }

    public CompressedImage getCameraData() {
        return mImageRawCompressedData;
    }

    public Button getChestButtonState() {
        return mButton;
    }

    public Led getLedState() {
        return mLed;
    }

    public JointState getWheelState() {
        return mJointState;
    }

    public String getOtherMessage() {
        return mResponseData;
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
        mClient.disconnect();
    }
}
