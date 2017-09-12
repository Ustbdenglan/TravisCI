package com.roslibrary.ros.message;

import java.util.List;

/**
 * Created by Eligah on 2017/9/12.
 */

public class WheelState {
    public String topic;
    public MsgBean msg;
    public String op;

    public static class MsgBean {

        public HeaderBean header;
        public List<Float> velocity;
        public List<Integer> effort;
        public List<String> name;
        public List<Double> position;

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
