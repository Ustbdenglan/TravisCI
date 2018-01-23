package com.iflytek.aiui.uartkit.ctrdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.iflytek.aiui.uartkit.util.PacketBuilder;

import android_serialport_api.SerialPortUtil;

public class MainActivity extends Activity {
    private static final String TAG = "UART_Controller";
    private boolean wifi = false;
    private String name = null;
    private String pwd = null;
    final AIUIMeathes it = new AIUIMeathes();
    //串口工具
    SerialPortUtil serialPortUtil =new SerialPortUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = "xiaomi_sineva";
        pwd = "sineva2017";
        serialPortUtil.openSrialPort();

        it.setList(new AIUIMeathes.TextListener() {
            @Override
            public void receivedText(final String info, String answer) {
                if (info != "") {
                    Log.e("my", "info" + info);
//                    it.mAgent.sendMessage(PacketBuilder.obtainTTSStartPacket(info));
                }
                if (answer != "") {
                    Log.e("my", "info" + answer);
                    it.mAgent.sendMessage(PacketBuilder.obtainTTSStartPacket(answer));
                }
            }

        });
    }

    public void wifi(View view) {
        try {
            Thread.sleep(1000);
            it.mAgent.sendMessage(PacketBuilder.obtainTTSStartPacket("网络配置中请稍后"));
            if (wifi == false) {
                it.setWIFI(name, pwd);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(8000);
            wifi = it.mAgent.sendMessage(PacketBuilder.obtainWIFIStatusReqPacket());
            if (it.mAgent.sendMessage(PacketBuilder.obtainAIUICtrPacket(7, 0, 0, ""))) {
                it.mAgent.sendMessage(PacketBuilder.obtainTTSStartPacket("网络" + name + "连接成功"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void wakeup(View view) {
        it.mAgent.sendMessage(PacketBuilder.obtainAIUICtrPacket(7, 0, 0, ""));
    }

    public void sleep(View view) {

        it.mAgent.sendMessage(PacketBuilder.obtainAIUICtrPacket(8, 0, 0, ""));

    }


    public void stop(View view) {
        it.mAgent.sendMessage(PacketBuilder.obtainAIUICtrPacket(6, 0, 0, ""));
    }

    public void continues(View view) {
        it.mAgent.sendMessage(PacketBuilder.obtainAIUICtrPacket(5, 0, 0, ""));
    }

    public void request(View view) {
        it.mAgent.sendMessage(PacketBuilder.obtainTTSStartPacket("You are a good men!"));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        serialPortUtil.closeSerialPort();
    }
}
