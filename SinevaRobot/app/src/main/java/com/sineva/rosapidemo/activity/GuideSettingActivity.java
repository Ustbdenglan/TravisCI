package com.sineva.rosapidemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.view.PromptDialog;
import com.sineva.rosapidemo.view.ToolbarControl;

import butterknife.BindView;
import butterknife.OnClick;

public class GuideSettingActivity extends BaseActivity {

    @BindView(R.id.toorbar)
    ToolbarControl toorbar;
    @BindView(R.id.lv_guide_list)
    ListView lvGuideList;
    @BindView(R.id.btn_add_guide)
    ImageView btnAddGuide;
    @BindView(R.id.btn_del_guide)
    ImageView btnDelGuide;
    @BindView(R.id.et_scene)
    EditText etScene;
    @BindView(R.id.et_space_time)
    EditText etSpaceTime;
    @BindView(R.id.cb_default_scene)
    CheckBox cbDefaultScene;
    @BindView(R.id.cb_can_charge)
    CheckBox cbCanCharge;
    @BindView(R.id.cb_can_aq)
    CheckBox cbCanAq;
    @BindView(R.id.et_begin_time)
    EditText etBeginTime;
    @BindView(R.id.et_cycle_times)
    EditText etCycleTimes;
    @BindView(R.id.et_trigger_word)
    EditText etTriggerWord;
    @BindView(R.id.sp_select_map)
    EditText spSelectMap;
    @BindView(R.id.btn_preview)
    Button btnPreview;
    @BindView(R.id.img_question)
    ImageView imgQuestion;
    @BindView(R.id.img_add_explanation)
    ImageView imgAddExplanation;
    @BindView(R.id.lv_guide_detail)
    ListView lvGuideDetail;
    @BindView(R.id.btn_cancle)
    Button btnCancle;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_setting;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_add_guide, R.id.btn_del_guide,R.id.img_question, R.id.img_add_explanation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_guide:
                break;
            case R.id.btn_del_guide:
                break;
            case R.id.img_question:
                PromptDialog.create(getSupportFragmentManager())
                        .setTitleText("文字说明")
                        .setContentText("可通过拖曳来调整顺序")
                        .show();
                break;
            case R.id.img_add_explanation:
                startActivity(new Intent(GuideSettingActivity.this,ExplanationActivity.class));
                break;
        }
    }
}
