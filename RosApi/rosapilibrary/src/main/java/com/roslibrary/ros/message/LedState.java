package com.roslibrary.ros.message;

/**
 * Created by Eligah on 2017/9/13.
 */

public class LedState extends Message {

    public String topic;
    public MsgBean msg;
    public String op;

    public static class MsgBean {

        public int data;
    }
}
