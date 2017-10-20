package com.sineva.rosapidemo.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.roslibrary.ros.message.OccupancyGrid;
import com.sineva.rosapidemo.R;

import java.util.TimerTask;


public class MapActivity extends BaseActivity {

    private ImageView ivMap;

    private Bitmap mBitmap;

    private String[] mTopicArray = {"/map"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ivMap = (ImageView) findViewById(R.id.iv_map);

        if (mRosApiClientInstance != null) {
            mRosApiClientInstance.subscribeTopic(mTopicArray);
        }

        if (mTimer != null) {
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    showMap();
                }
            }, 0, 50);
        }
    }

    private void showMap() {
        OccupancyGrid mapData = mRosApiClientInstance.getMapData();
        if (null != mapData) {
            try {
                int width = mapData.msg.info.width;
                int height = mapData.msg.info.height;

                int secs = mapData.msg.info.map_load_time.secs;
                int nsecs = mapData.msg.info.map_load_time.nsecs;

                if (secs != 0 && nsecs != 0) {
                    mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

                    int len = mapData.msg.data.size();
                    int x, y, d, p;
                    for (int i = 0; i < len; i++) {
                        x = i % width;
                        y = height - 1 - i / width;

                        d = (int) (long) mapData.msg.data.get(i);
                        if (d == -1) {
                            mBitmap.setPixel(x, y, Color.rgb(0x59, 0x59, 0x59));
                        } else {
                            p = 0x59 + (int) ((0xB1 - 0x59) * d / 100f);
                            mBitmap.setPixel(x, y, Color.rgb(p, p, p));
                        }
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivMap.setImageDrawable(new BitmapDrawable(getResources(), mBitmap));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
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
