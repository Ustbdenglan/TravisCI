package com.sineva.rosapidemo.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.sineva.rosapidemo.R;

/**
 * Created by sin on 2018/1/22.
 */

public class SeekBarPopupWindow extends PopupWindow {
    private Context mContext;

    private View view;
    private SeekBar sbSelect;
    private OnSeekBarProgressChangeLintener mOnSeekBarProgressChangeLintener;



    public SeekBarPopupWindow(Context mContext, final View mView, final OnSeekBarProgressChangeLintener mOnSeekBarProgressChangeLintener) {

        this.view = LayoutInflater.from(mContext).inflate(R.layout.popup_select_speed_dialog, null);
        sbSelect= this.view.findViewById(R.id.sb_select);

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = SeekBarPopupWindow.this.view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        sbSelect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mOnSeekBarProgressChangeLintener.onSeekBarProgressChange(i,mView);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mOnSeekBarProgressChangeLintener.onSeekBarProgressEnd(seekBar.getProgress(),mView);
            }
        });


        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //this.setWidth((int) (ScreenUtils.getScreenWidth(mContext)*0.98));
        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);


        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);




    }


    public interface OnSeekBarProgressChangeLintener{
        int onSeekBarProgressChange(int progress,View view);
        int onSeekBarProgressEnd(int progress,View view);
    }
    public void setOnSeekBarProgressChangeLintener(OnSeekBarProgressChangeLintener onSeekBarProgressChangeLintener){
        this.mOnSeekBarProgressChangeLintener=onSeekBarProgressChangeLintener;
    }
}
