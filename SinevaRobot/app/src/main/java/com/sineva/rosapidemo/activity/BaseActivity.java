package com.sineva.rosapidemo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.roslibrary.ros.RosApiClient;
import com.sineva.rosapidemo.db.SinevaSQLiteOpenHelper;

import java.util.Timer;

/**
 * Created by Eligah on 2017/9/28.
 */

public class BaseActivity extends Activity {

    public RosApiClient mRosApiClientInstance;
    public Timer mTimer;
    public SQLiteDatabase mWritableDatabase;
    private RosbridgeReceiver mRosDisconnectReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mRosApiClientInstance = RosApiClient.getRosApiClientInstance();

        mTimer = new Timer();

        SinevaSQLiteOpenHelper helper = new SinevaSQLiteOpenHelper(this, "RosData", null, 1);
        mWritableDatabase = helper.getWritableDatabase();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sineva.rosbridge_disconnected");
        mRosDisconnectReceiver = new RosbridgeReceiver();
        this.registerReceiver(mRosDisconnectReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
