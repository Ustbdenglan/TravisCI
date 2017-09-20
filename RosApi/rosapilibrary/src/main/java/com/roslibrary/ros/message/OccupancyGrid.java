package com.roslibrary.ros.message;

import java.util.List;

/**
 * Created by Eligah on 2017/9/19.
 */

public class OccupancyGrid extends Message{

    public String topic;
    public MsgBean msg;

    public static class MsgBean {

        public InfoBean info;
        public Header header;
        public List<Integer> data;

        public static class InfoBean {

            public OriginBean origin;
            public int width;
            public MapLoadTimeBean map_load_time;
            public float resolution;
            public int height;

            public static class OriginBean {
                public PositionBean position;
                public OrientationBean orientation;

                public static class PositionBean {

                    public double y;
                    public double x;
                    public double z;
                }

                public static class OrientationBean {

                    public double y;
                    public double x;
                    public double z;
                    public double w;
                }
            }

            public static class MapLoadTimeBean {

                public int secs;
                public int nsecs;
            }
        }
    }
}
