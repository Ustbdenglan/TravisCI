package com.sineva.rosapidemo.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.roslibrary.ros.Constants;
import com.sineva.rosapidemo.R;

import java.util.Timer;
import java.util.TimerTask;


public class HeadActivity extends BaseActivity {

    private final static Double MAX_HEAD_LINEARSPEED = 5.0;
    private final static Double MAX_HEAD_ANGULARSPEED = 30.0;

    private Timer mTimer;

    public static final int DELAY = 0;
    public static final int PERIOD = 500;

    private Double mHead_Def_linearSpeed = 2.5;
    private Double mHead_Def_angularSpeed = 15.0;

    private String mHeadMsg;

    private ImageButton up;
    private ImageButton down;
    private ImageButton left;
    private ImageButton right;

    private SeekBar linear;
    private SeekBar angular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movebase_head);

        up = (ImageButton) findViewById(R.id.btn_forward);
        down = (ImageButton) findViewById(R.id.btn_backward);
        left = (ImageButton) findViewById(R.id.btn_left);
        right = (ImageButton) findViewById(R.id.btn_right);
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
                setHeadCabrageSpeed(progress * MAX_HEAD_LINEARSPEED / 100);
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
                setHeadRollSpeed(progress * MAX_HEAD_ANGULARSPEED / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        headUp();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopHeadMovemet();
                        break;
                }
                return false;
            }
        });

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        headDown();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopHeadMovemet();
                        break;
                }
                return false;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        headTurnLeft();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopHeadMovemet();
                        break;
                }
                return false;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        headTurnRight();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopHeadMovemet();
                        break;
                }
                return false;
            }
        });
    }

    //control the robot's head
    public void headUp() {
        stopHeadMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeHeadLinearMessageAndSend(String.valueOf(mHead_Def_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void headDown() {
        stopHeadMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeHeadLinearMessageAndSend("-" + String.valueOf(mHead_Def_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void headTurnLeft() {
        stopHeadMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeHeadAngularMessageAndSend(String.valueOf(mHead_Def_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void headTurnRight() {
        stopHeadMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeHeadAngularMessageAndSend("-" + String.valueOf(mHead_Def_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void stopHeadMovemet() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mHeadMsg = Constants.STOP_MESSAGE;
        sendHeadMsgToTopic(mHeadMsg);
    }

    public void setHeadRollSpeed(Double rollSpeed) {
        mHead_Def_angularSpeed = rollSpeed;
    }

    public void setHeadCabrageSpeed(Double cabrageSpeed) {
        mHead_Def_linearSpeed = cabrageSpeed;
    }

    private void sendHeadMsgToTopic(String msg) {
        if (mRosApiClientInstance != null) {
            mRosApiClientInstance.publishTopic("/joy_teleop/cmd_vel_head",msg);
            //mRosApiClientInstance.send("{\"op\":\"publish\",\"topic\":\"/joy_teleop/cmd_vel_head\",\"msg\":{" + msg + "}}");
        }
    }

    private void changeHeadLinearMessageAndSend(String strLinearSpeed) {
        mHeadMsg = "\"linear\":{\"x\":" + strLinearSpeed + ",\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
        sendHeadMsgToTopic(mHeadMsg);
    }

    private void changeHeadAngularMessageAndSend(String strAngularSpeed) {
        mHeadMsg = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + strAngularSpeed + "}";
        sendHeadMsgToTopic(mHeadMsg);
    }
}
