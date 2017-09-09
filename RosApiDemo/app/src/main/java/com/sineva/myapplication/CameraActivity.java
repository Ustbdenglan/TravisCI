package com.sineva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.roslibrary.ros.entity.PublishEvent;
import com.sineva.EventInterface;
import com.sineva.implementation.CameraClient;

import de.greenrobot.event.EventBus;


public class CameraActivity extends Activity implements EventInterface {

    private ImageView ivCamera;
    private Bitmap mCameraData;
    private CameraClient mCameraClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent intent = getIntent();

        mCameraClient = CameraClient.getCameraClientInstance(intent.getStringExtra("ip"), intent.getStringExtra("port"), this);
        mCameraClient.initClient();
        EventBus.getDefault().register(this);

        ivCamera = (ImageView) findViewById(R.id.iv_camera);
    }

    @Override
    public void onEvent(String s) {

    }

    @Override
    public void onEvent(PublishEvent publishEvent) {
        mCameraData = mCameraClient.getCameraData();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivCamera.setImageDrawable(new BitmapDrawable(mCameraData));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mCameraClient.shutDownClient();
    }
}
