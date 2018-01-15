package com.roslibrary.ros;

/**
 * Created by Eligah on 2017/9/4.
 */

public class Constants {
    public static final String TAG = "com.sineva.ros_api";
    public static final String ROSBRIDGE_DISCONNECTED = "com.sineva.rosbridge_disconnected";

    public static final String SUBSCRIBE_TOPIC_HEADER = "{\"op\":\"subscribe\",\"topic\":\"";
    public static final String SUBSCRIBE_TOPIC_ENDER = "\"}";

    public static final String STOP_MESSAGE = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
}
