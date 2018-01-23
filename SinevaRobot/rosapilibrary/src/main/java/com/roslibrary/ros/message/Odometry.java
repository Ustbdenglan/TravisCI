package com.roslibrary.ros.message;

import java.util.List;

/**
 * Created by Eligah on 2017/9/12.
 */

public class Odometry extends Message {

    public String topic;
    public MsgBean msg;
    public String op;

    public static class MsgBean {

        public TwistBeanX twist;
        public Header header;
        public PoseBeanX pose;
        public String child_frame_id;

        public static class TwistBeanX {

            public TwistBean twist;
            public List<Float> covariance;

            public static class TwistBean {

                public LinearBean linear;
                public AngularBean angular;

                public static class LinearBean {

                    public double y;
                    public double x;
                    public double z;
                }

                public static class AngularBean {

                    public double y;
                    public double x;
                    public double z;
                }
            }
        }

        public static class PoseBeanX {

            public PoseBean pose;
            public List<Float> covariance;

            public static class PoseBean {

                public PositionBean position;
                public OrientationBean orientation;

                public static class PositionBean {

                    public float y;
                    public float x;
                    public float z;
                }

                public static class OrientationBean {

                    public float y;
                    public float x;
                    public float z;
                    public float w;
                }
            }
        }
    }
}
