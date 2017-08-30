package com.sineva.rosclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.roslibrary.ros.rosbridge.ROSBridgeClient;
import com.sineva.rosclient.R;
import com.sineva.rosclient.RCApplication;

import java.util.Timer;
import java.util.TimerTask;


public class HeadActivity extends Activity {

    private ROSBridgeClient client;

    private Double def_linearSpeed = 2.5;
    private Double def_angularSpeed = 15.0;

    private String msg = "";

    private final static Double MAX_HEAD_LINEARSPEED = 5.0;
    private final static Double MAX_HEAD_ANGULARSPEED = 30.0;

    private final static String HEAD_BUTTON_TEXT_UP = "up";
    private final static String HEAD_BUTTON_TEXT_DOWN = "down";

    private Button up;
    private Button down;
    private Button left;
    private Button right;
    private SeekBar linear;
    private SeekBar angular;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);

        up = (Button) findViewById(R.id.btn_forward);
        up.setText(HEAD_BUTTON_TEXT_UP);
        down = (Button) findViewById(R.id.btn_backward);
        down.setText(HEAD_BUTTON_TEXT_DOWN);
        left = (Button) findViewById(R.id.btn_left);
        right = (Button) findViewById(R.id.btn_right);
        linear = (SeekBar) findViewById(R.id.sb_linear);
        angular = (SeekBar) findViewById(R.id.sb_angular);
        TextView tvLinear = (TextView) findViewById(R.id.tv_linear);
        tvLinear.setText("Cabrage Speed");
        TextView tvAngular = (TextView) findViewById(R.id.tv_angular);
        tvAngular.setText("Rotate Speed");

        setListener();

        client = ((RCApplication) getApplication()).getRosClient();
    }

    private void setListener() {
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                changeLinearMessageAndSend(String.valueOf(def_linearSpeed));
                            }
                        }, 0, 550);
                        break;
                    case MotionEvent.ACTION_UP:
                        stopMovemet();
                        break;
                }
                return true;
            }
        });

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                changeLinearMessageAndSend("-" + String.valueOf(def_linearSpeed));
                            }
                        }, 0, 550);
                        break;
                    case MotionEvent.ACTION_UP:
                        stopMovemet();
                        break;
                }
                return true;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                changeAngularMessageAndSend("-" + String.valueOf(def_angularSpeed));
                            }
                        }, 0, 550);
                        break;
                    case MotionEvent.ACTION_UP:
                        stopMovemet();
                        break;
                }
                return true;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                changeAngularMessageAndSend(String.valueOf(def_angularSpeed));
                            }
                        }, 0, 550);
                        break;
                    case MotionEvent.ACTION_UP:
                        stopMovemet();
                        break;
                }
                return true;
            }
        });

        linear.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                def_linearSpeed = progress / 100 * MAX_HEAD_LINEARSPEED;
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
                def_angularSpeed = progress / 100 * MAX_HEAD_ANGULARSPEED;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void stopMovemet() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
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

    public void onStopClick(View view) {
        stopMovemet();
    }
}
