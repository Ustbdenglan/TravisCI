package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;

import com.sineva.rosapidemo.R;

/**
 * Created by Eligah on 2017/10/12.
 */

public class TourGuideActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide);
    }

    public void onCreateMapClick(View view) {
        startActivity(new Intent(TourGuideActivity.this, CreateMapActivity.class));
    }

    public void onLocalMapClick(View view) {
        startActivity(new Intent(TourGuideActivity.this, LocalMapActivity.class));
    }

    public void onEditSequence(View view) {
        startActivity(new Intent(TourGuideActivity.this, EditSequenceActivity.class));
    }
}
