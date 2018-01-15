package com.roslibrary.ros.message;

import java.util.List;

public class AimrPowerState extends Message {

    public String topic;
    public MsgBean msg;
    public String op;

    public static class MsgBean {

        public DataBean data;

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
}
