package com.iflytek.aiui.uartkit.ctrdemo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iflytek.aiui.uartkit.UARTAgent;
import com.iflytek.aiui.uartkit.constant.UARTConstant;
import com.iflytek.aiui.uartkit.entity.AIUIPacket;
import com.iflytek.aiui.uartkit.entity.CustomPacket;
import com.iflytek.aiui.uartkit.entity.MsgPacket;
import com.iflytek.aiui.uartkit.entity.WIFIConfPacket;
import com.iflytek.aiui.uartkit.listener.EventListener;
import com.iflytek.aiui.uartkit.listener.UARTEvent;
import com.iflytek.aiui.uartkit.util.PacketBuilder;

import java.util.Arrays;

import static android.content.ContentValues.TAG;

/**
 * Created by setup on 2017/11/13.
 */

public class AIUIMeathes {
    public UARTAgent mAgent;
    private TextListener mTextListener;
    private Gson gs =new GsonBuilder().serializeNulls().create();
    public AIUIMeathes() {
        mAgent = UARTAgent.createAgent("/dev/ttyS3", 115200, new EventListener() {

            @Override
            public void onEvent(UARTEvent event) {
                switch (event.eventType) {
                    case UARTConstant.EVENT_INIT_SUCCESS:
                        Log.d(TAG, "Init UART Success");
                        break;
                    case UARTConstant.EVENT_INIT_FAILED:
                        Log.d(TAG, "Init UART Failed");
                        break;
                    case UARTConstant.EVENT_MSG:
                        MsgPacket recvPacket = (MsgPacket) event.data;
                        processPacket(recvPacket);
                        break;
                    case UARTConstant.EVENT_SEND_FAILED:
                        MsgPacket sendPacket = (MsgPacket) event.data;
                        mAgent.sendMessage(sendPacket);
                    default:
                        break;
                }
            }
        });

    }

    public void setWIFI(final String name, final String pwd) {
        mAgent.sendMessage(PacketBuilder.obtainWIFIConfPacket(WIFIConfPacket.WIFIStatus.CONNECTED, WIFIConfPacket.EncryptMethod.WPA, name, pwd));
    }

    public void processPacket(MsgPacket packet) {
        switch (packet.getMsgType()) {
            case MsgPacket.AIUI_PACKET_TYPE:
                String str = new String(((AIUIPacket) packet).content);
                if (str!=null) {
                    String strs = getJson(str);
                    String answer =getAnswer(str);
                    if (strs != null) {
                        if(mTextListener!=null){
                            mTextListener.receivedText(strs,answer);
                        }
                    }
                }
                Log.e("ss", ((AIUIPacket) packet).content);
                break;
            case MsgPacket.HANDSHAKE_REQ_TYPE:
                Log.e(TAG, "HANDSHAKE_REQ_TYPE" + ((AIUIPacket) packet).content);
                break;
            case MsgPacket.WIFI_CONF_TYPE:
                Log.d(TAG, "wifi状态" + ((AIUIPacket) packet).content);
                break;
            case MsgPacket.AIUI_CONF_TYPE:
                Log.d(TAG, "AIUI_CONF_TYPE" + ((AIUIPacket) packet).content);
                break;
            case MsgPacket.CTR_PACKET_TYPE:
                Log.d(TAG, "CTR_PACKET_TYPE " + ((AIUIPacket) packet).content);
                break;
            case MsgPacket.CUSTOM_PACKET_TYPE:
                Log.d(TAG, "aiui custom data " + Arrays.toString(((CustomPacket) packet).customData));
            default:
                break;
        }

    }

    private String getAnswer(String str) {
        Aiui_Result result = gs.fromJson(str, Aiui_Result.class);
        String answer ="";
        if(result.getContent()!=null){
            if(result.getContent().getResult()!=null){
                if (result.getContent().getResult().getIntent()!=null){
                    if (result.getContent().getResult().getIntent().getAnswer()!=null){
                        answer= result.getContent().getResult().getIntent().getAnswer().getText();
                    }
                }
            }
        }
        return answer;
    }
    public String getJson(String str) {
        Aiui_Result result = gs.fromJson(str, Aiui_Result.class);
        String info="";
        if(result.getContent()!=null){
            if(result.getContent().getResult()!=null){
                if (result.getContent().getResult().getIntent()!=null){
                    info= result.getContent().getResult().getIntent().getText();
                }
            }
        }
        return  info;
    }



    public void setList(TextListener listener) {
        if (listener != null) {
            this.mTextListener = listener;
        }
    }

    public interface TextListener {
        void receivedText(String result, String answer);
    }

}
