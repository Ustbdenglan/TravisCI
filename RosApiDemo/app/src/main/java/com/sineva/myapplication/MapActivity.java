package com.sineva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.roslibrary.ros.entity.PublishEvent;
import com.sineva.EventInterface;
import com.sineva.implementation.MapClient;

import de.greenrobot.event.EventBus;


public class MapActivity extends Activity implements EventInterface {

    private ImageView ivMap;
    private MapClient mMapClient;
    private Bitmap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();

        mMapClient = MapClient.getMapClientInstance(intent.getStringExtra("ip"), intent.getStringExtra("port"), this);
        mMapClient.initClient();
        EventBus.getDefault().register(this);

        ivMap = (ImageView) findViewById(R.id.iv_map);
    }

    @Override
    public void onEvent(String s) {
        mMap = mMapClient.getMapData();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivMap.setImageDrawable(new BitmapDrawable(mMap));
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
        mMapClient.shutDownClient();
    }

}
