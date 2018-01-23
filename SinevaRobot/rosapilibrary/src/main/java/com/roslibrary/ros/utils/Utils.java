package com.roslibrary.ros.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.roslibrary.ros.entity.PublishEvent;
import com.roslibrary.ros.entity.UniWsResult;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Eligah on 2017/9/4.
 */

public class Utils {

    /**
     * 数据解析
     *
     * @param jsonStr JSON字符串
     * @return UniWsResult<T> 数据对象
     */
    public static <T> UniWsResult<T> parseJson(String jsonStr,Class c) {
        Gson gson = new Gson();
        Type objectType = type(UniWsResult.class, c);
        return gson.fromJson(jsonStr, objectType);
    }
    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    public static Bitmap parseMapData(PublishEvent msgMapData) {
        Bitmap bitmap = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(msgMapData.msg);
            JSONArray dataArray = (JSONArray) jsonObj.get("data");
            JSONObject jsonInfo = (JSONObject) jsonObj.get("info");

            int width = (int) (long) jsonInfo.get("width");
            int height = (int) (long) jsonInfo.get("height");

            JSONObject jsonInf = (JSONObject) jsonInfo.get("map_load_time");

            int secs = (int) (long) jsonInf.get("secs");
            int nsecs = (int) (long) jsonInf.get("nsecs");

            if (secs != 0 && nsecs != 0) {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

                int len = dataArray.size();
                int x, y, d, p;
                for (int i = 0; i < len; i++) {
                    x = i % width;
                    y = height - 1 - i / width;
                    d = (int) (long) dataArray.get(i);
                    if (d == -1) {
                        bitmap.setPixel(x, y, Color.rgb(0x59, 0x59, 0x59));
                    } else {
                        p = 0x59 + (int) ((0xB1 - 0x59) * d / 100f);
                        bitmap.setPixel(x, y, Color.rgb(p, p, p));
                    }
                }
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap parseCameraData(PublishEvent cameraData) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(cameraData.msg);
            final String bitmaps = (String) jsonObj.get("data");

            Bitmap bitmap = null;
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
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showLargeLog(String logContent, int showLength, String tag) {
        if (logContent.length() > showLength) {
            String show = logContent.substring(0, showLength);
            Log.e(tag, show);
            if ((logContent.length() - showLength) > showLength) {
                String partLog = logContent.substring(showLength, logContent.length());
                showLargeLog(partLog, showLength, tag);
            } else {
                String printLog = logContent.substring(showLength, logContent.length());
                Log.e(tag, printLog);
            }

        } else {
            Log.e(tag, logContent);
        }
    }
}
