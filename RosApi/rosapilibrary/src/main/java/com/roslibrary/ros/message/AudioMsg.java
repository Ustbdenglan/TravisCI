package com.roslibrary.ros.message;

@MessageType(string = "std_msgs/Int16MultiArray")
public class AudioMsg extends Message {
    public short[] data;
}
