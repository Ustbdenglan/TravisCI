package com.sineva.rosapidemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.roslibrary.ros.RosApiClient;
import com.sineva.rosapidemo.utils.AppManager;

import java.util.Timer;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sin on 2018/1/28.
 * 简单封装了Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    private final String ROSBRIDGE_DISCONNECTED = "com.sineva.rosbridge_disconnected";
    public RosApiClient mRosApiClientInstance;
    public Timer mTimer;
    public SQLiteDatabase mWritableDatabase;
    private RosbridgeReceiver mRosDisconnectReceiver;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        AppManager.instance.addActivity(this);
        setContentView(getLayoutId());
        unbinder= ButterKnife.bind(this);
        this.initView();

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();
        mTimer = new Timer();
     /*   SinevaSQLiteOpenHelper helper = new SinevaSQLiteOpenHelper(this, "RosData", null, 1);
        mWritableDatabase = helper.getWritableDatabase();*/

        IntentFilter filter = new IntentFilter();
        filter.addAction(ROSBRIDGE_DISCONNECTED);
        mRosDisconnectReceiver = new RosbridgeReceiver();
        this.registerReceiver(mRosDisconnectReceiver, filter);

        this.initData();

    }

    //获取布局
    protected abstract int getLayoutId();

    //初始化布局和监听
    protected abstract void initView();

    protected void initData(){};

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
        AppManager.instance.removeActivity(this);

        if (mTimer != null) {
            mTimer.cancel();
        }
        this.unregisterReceiver(mRosDisconnectReceiver);
    }

    class RosbridgeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Rosbridge disconnected, please reconnect!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
