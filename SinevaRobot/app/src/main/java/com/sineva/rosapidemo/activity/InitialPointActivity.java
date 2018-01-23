package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.view.ToolbarControl;

import butterknife.BindView;
import butterknife.OnClick;

public class InitialPointActivity extends BaseActivity {

    @BindView(R.id.toorbar)
    ToolbarControl toorbar;
    @BindView(R.id.btn_set_initial_point)
    Button btnSetInitialPoint;
    @BindView(R.id.btn_cancle)
    Button btnCancle;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_initial_point;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_set_initial_point, R.id.btn_cancle, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_set_initial_point:
                Toast.makeText(InitialPointActivity.this,"设置初始点",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cancle:
                break;
            case R.id.btn_confirm:
                startActivity(new Intent(InitialPointActivity.this,InMotionActivity.class));
                break;
        }
    }
}
