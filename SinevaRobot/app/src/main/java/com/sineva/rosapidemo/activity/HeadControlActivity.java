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

import static com.sineva.rosapidemo.R.id.sb_horizontal_speed;
import static com.sineva.rosapidemo.R.id.sb_vertical_speed;


public class HeadControlActivity extends BaseActivity {

    private final static Double MAX_HEAD_LINEARSPEED = 5.0;
    private final static Double MAX_HEAD_ANGULARSPEED = 30.0;

    @BindView(R.id.btn_forward)
    ImageButton btnForward;
    @BindView(R.id.btn_backward)
    ImageButton btnBackward;
    @BindView(R.id.btn_left)
    ImageButton btnLeft;
    @BindView(R.id.btn_right)
    ImageButton btnRight;
    @BindView(R.id.btn_stop)
    ImageButton btnStop;
    @BindView(R.id.activity_nodes)
    LinearLayout activityNodes;

    @BindView(sb_vertical_speed)
    SeekBar sbVerticalSpeed;
    @BindView(sb_horizontal_speed)
    SeekBar sbHorizontalSpeed;

    private Timer mTimer;

    public static final int DELAY = 0;
    public static final int PERIOD = 500;

    private Double mHead_Def_linearSpeed = 2.5;
    private Double mHead_Def_angularSpeed = 15.0;

    private String mHeadMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_control_head;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        setListener();
    }

    private void setListener() {
        sbVerticalSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        sbHorizontalSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        btnForward.setOnTouchListener(new View.OnTouchListener() {
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

        btnBackward.setOnTouchListener(new View.OnTouchListener() {
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

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
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

        btnRight.setOnTouchListener(new View.OnTouchListener() {
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
            mRosApiClientInstance.publishTopic("/joy_teleop/cmd_vel_head", msg);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
