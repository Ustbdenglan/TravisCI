package com.sineva.entity;

import java.util.List;

/**
 * Created by Eligah on 2017/8/31.
 */

public class LidarData {

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
        public HeaderBean header;
        public double time_increment;
        public List<Float> ranges;
        public List<Integer> intensities;

        public static class HeaderBean {

            public StampBean stamp;
            public String frame_id;
            public int seq;

            public static class StampBean {

                public int secs;
                public int nsecs;
            }
        }
    }
}
