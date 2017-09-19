package com.roslibrary.ros.message;

import java.util.List;

/**
 * Created by Eligah on 2017/8/31.
 */

public class LaserScan extends Message {

    public String topic;
    public MsgBean msg;
    public String op;

    public static class MsgBean {

        public float angle_min;
        public float range_min;
        public float scan_time;
        public float range_max;
        public float angle_increment;
        public float angle_max;
        public Header header;
        public float time_increment;
        public List<Float> ranges;
        public List<Float> intensities;
    }
}
