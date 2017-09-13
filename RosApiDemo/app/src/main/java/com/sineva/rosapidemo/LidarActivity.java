package com.sineva.rosapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.LaserScan;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LidarActivity extends Activity {

    private RelativeLayout root;
    private LidarView lidarView;
    private TextView tvSecs;
    private TextView tvNsecs;
    private RosApiClient mRosApiClientInstance;
    private LaserScan mLaserScanData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lidar);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        root = (RelativeLayout) findViewById(R.id.root);
        lidarView = new LidarView(this);
        tvSecs = (TextView) findViewById(R.id.tv_secs);
        tvNsecs = (TextView) findViewById(R.id.tv_nsecs);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showLaserScan();
            }
        }, 3000, 50);
    }

    private void showLaserScan() {
        mLaserScanData = mRosApiClientInstance.getLaserScanData();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                root.removeView(lidarView);
                tvSecs.setText(Integer.toString(mLaserScanData.msg.header.stamp.secs));
                tvNsecs.setText(Integer.toString(mLaserScanData.msg.header.stamp.nsecs));
                List<Float> ranges = mLaserScanData.msg.ranges;
                lidarView.setdata(ranges);
                lidarView.invalidate();
                root.addView(lidarView);
            }
        });
    }
}
