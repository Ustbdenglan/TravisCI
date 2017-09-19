package com.sineva.rosapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.roslibrary.ros.RosApiClient;
import com.roslibrary.ros.message.Odometry;

import java.util.Timer;
import java.util.TimerTask;


public class MobileBaseControllerActivity extends Activity {

    private RosApiClient mRosApiClientInstance;
    private TextView mTvPositionX;
    private TextView mTvPositionY;
    private TextView mTvPositionZ;
    private TextView mTvOrientationX;
    private TextView mTvOrientationY;
    private TextView mTvOrientationZ;
    private TextView mTvOrientationW;

    private Odometry mMoveBaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_base_controller);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        mTvPositionX = findViewById(R.id.tv_positionx);
        mTvPositionY = findViewById(R.id.tv_positiony);
        mTvPositionZ = findViewById(R.id.tv_positionz);
        mTvOrientationX = findViewById(R.id.tv_orientationx);
        mTvOrientationY = findViewById(R.id.tv_orientationy);
        mTvOrientationZ = findViewById(R.id.tv_orientationz);
        mTvOrientationW = findViewById(R.id.tv_orientationw);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showData();
            }
        }, 0, 500);
    }

    private void showData() {
        mMoveBaseData = mRosApiClientInstance.getMoveBaseData();
        if (null != mMoveBaseData) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvPositionX.setText("X : " + String.valueOf(mMoveBaseData.msg.pose.pose.position.x));
                    mTvPositionY.setText("Y : " + String.valueOf(mMoveBaseData.msg.pose.pose.position.y));
                    mTvPositionZ.setText("Z : " + String.valueOf(mMoveBaseData.msg.pose.pose.position.z));
                    mTvOrientationX.setText("X : " + String.valueOf(mMoveBaseData.msg.pose.pose.orientation.x));
                    mTvOrientationY.setText("Y : " + String.valueOf(mMoveBaseData.msg.pose.pose.orientation.y));
                    mTvOrientationZ.setText("Z : " + String.valueOf(mMoveBaseData.msg.pose.pose.orientation.z));
                    mTvOrientationW.setText("W : " + String.valueOf(mMoveBaseData.msg.pose.pose.orientation.w));
                }
            });
        }
    }
}
