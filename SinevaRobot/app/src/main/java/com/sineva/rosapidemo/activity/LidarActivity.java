package com.sineva.rosapidemo.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roslibrary.ros.message.LaserScan;
import com.sineva.rosapidemo.R;

import java.util.List;
import java.util.TimerTask;

public class LidarActivity extends BaseActivity {

    private RelativeLayout root;
    private LidarView lidarView;
    private TextView tvSecs;
    private TextView tvNsecs;
    private LaserScan mLaserScanData;
    private String[] mTopicArray = {"/scan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lidar);

        root = (RelativeLayout) findViewById(R.id.root);
        lidarView = new LidarView(this);
        tvSecs = (TextView) findViewById(R.id.tv_secs);
        tvNsecs = (TextView) findViewById(R.id.tv_nsecs);


        if (mRosApiClientInstance != null) {
            mRosApiClientInstance.subscribeTopic(mTopicArray);
        }

        if (mTimer != null) {
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    showLaserScan();
                }
            }, 0, 50);
        }
    }

    private void showLaserScan() {
        mLaserScanData = mRosApiClientInstance.getLaserScanData();
        if (null != mLaserScanData) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRosApiClientInstance != null) {
            mRosApiClientInstance.unSubscribeTopic(mTopicArray);
        }
    }
}
