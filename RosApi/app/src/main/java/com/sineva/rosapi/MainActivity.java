package com.sineva.rosapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;

import com.roslibrary.ros.RosApiClient;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private RosApiClient mRosApiClientInstance;
    private ImageView mIv;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = (ImageView) findViewById(R.id.imageView);
        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();
        mRosApiClientInstance.initClient("192.168.31.225", "9090");
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String aimrPowerState = mRosApiClientInstance.getCameraData();
                try {
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObj = (JSONObject) parser.parse(aimrPowerState);
                    final String bitmaps = (String) jsonObj.get("data");

                    byte[] imgByte = null;
                    InputStream input = null;
                    try {
                        imgByte = Base64.decode(bitmaps, Base64.DEFAULT);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 1;
                        input = new ByteArrayInputStream(imgByte);
                        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
                        bitmap = (Bitmap) softRef.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        if (imgByte != null) {
                            imgByte = null;
                        }

                        if (input != null) {
                            try {
                                input.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mIv.setImageDrawable(new BitmapDrawable(bitmap));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5000, 60);*/
    }
}
