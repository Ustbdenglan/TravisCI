package com.roslibrary.ros.message;

@MessageType(string = "std_msgs/String")
public class SemanticRequest extends Message {
    public SemanticRequest(String args) {
        jsonStr = args;
    }

    public String jsonStr;
}
