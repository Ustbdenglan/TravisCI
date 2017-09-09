package com.sineva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sineva.implementation.MoveBaseClient;


public class MoveBaseActivity extends Activity {

    private final static Double MAX_MOVEBASE_LINEARSPEED = 0.3;
    private final static Double MAX_MOVEBASE_ANGULARSPEED = 0.3;

    private SeekBar linear;
    private SeekBar angular;

    private MoveBaseClient mMoveBaseClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movebase_head);

        Intent intent = getIntent();

        mMoveBaseClient = MoveBaseClient.getMoveBaseClientInstance(intent.getStringExtra("ip"), intent.getStringExtra("port"), this);

        linear = (SeekBar) findViewById(R.id.sb_linear);
        angular = (SeekBar) findViewById(R.id.sb_angular);
        TextView tvLinear = (TextView) findViewById(R.id.tv_linear);
        tvLinear.setText("Forward Speed");
        TextView tvAngular = (TextView) findViewById(R.id.tv_angular);
        tvAngular.setText("Rotate Speed");

        setListener();

    }

    private void setListener() {

        linear.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMoveBaseClient.setMaxCabrageSpeed((progress * MAX_MOVEBASE_LINEARSPEED) / 100);
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
                mMoveBaseClient.setMaxRollSpeed(progress * MAX_MOVEBASE_ANGULARSPEED / 100);
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
        mMoveBaseClient.stopMovemet();
    }

    public void onForwardClick(View view) {
        mMoveBaseClient.forward();
    }

    public void onBackwardClick(View view) {
        mMoveBaseClient.backward();
    }

    public void onLeftClick(View view) {
        mMoveBaseClient.left();
    }

    public void onRightClick(View view) {
        mMoveBaseClient.right();
    }
}
