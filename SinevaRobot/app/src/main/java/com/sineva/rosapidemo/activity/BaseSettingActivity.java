package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sineva.rosapidemo.R;

import org.zeromq.ZMQ;

/**
 * Created by Eligah on 2017/10/12.
 */

public class BaseSettingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_setting);


    }

    public void onHeadControlClick(View view) {
        startActivity(new Intent(BaseSettingActivity.this, HeadActivity.class));
    }

    public void onAboutUsClick(View view) throws InterruptedException {
        startActivity(new Intent(BaseSettingActivity.this, AboutUsActivity.class));
        //zmq demo code
        /*new Thread(networkTask).start();*/
    }

    //zmq demo code
    /*Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            ZMQ.Context context = ZMQ.context(1);
            ZMQ.Socket publisher = context.socket(ZMQ.PUB);
            boolean bind = publisher.bind("tcp://192.168.31.225:8001");
            Log.e(TAG, bind + "");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String a = "{\"tran_code\":3106,\"body\":\"\"cmd\":5,\"param1\":1,\"param2\":1\"}";
            boolean send = publisher.send(a.getBytes(), 1);
            Log.e(TAG, send + "");


            publisher.close();
            context.term();
        }
    };*/

}
