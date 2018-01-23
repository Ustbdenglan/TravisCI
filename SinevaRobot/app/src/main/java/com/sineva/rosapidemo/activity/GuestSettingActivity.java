package com.sineva.rosapidemo.activity;

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

public class GuestSettingActivity extends BaseActivity {

    @BindView(R.id.toorbar)
    ToolbarControl toorbar;
    @BindView(R.id.lv_guest_list)
    ListView lvGuestList;
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
    @BindView(R.id.et_begin_time)
    EditText etBeginTime;
    @BindView(R.id.et_cycle_times)
    EditText etCycleTimes;
    @BindView(R.id.et_trigger_word)
    EditText etTriggerWord;
    @BindView(R.id.btn_move_photo)
    Button btnMovePhoto;
    @BindView(R.id.btn_move_music)
    Button btnMoveMusic;
    @BindView(R.id.btn_move_video)
    Button btnMoveVideo;
    @BindView(R.id.btn_move_play)
    Button btnMovePlay;
    @BindView(R.id.sp_select_map)
    EditText spSelectMap;
    @BindView(R.id.btn_preview)
    Button btnPreview;
    @BindView(R.id.et_target_word)
    EditText etTargetWord;
    @BindView(R.id.btn_point_play)
    Button btnPointPlay;
    @BindView(R.id.img_question)
    ImageView imgQuestion;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.lv_guest_point_list)
    ListView lvGuestPointList;
    @BindView(R.id.btn_cancle)
    Button btnCancle;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guest_setting;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_point_play, R.id.img_question, R.id.img_add, R.id.btn_cancle, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_point_play:
                break;
            case R.id.img_question:
                PromptDialog.create(getSupportFragmentManager())
                        .setTitleText("文字说明")
                        .setContentText("可通过拖曳来调整顺序")
                        .show();
                break;
            case R.id.img_add:
                break;
            case R.id.btn_cancle:
                break;
            case R.id.btn_confirm:
                break;
        }
    }
}
