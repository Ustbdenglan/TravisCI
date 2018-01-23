package com.sineva.rosapidemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.SetConstants;
import com.sineva.rosapidemo.adapter.WifiAdapter;
import com.sineva.rosapidemo.utilcode.util.SPUtils;
import com.sineva.rosapidemo.view.SwitchButton;
import com.sineva.rosapidemo.view.ToolbarControl;

import java.util.ArrayList;

import butterknife.BindView;

public class WifiSetActivity extends BaseActivity {

    @BindView(R.id.toorbar)
    ToolbarControl toorbar;
    @BindView(R.id.sb_devmode)
    SwitchButton sbDevmode;
    @BindView(R.id.lv_wifi_list)
    ListView lvWifiList;

    private ArrayList<String> wifiList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wifi_set;
    }

    @Override
    protected void initView() {
        sbDevmode.setChecked(SPUtils.getInstance().getBoolean(SetConstants.IS_WIFI_OPEN));
        sbDevmode.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SPUtils.getInstance().put(SetConstants.IS_WIFI_OPEN,isChecked);
                if (isChecked){
                    lvWifiList.setVisibility(View.VISIBLE);
                    initData();

                }else{
                    lvWifiList.setVisibility(View.GONE);
                    wifiList.clear();
                }
            }
        });

        lvWifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showInputDialog();

            }
        });
    }

    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(WifiSetActivity.this);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(WifiSetActivity.this);
        inputDialog.setTitle("请输入密码").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(WifiSetActivity.this,
                                "密码错误".toString(),
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    protected void initData() {
        if(sbDevmode.isChecked()){
            wifiList.add("aaaaa");
            wifiList.add("bbbbb");
            wifiList.add("ccccc");
            wifiList.add("ddddd");
            lvWifiList.setAdapter(new WifiAdapter(this, wifiList));
        }

    }

}
