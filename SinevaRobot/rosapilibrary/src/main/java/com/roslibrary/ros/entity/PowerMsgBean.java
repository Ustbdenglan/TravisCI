package com.roslibrary.ros.entity;

import java.util.List;

/**
 * Created by sin on 2018/1/16.
 * 电池电量msg bean类
 */

public class PowerMsgBean<T> {
    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        public int battery_status;
        public float discharge_voltage;
        public float charge_voltage;
        public int estop;
        public int head_motor_mid_crossing;
        public int shutdown;
        public int charging_status;
        public List<Integer> sonar;
        public List<Integer> ir;
        public List<Integer> secure_sw;
        public List<Integer> errors;
        public List<Integer> autocharge_ir;
    }

}
