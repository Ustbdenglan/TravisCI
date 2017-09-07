package com.sineva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sineva.implementation.HeadClient;


public class HeadActivity extends Activity {

    private final static Double MAX_HEAD_LINEARSPEED = 5.0;
    private final static Double MAX_HEAD_ANGULARSPEED = 30.0;

    private final static String HEAD_BUTTON_TEXT_UP = "up";
    private final static String HEAD_BUTTON_TEXT_DOWN = "down";

    private Button up;
    private Button down;
    private SeekBar linear;
    private SeekBar angular;

    private HeadClient mHeadClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movebase_head);

        Intent intent = getIntent();

        mHeadClient = HeadClient.getHeadClientInstance(intent.getStringExtra("ip"), intent.getStringExtra("port"), this);

        up = (Button) findViewById(R.id.btn_forward);
        up.setText(HEAD_BUTTON_TEXT_UP);
        down = (Button) findViewById(R.id.btn_backward);
        down.setText(HEAD_BUTTON_TEXT_DOWN);
        linear = (SeekBar) findViewById(R.id.sb_linear);
        angular = (SeekBar) findViewById(R.id.sb_angular);
        TextView tvLinear = (TextView) findViewById(R.id.tv_linear);
        tvLinear.setText("Cabrage Speed");
        TextView tvAngular = (TextView) findViewById(R.id.tv_angular);
        tvAngular.setText("Rotate Speed");

        setListener();
    }

    private void setListener() {

        linear.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mHeadClient.setMaxCabrageSpeed(progress * MAX_HEAD_LINEARSPEED / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        angular.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mHeadClient.setMaxRollSpeed(progress * MAX_HEAD_ANGULARSPEED / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void onStopClick(View view) {
        mHeadClient.stopMovemet();
    }

    public void onForwardClick(View view) {
        mHeadClient.up();
    }

    public void onBackwardClick(View view) {
        mHeadClient.down();
    }

    public void onLeftClick(View view) {
        mHeadClient.left();
    }

    public void onRightClick(View view) {
        mHeadClient.right();
    }
}
