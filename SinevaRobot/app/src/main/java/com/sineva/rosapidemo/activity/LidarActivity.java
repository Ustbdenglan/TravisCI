package com.sineva.rosapidemo.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.roslibrary.ros.message.LaserScan;
import com.sineva.rosapidemo.R;

import java.util.List;

import butterknife.BindView;

public class LidarActivity extends BaseActivity {

    @BindView(R.id.tv_secs)
    TextView tvSecs;
    @BindView(R.id.tv_nsecs)
    TextView tvNsecs;
    @BindView(R.id.root)
    LinearLayout root;
    private LidarView lidarView;
    private LaserScan mLaserScanData;
    private String[] mTopicArray = {"/scan"};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_lidar;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //lidarView = new LidarView(this);
/*
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
        }*/
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
       /* if (mRosApiClientInstance != null) {
            mRosApiClientInstance.unSubscribeTopic(mTopicArray);
        }*/
    }
}
