package com.sineva.rosapidemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.roslibrary.ros.RosApiClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Timer;
import java.util.TimerTask;


public class MapActivity extends Activity {

    private ImageView ivMap;
    private RosApiClient mRosApiClientInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        ivMap = (ImageView) findViewById(R.id.iv_map);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showMap();
            }
        }, 3000);
    }

    private void showMap() {
        String mapData = mRosApiClientInstance.getMapData();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(mapData);
            JSONArray dataArray = (JSONArray) jsonObj.get("data");
            JSONObject jsonInfo = (JSONObject) jsonObj.get("info");
            int width = (int) (long) jsonInfo.get("width");
            int height = (int) (long) jsonInfo.get("height");

            final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);

            int len = dataArray.size();
            int x, y, d, p;
            for (int i = 0; i < len; i++) {
                x = i % width;
                y = i / width;
                d = (int) (long) dataArray.get(i);
                if (d == -1) {
                    bitmap.setPixel(x, y, Color.rgb(0x59, 0x59, 0x59));
                } else {
                    p = 0x59 + (int) ((0xB1 - 0x59) * d / 100f);
                    bitmap.setPixel(x, y, Color.rgb(p, p, p));
                }
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ivMap.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
