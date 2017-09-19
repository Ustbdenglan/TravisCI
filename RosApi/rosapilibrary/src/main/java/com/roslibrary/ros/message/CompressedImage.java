package com.roslibrary.ros.message;

import java.util.List;

/**
 * Created by Eligah on 2017/9/19.
 */

public class CompressedImage {
    public String topic;
    public MsgBean msg;
    public String op;

    public static class MsgBean {

        public Header header;
        public String format;
        public String data;
    }
}
