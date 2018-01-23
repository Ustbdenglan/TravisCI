package com.sineva.rosapidemo.activity;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.SetConstants;
import com.sineva.rosapidemo.utilcode.util.SPUtils;
import com.sineva.rosapidemo.view.SwitchButton;

import butterknife.BindView;

public class DevModeActivity extends BaseActivity {

    @BindView(R.id.sbtn_devmode)
    SwitchButton sbtnDevmode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_mode;
    }

    @Override
    protected void initView() {
        sbtnDevmode.setChecked(SPUtils.getInstance().getBoolean(SetConstants.IS_DEV_MODE));
        sbtnDevmode.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SPUtils.getInstance().put(SetConstants.IS_DEV_MODE,isChecked);
            }
        });
    }

}
