package com.sineva.rosapidemo.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sineva.rosapidemo.R;


/**
 * dialog
 * Created by lhw on 2018/1/23 .
 */
public class PromptDialog extends DialogFragment {

    private String tag = "";
    private FragmentManager mFragmentManager;

    private CharSequence mTitleText = "提示";
    private CharSequence mContentText = "";
    private CharSequence mCancelText = "";
    private CharSequence mConfirmText = "确定";

    private boolean mCancelable = false;

    private DialogInterface.OnClickListener mConfirmListener;
    private DialogInterface.OnClickListener mCancelListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_prompt, null);
        TextView titleTv = (TextView) view.findViewById(R.id.dialog_title);
        TextView textTv = (TextView) view.findViewById(R.id.dialog_text);
        titleTv.setText(mTitleText);
        textTv.setText(mContentText);
        builder.setView(view)
                .setCancelable(mCancelable)
                .setPositiveButton(mConfirmText, mConfirmListener)
                .setNegativeButton(mCancelText, mCancelListener);
        return builder.create();
    }

    public static PromptDialog create(FragmentManager fragmentManager) {
        return create(fragmentManager, "tag");
    }

    public static PromptDialog create(FragmentManager fragmentManager, String tag) {
        PromptDialog dialogFragment = new PromptDialog();
        dialogFragment.mFragmentManager = fragmentManager;
        dialogFragment.tag = tag;
        return dialogFragment;
    }

    public PromptDialog setTitleText(CharSequence text) {
        mTitleText = text;
        return this;
    }

    public PromptDialog setContentText(CharSequence text) {
        mContentText = text;
        return this;
    }

    public PromptDialog setPositiveBtnText(CharSequence text) {
        mConfirmText = text;
        return this;
    }

    public PromptDialog setNegativeBtnText(CharSequence text) {
        mCancelText = text;
        return this;
    }

    public PromptDialog setDialogCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return this;
    }

    public PromptDialog setPositiveBtnListener(DialogInterface.OnClickListener listener) {
        mConfirmListener = listener;
        return this;
    }

    public PromptDialog setNegativeBtnListener(DialogInterface.OnClickListener listener) {
        mCancelListener = listener;
        return this;
    }

    public void show() {
        super.show(mFragmentManager, tag);
    }
}
