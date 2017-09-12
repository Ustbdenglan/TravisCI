package com.sineva.rosapidemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

/**
 * Created by Eligah on 2017/8/31.
 */

public class LidarView extends View {
    private List<Float> ranges;
    private final int heightPixels;
    private final int widthPixels;

    public LidarView(Context context) {
        super(context);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        heightPixels = dm.heightPixels;//1208
        widthPixels = dm.widthPixels;//720
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        for (int i = ranges.size() - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                if (ranges.get(i) > 0.02 && ranges.get(i) < 30) {
                    paint.setStrokeWidth(3);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.BLACK);
                    Float aFloat = ranges.get(i) * 50;
                    RectF rect = new RectF(widthPixels / 2 - aFloat, heightPixels / 2 - aFloat, widthPixels / 2 + aFloat, heightPixels / 2 + aFloat);
                    canvas.drawArc(rect, 167 + (ranges.size() - i) / 4, 1, true, paint);
                }
            }
        }
    }

    public void setdata(List<Float> ranges) {
        this.ranges = ranges;
    }
}
