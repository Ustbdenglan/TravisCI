package com.sineva.rosclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.jilk.ros.rosbridge.ROSBridgeClient;
import com.sineva.rosclient.R;
import com.sineva.rosclient.RCApplication;


public class HeadActivity extends Activity implements View.OnClickListener {

    private ROSBridgeClient client;

    private Double linearSpeed = 2.5;
    private Double angularSpeed = 15.0;

    private String msg = "";

    private final static Double MAX_HEAD_LINEARSPEED = 5.0;
    private final static Double MAX_HEAD_ANGULARSPEED = 30.0;
    private final static String HEAD_BUTTON_TEXT_UP = "up";
    private final static String HEAD_BUTTON_TEXT_DOWN = "down";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);

        Button up = (Button) findViewById(R.id.button);
        up.setText(HEAD_BUTTON_TEXT_UP);
        up.setOnClickListener(this);
        Button down = (Button) findViewById(R.id.button2);
        down.setText(HEAD_BUTTON_TEXT_DOWN);
        down.setOnClickListener(this);
        Button left = (Button) findViewById(R.id.button3);
        left.setOnClickListener(this);
        left.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
        Button right = (Button) findViewById(R.id.button4);
        right.setOnClickListener(this);
        Button stop = (Button) findViewById(R.id.button5);
        stop.setOnClickListener(this);
        SeekBar linear = (SeekBar) findViewById(R.id.linear);
        linear.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                linearSpeed = progress / 100 * MAX_HEAD_LINEARSPEED;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        SeekBar angular = (SeekBar) findViewById(R.id.angular);
        angular.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                angularSpeed = progress / 100 * MAX_HEAD_ANGULARSPEED;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        client = ((RCApplication) getApplication()).getRosClient();
    }

    @Override
    public void onClick(View v) {
        msg = "";
        switch (v.getId()) {
            case R.id.button:
                changeLinearMessageAndSend(String.valueOf(linearSpeed));
                break;
            case R.id.button2:
                changeLinearMessageAndSend("-" + String.valueOf(linearSpeed));
                break;
            case R.id.button3:
                changeAngularMessageAndSend("-" + String.valueOf(angularSpeed));
                break;
            case R.id.button4:
                changeAngularMessageAndSend(String.valueOf(angularSpeed));
                break;
            case R.id.button5:
                stopMovemet();
                break;
            default:
                break;
        }

    }

    private void stopMovemet() {
        msg = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
        sendMsgToTopic(msg);
    }

    private void sendMsgToTopic(String msg) {
        client.send("{\"op\":\"publish\",\"topic\":\"/joy_teleop/cmd_vel_head\",\"msg\":{" + msg + "}}");
    }

    private void changeLinearMessageAndSend(String strLinearSpeed) {
        msg = "\"linear\":{\"x\":" + strLinearSpeed + ",\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
        sendMsgToTopic(msg);
    }

    private void changeAngularMessageAndSend(String strAngularSpeed) {
        msg = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + strAngularSpeed + "}";
        sendMsgToTopic(msg);
    }

}
