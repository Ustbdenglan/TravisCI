package com.sineva.rosclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sineva.rosclient.R;


public class ChooseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }

    public void onMoveBaseClick(View view) {
        startActivity(new Intent(ChooseActivity.this, MoveBaseActivity.class));
    }

    public void onHeadClick(View view) {
        startActivity(new Intent(ChooseActivity.this, HeadActivity.class));
    }
}
