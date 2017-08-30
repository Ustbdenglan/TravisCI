package com.sineva.rosclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sineva.rosclient.R;


public class ChooseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Button chassis = (Button) findViewById(R.id.button6);
        chassis.setOnClickListener(this);
        Button head = (Button) findViewById(R.id.button7);
        head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button6:
                startActivity(new Intent(ChooseActivity.this, ChassisActivity.class));
                break;
            case R.id.button7:
                startActivity(new Intent(ChooseActivity.this, HeadActivity.class));
                break;
            default:
                break;
        }
    }
}
