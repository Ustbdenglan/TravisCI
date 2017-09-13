package com.roslibrary.ros.message;

import java.util.List;

/**
 * Created by Eligah on 2017/9/12.
 */

public class WheelState extends Message {
    public String topic;
    public MsgBean msg;
    public String op;

    public static class MsgBean {

        public Header header;
        public List<Float> velocity;
        public List<Integer> effort;
        public List<String> name;
        public List<Double> position;
    }
}
