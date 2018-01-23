package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;

import com.sineva.rosapidemo.R;

/**
 * Created by Eligah on 2017/10/12.
 */

public class EditSequenceActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_sequence;
    }

    @Override
    protected void initView() {

    }

    public void onChooseScene(View view) {
        startActivity(new Intent(EditSequenceActivity.this,ChooseSceneActivity.class));
    }
}
