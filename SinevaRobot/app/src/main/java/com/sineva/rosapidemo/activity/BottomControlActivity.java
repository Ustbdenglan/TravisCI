package com.sineva.rosapidemo.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.roslibrary.ros.Constants;
import com.sineva.rosapidemo.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomControlActivity extends BaseActivity {

    private final static Double MAX_MOVEBASE_LINEARSPEED = 0.3;
    private final static Double MAX_MOVEBASE_ANGULARSPEED = 0.3;
    @BindView(R.id.btn_forward)
    ImageButton btnForward;
    @BindView(R.id.btn_backward)
    ImageButton btnBackward;
    @BindView(R.id.btn_left)
    ImageButton btnLeft;
    @BindView(R.id.btn_right)
    ImageButton btnRight;

    @BindView(R.id.sb_move_speed)
    SeekBar sbMoveSpeed;
    @BindView(R.id.sb_rotate_speed)
    SeekBar sbRotateSpeed;

    @BindView(R.id.btn_stop)
    ImageButton btnStop;
    @BindView(R.id.activity_nodes)
    LinearLayout activityNodes;


    private Timer mTimer;

    private Double mMoveBase_Def_linearSpeed = 0.15;
    private Double mMoveBase_Def_angularSpeed = 0.15;

    private String mMoveBaseMsg;

    public static final int DELAY = 0;
    public static final int PERIOD = 500;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_control_bottom;
    }

    @Override
    protected void initView() {
        setListener();
    }


    private void setListener() {

        sbMoveSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        sbRotateSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        btnForward.setOnTouchListener(new View.OnTouchListener() {
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

        btnBackward.setOnTouchListener(new View.OnTouchListener() {
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

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
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

        btnRight.setOnTouchListener(new View.OnTouchListener() {
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
            mRosApiClientInstance.publishTopic("/joy_teleop/cmd_vel_base", msg);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
