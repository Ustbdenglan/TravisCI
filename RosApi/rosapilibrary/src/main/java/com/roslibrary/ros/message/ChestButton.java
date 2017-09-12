package com.roslibrary.ros.message;

/**
 * Created by Eligah on 2017/9/12.
 */

public class ChestButton {

    public String topic;
    public MsgBean msg;
    public String op;

    public static class MsgBean {

        public DataBean data;

        public static class DataBean {

            public boolean close_press;
            public int led_state;
        }
    }
}
