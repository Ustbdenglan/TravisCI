package com.sineva.rosapidemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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


public class CameraActivity extends Activity {

    private ImageView ivCamera;

    private RosApiClient mRosApiClientInstance;

    private Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ivCamera = (ImageView) findViewById(R.id.iv_camera);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                showCameraPicture();
            }
        }, 3000, 50);
    }

    private void showCameraPicture() {
        String cameraData = mRosApiClientInstance.getCameraData();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(cameraData);
            final String bitmaps = (String) jsonObj.get("data");

            byte[] imgByte = null;
            InputStream input = null;
            try {
                imgByte = Base64.decode(bitmaps, Base64.DEFAULT);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                input = new ByteArrayInputStream(imgByte);
                SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
                mBitmap = (Bitmap) softRef.get();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivCamera.setImageDrawable(new BitmapDrawable(mBitmap));
                    }
                });
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
