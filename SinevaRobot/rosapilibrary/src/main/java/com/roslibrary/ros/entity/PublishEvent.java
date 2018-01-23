package com.roslibrary.ros.entity;

import com.roslibrary.ros.rosbridge.operation.Operation;

/**
 * EventBus event entity,describe ros server response info
 */

public class PublishEvent {
    public String originMsg;
    public String msg;
    public String id;
    public String name;
    public String op;

    public PublishEvent(String message, Operation operation, String name, String content) {
        if (null != operation) {
            id = operation.id;
            op = operation.op;
        }
        this.name = name;
        msg = content;
        originMsg = message;
    }
}
