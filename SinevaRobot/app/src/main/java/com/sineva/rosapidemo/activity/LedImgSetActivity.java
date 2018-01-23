package com.sineva.rosapidemo.activity;

import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.entity.LedColorBean;
import com.sineva.rosapidemo.view.ColorSelectPopupWindow;
import com.sineva.rosapidemo.view.ToolbarControl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LedImgSetActivity extends BaseActivity {

    private static final String TAG = "LedImgSetActivity";
    @BindView(R.id.toorbar)
    ToolbarControl toorbar;
    @BindView(R.id.tv_chargeing_color)
    TextView tvChargeingColor;
    @BindView(R.id.img_chargeing_color)
    ImageView imgChargeingColor;
    @BindView(R.id.tv_full_charge_color)
    TextView tvFullChargeColor;
    @BindView(R.id.img_full_chargeg_color)
    ImageView imgFullChargegColor;
    @BindView(R.id.tv_no_charge_color)
    TextView tvNoChargeColor;
    @BindView(R.id.img_no_chargeg_color)
    ImageView imgNoChargegColor;
    @BindView(R.id.tv_regular_user_color)
    TextView tvRegularUserColor;
    @BindView(R.id.img_regular_user_color)
    ImageView imgRegularUserColor;

    @BindView(R.id.lin_chargeing_color)
    LinearLayout linChargeingColor;
    @BindView(R.id.lin_full_charge_color)
    LinearLayout linFullChargeColor;
    @BindView(R.id.lin_no_charge_color)
    LinearLayout linNoChargeColor;
    @BindView(R.id.lin_regular_user_color)
    LinearLayout linRegularUserColor;

    private ColorSelectPopupWindow mColorSelectPopupWindow;
    private List<LedColorBean> list=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_led_img_set;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        list.add(new LedColorBean("红色", Color.parseColor("#ff0000")));
        list.add(new LedColorBean("蓝色", Color.parseColor("#0000ff")));
        list.add(new LedColorBean("草莓色", Color.parseColor("#872657")));
        list.add(new LedColorBean("黄色", Color.parseColor("#ffff00")));
        list.add(new LedColorBean("青色", Color.parseColor("#00ffff")));
        list.add(new LedColorBean("绿色", Color.parseColor("#00ff00")));
        list.add(new LedColorBean("紫色", Color.parseColor("#a020f0")));
        list.add(new LedColorBean("棕红", Color.parseColor("#d96519")));
    }

    @OnClick({R.id.lin_chargeing_color, R.id.lin_full_charge_color, R.id.lin_no_charge_color, R.id.lin_regular_user_color})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_chargeing_color:
                openColorSelectPopWin(linChargeingColor);
                break;
            case R.id.lin_full_charge_color:
                openColorSelectPopWin(linFullChargeColor);
                break;
            case R.id.lin_no_charge_color:
                openColorSelectPopWin(linNoChargeColor);
                break;
            case R.id.lin_regular_user_color:
                openColorSelectPopWin(linRegularUserColor);
                break;
        }
    }

    /**
     *
     */
    private void openColorSelectPopWin(View viewLayout) {
        mColorSelectPopupWindow = new ColorSelectPopupWindow(this, viewLayout, list, new ColorSelectPopupWindow.OnItemSelectLintener() {
            @Override
            public void onItemSelecter(LedColorBean ledColorBean, View viewLayout) {
                LinearLayout linearLayout=(LinearLayout)viewLayout;
                TextView tv = (TextView) linearLayout.getChildAt(0);
                ImageView image = (ImageView) linearLayout.getChildAt(1);
                tv.setText(ledColorBean.getColorName());
                image.setBackgroundColor(ledColorBean.getColorNumber());
                mColorSelectPopupWindow.dismiss();
            }
        });

        setBackgroundAlpha(0.8f);
        mColorSelectPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                setBackgroundAlpha(1.0f);
            }
        });
        mColorSelectPopupWindow.setWidth(viewLayout.getWidth());
        mColorSelectPopupWindow.showAsDropDown(viewLayout);

    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
       /* WindowManager.LayoutParams lp = BaseSettingActivity.this.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);


        Activity mActivity = (Activity) mContext;*/
        Window window = LedImgSetActivity.this.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        //这里设置透明度
        windowParams.dimAmount = 0.5f;
        windowParams.alpha = bgAlpha;

        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        window.setAttributes(windowParams);
    }
}
