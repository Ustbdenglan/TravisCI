package com.sineva.rosapidemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.roslibrary.ros.message.CompressedImage;
import com.sineva.rosapidemo.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.TimerTask;


public class CameraActivity extends BaseActivity {

    private ImageView ivCamera;

    private Bitmap mBitmap = null;

    private String[] mTopicArray = {"/rgbd/rgb/image_raw/compressed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ivCamera = (ImageView) findViewById(R.id.iv_camera);

        if (mRosApiClientInstance != null) {
            mRosApiClientInstance.subscribeTopic(mTopicArray);
        }

        if (mTimer != null) {
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    showCameraPicture();
                }
            }, 0, 50);
        }
    }

    private void showCameraPicture() {
        CompressedImage compressedImage = mRosApiClientInstance.getCameraData();
        if (null != compressedImage) {
            String cameraData = compressedImage.msg.data;
            try {
                byte[] imgByte = null;
                InputStream input = null;
                try {
                    imgByte = Base64.decode(cameraData, Base64.DEFAULT);
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
}
