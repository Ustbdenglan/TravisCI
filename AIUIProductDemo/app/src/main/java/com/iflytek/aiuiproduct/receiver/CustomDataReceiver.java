package com.iflytek.aiuiproduct.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 自定义串口数据监听
 * Created by sineva on 2018/1/23.
 */

public class CustomDataReceiver extends BroadcastReceiver {
    private static final String ACTION_SEND_CUSTOM_DATA = "com.iflytek.aiui.devboard.action.SEND_CUSTOM_DATA";
    private static final String CUSTOM_DATA_KEY = "custom_data";

    @Override
    public void onReceive(Context context, Intent intent) {
        byte[] custom_data_keys = intent.getByteArrayExtra("CUSTOM_DATA_KEY");
        Log.e("my", "下位机信息----: "+custom_data_keys.length );

      /*  byte[] customData = intent.getByteArrayExtra(CUSTOM_DATA_KEY);
        Log.d("CustomDataReceiver", "recv custom data " + Arrays.toString(customData));
        Intent respAck = new Intent(ACTION_SEND_CUSTOM_DATA);
        respAck.putExtra(CUSTOM_DATA_KEY, "马越");
        context.sendBroadcast(respAck);*/

    }
}
