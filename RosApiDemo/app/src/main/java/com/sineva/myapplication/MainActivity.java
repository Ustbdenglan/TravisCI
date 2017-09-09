package com.sineva.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String mIp;
    private String mPort;
    private EditText mEtIp;
    private EditText mEtPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtIp = findViewById(R.id.et_ip);
        mEtPort = findViewById(R.id.et_port);
    }

    private void showTip(final String tip) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, tip, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onConnectClick(View view) {
        mIp = mEtIp.getText().toString();
        mPort = mEtPort.getText().toString();
        Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
        intent.putExtra("ip", mIp);
        intent.putExtra("port", mPort);
        startActivity(intent);
    }
}
