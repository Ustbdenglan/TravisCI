package com.roslibrary.ros.entity;

/**
 * Created by sin on 2018/1/16.
 * UniWsResult通用的数据类  T msg为具体的实体类
 */

public class UniWsResult<T> {

    private String op;

    private String topic;

    private T msg;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
