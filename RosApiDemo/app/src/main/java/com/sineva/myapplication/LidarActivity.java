package com.sineva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roslibrary.ros.entity.PublishEvent;
import com.sineva.EventInterface;
import com.sineva.entity.LidarData;
import com.sineva.implementation.LidarClient;

import java.util.List;

import de.greenrobot.event.EventBus;

public class LidarActivity extends Activity implements EventInterface {

    private RelativeLayout root;
    private LidarView lidarView;
    private TextView tvSecs;
    private TextView tvNsecs;
    private LidarClient mLidarClient;
    private LidarData mLidarData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lidar);

        Intent intent = getIntent();

        mLidarClient = LidarClient.getLidarClientInstance(intent.getStringExtra("ip"), intent.getStringExtra("port"), this);
        mLidarClient.initClient();
        EventBus.getDefault().register(this);

        root = (RelativeLayout) findViewById(R.id.root);
        lidarView = new LidarView(this);
        tvSecs = (TextView) findViewById(R.id.tv_secs);
        tvNsecs = (TextView) findViewById(R.id.tv_nsecs);
    }

    @Override
    public void onEvent(String s) {
        mLidarData = mLidarClient.getLidarData();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                root.removeView(lidarView);
                tvSecs.setText(Integer.toString(mLidarData.msg.header.stamp.secs));
                tvNsecs.setText(Integer.toString(mLidarData.msg.header.stamp.nsecs));
                List<Float> ranges = mLidarData.msg.ranges;
                lidarView.setdata(ranges);
                lidarView.invalidate();
                root.addView(lidarView);
            }
        });
    }

    @Override
    public void onEvent(PublishEvent publishEvent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mLidarClient.shutDownClient();
    }

}
