package com.sineva;

/**
 * Created by Eligah on 2017/9/4.
 */

public class Constants {
    public static final String TAG = "com.sineva.ros_api";

    public static final String SUBSCRIBE_BATTERY_AND_SONAR_TOPIC = "{\"op\":\"subscribe\",\"topic\":\"/aimr_power/state\"}";
    public static final String UNSUBSCRIBE_BATTERY_AND_SONAR_TOPIC = "{\"op\":\"subscribe\",\"topic\":\"/aimr_power/state\"}";

    public static final String SUBSCRIBE_CAMERA_TOPIC = "{\"op\":\"subscribe\",\"topic\":\"/usb_cam/image_raw/compressed\"}";
    public static final String UNSUBSCRIBE_CAMERA_TOPIC = "{\"op\":\"unsubscribe\",\"topic\":\"/usb_cam/image_raw/compressed\"}";

    public static final String SUBSCRIBE_LIDAR_TOPIC = "{\"op\":\"subscribe\",\"topic\":\"/scan\"}";
    public static final String UNSUBSCRIBE_LIDAR_TOPIC = "{\"op\":\"unsubscribe\",\"topic\":\"/scan\"}";

    public static final String SUBSCRIBE_MAP_TOPIC = "{\"op\":\"subscribe\",\"topic\":\"/map\"}";
    public static final String UNSUBSCRIBE_MAP_TOPIC = "{\"op\":\"unsubscribe\",\"topic\":\"/map\"}";

    public static final String STOP_MESSAGE = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
}
