package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.sineva.rosapidemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ChargeImgSetActivity extends BaseActivity {

    private final String VIEWID = "ViewId";
    @BindView(R.id.btn_chargeing)
    Button btnChargeing;
    @BindView(R.id.btn_charged)
    Button btnCharged;
    @BindView(R.id.btn_charge_faliure)
    Button btnChargeFaliure;

    private final int RESULT=100;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_img_set;
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.btn_chargeing, R.id.btn_charged, R.id.btn_charge_faliure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_chargeing:
                chooseBgImg(view.getId());
                break;
            case R.id.btn_charged:
                chooseBgImg(view.getId());
                break;
            case R.id.btn_charge_faliure:
                chooseBgImg(view.getId());
                break;
        }
    }

    /**
     * 打开相册选择图片
     * @param id
     */
    private void chooseBgImg(int id) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(VIEWID,id);
        startActivityForResult(intent, RESULT);
    }
}
