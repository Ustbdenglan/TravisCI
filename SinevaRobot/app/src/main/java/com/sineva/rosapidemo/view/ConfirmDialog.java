package com.sineva.rosapidemo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sineva.rosapidemo.R;

/**
 * Created by sin on 2018/1/23.
 */

public class ConfirmDialog extends Dialog implements View.OnClickListener {

    //提示语
    private TextView mSlogan;
    //取消按钮
    private Button mCancel;
    //确认按钮
    private Button mConfirm;

    //    private MyCollectionActivity mActivity;
    private OnConfirmListener mListener;

    public ConfirmDialog(Context context) {
        super(context, R.style.Theme_Light_FullScreenDialogAct);
        setContentView(R.layout.dialog_confirm);

        initView();
        initData();
        initEvent();
    }

    @Override
    public void show() {
        super.show();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mSlogan = (TextView) findViewById(R.id.simple_dialog_slogan);
        mCancel = (Button) findViewById(R.id.simple_dialog_cancel);
        mConfirm = (Button) findViewById(R.id.simple_dialog_confirm);
    }

    /**
     * 初始化控件、数据
     */
    private void initData() {

    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    public ConfirmDialog setClickListener(String slogan, OnConfirmListener mListener) {
        mSlogan.setText(slogan);
        this.mListener = mListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.simple_dialog_cancel:
                cancel();
                break;
            case R.id.simple_dialog_confirm:
                dismiss();
                mListener.onConfirm();
                break;
            default:
                break;
        }
    }

    public interface OnConfirmListener {
        void onConfirm();
    }
}
