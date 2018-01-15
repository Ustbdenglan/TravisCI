package com.sineva.rosapidemo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ff on 17-12-20.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AndroidWifiUtils {


    public static void startNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest networkRequest = new NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED).addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build();
        cm.requestNetwork(networkRequest, networkCallback);
    }

    private static ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(final Network network) {

            ConnectivityManager.setProcessDefaultNetwork(network);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpURLConnection connection = (HttpURLConnection) network.openConnection(new URL("https://www.baidu.com"));
                        connection.connect();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String s = bufferedReader.readLine();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        @Override
        public void onLost(Network network) {
        }


        @Override
        public void onUnavailable() {
        }

        @Override
        public void onLosing(Network network, int maxMsToLive) {
        }


    };

}
