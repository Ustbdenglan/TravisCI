package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sineva.rosapidemo.R;

/**
 * Created by Eligah on 2017/10/12.
 */

public class EditSequenceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sequence);
    }

    public void onChooseScene(View view) {
        startActivity(new Intent(EditSequenceActivity.this,ChooseSceneActivity.class));
    }
}
