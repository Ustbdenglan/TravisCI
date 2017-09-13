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

        public double angle_min;
        public double range_min;
        public double scan_time;
        public int range_max;
        public double angle_increment;
        public double angle_max;
        public Header header;
        public double time_increment;
        public List<Float> ranges;
        public List<Integer> intensities;
    }
}
