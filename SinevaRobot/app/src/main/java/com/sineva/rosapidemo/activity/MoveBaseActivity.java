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


public class MoveBaseActivity extends BaseActivity {

    private final static Double MAX_MOVEBASE_LINEARSPEED = 0.3;
    private final static Double MAX_MOVEBASE_ANGULARSPEED = 0.3;

    private SeekBar linear;
    private SeekBar angular;

    private Timer mTimer;

    private Double mMoveBase_Def_linearSpeed = 0.15;
    private Double mMoveBase_Def_angularSpeed = 0.15;

    private ImageButton up;
    private ImageButton down;
    private ImageButton left;
    private ImageButton right;

    private String mMoveBaseMsg;

    public static final int DELAY = 0;
    public static final int PERIOD = 500;


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
        tvLinear.setText("Forward Speed");
        TextView tvAngular = (TextView) findViewById(R.id.tv_angular);
        tvAngular.setText("Rotate Speed");

        setListener();
    }

    private void setListener() {

        linear.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setMoveBaseCabrageSpeed((progress * MAX_MOVEBASE_LINEARSPEED) / 100);
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
                setMoveBaseRollSpeed(progress * MAX_MOVEBASE_ANGULARSPEED / 100);
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
                        robotForward();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopMoveBaseMovemet();
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
                        robotBackward();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopMoveBaseMovemet();
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
                        robotTurnLeft();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopMoveBaseMovemet();
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
                        robotTurnRight();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopMoveBaseMovemet();
                        break;
                }
                return false;
            }
        });
    }

    //control the robot's wheel
    public void robotForward() {
        stopMoveBaseMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeMoveBaseLinearMessageAndSend(String.valueOf(mMoveBase_Def_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void robotBackward() {
        stopMoveBaseMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeMoveBaseLinearMessageAndSend("-" + String.valueOf(mMoveBase_Def_linearSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void robotTurnLeft() {
        stopMoveBaseMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeMoveBaseAngularMessageAndSend(String.valueOf(mMoveBase_Def_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void robotTurnRight() {
        stopMoveBaseMovemet();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeMoveBaseAngularMessageAndSend("-" + String.valueOf(mMoveBase_Def_angularSpeed));
            }
        }, DELAY, PERIOD);
    }

    public void stopMoveBaseMovemet() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mMoveBaseMsg = Constants.STOP_MESSAGE;
        sendMoveBaseMsgToTopic(mMoveBaseMsg);
    }

    public void setMoveBaseRollSpeed(Double rollSpeed) {
        mMoveBase_Def_angularSpeed = rollSpeed;
    }

    public void setMoveBaseCabrageSpeed(Double cabrageSpeed) {
        mMoveBase_Def_linearSpeed = cabrageSpeed;
    }

    private void sendMoveBaseMsgToTopic(String msg) {
        if (mRosApiClientInstance != null) {
            mRosApiClientInstance.publishTopic("/joy_teleop/cmd_vel_base",msg);
            //mRosApiClientInstance.send("{\"op\":\"publish\",\"topic\":\"/joy_teleop/cmd_vel_base\",\"msg\":{" + msg + "}}");
        }
    }

    private void changeMoveBaseLinearMessageAndSend(String strLinearSpeed) {
        mMoveBaseMsg = "\"linear\":{\"x\":" + strLinearSpeed + ",\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}";
        sendMoveBaseMsgToTopic(mMoveBaseMsg);
    }

    private void changeMoveBaseAngularMessageAndSend(String strAngularSpeed) {
        mMoveBaseMsg = "\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":" + strAngularSpeed + "}";
        sendMoveBaseMsgToTopic(mMoveBaseMsg);
    }
}
