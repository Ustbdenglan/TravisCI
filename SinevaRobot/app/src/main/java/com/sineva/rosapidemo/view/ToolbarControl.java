package com.sineva.rosapidemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sineva.rosapidemo.R;

/**
 * Created by sin on 2018/1/17.
 */

public class ToolbarControl extends Toolbar {

    private static final String TAG = ToolbarControl.class.getSimpleName();

    private String titleText;
    private ImageView leftButton;
    private TextView titleTextView;
    private ImageView rightButton;

    public ToolbarControl(Context context) {
        super(context);
        init(context, null);
    }

    public ToolbarControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.toolbar_control, this, true);

        //很重要
        setContentInsetsRelative(0, 0);

        leftButton=view.findViewById(R.id.toolbar_left_button1);
        titleTextView=view.findViewById(R.id.toolbar_title1);
        rightButton=view.findViewById(R.id.toolbar_right_button1);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ToolbarControl, 0, 0);
        titleText = a.getString(R.styleable.ToolbarControl_titleText);
        titleTextView.setText(titleText);

        a.recycle();
    }

    public void setTitle(String titleStr) {
        if (titleTextView != null) {
            titleTextView.setText(titleStr);
        }
    }

    public void setTitleByResourceId(int rid) {
        if (titleTextView != null) {
            titleTextView.setText(rid);
        }
    }

    public void setRightButtonImage(int resourceId) {
        if (rightButton != null) {
            rightButton.setImageResource(resourceId);
        }
    }

    public void showImage() {
        if (rightButton != null) {
            rightButton.setVisibility(View.VISIBLE);
        }
    }

    public void hideImage() {
        if (rightButton != null) {
            rightButton.setVisibility(View.GONE);
        }
    }

    public void hide() {
        this.setVisibility(View.GONE);
    }

    public void setBackButtonOnClickListerner(OnClickListener listerner) {
        if (leftButton != null && listerner != null) {
            leftButton.setOnClickListener(listerner);
        }
    }

    public void setRightButtonOnClickListener(OnClickListener listener) {
        if (rightButton != null && listener != null) {
            rightButton.setOnClickListener(listener);
        }
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }
}