package com.sineva.rosapidemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sineva.rosapidemo.R;

import java.util.ArrayList;

/**
 * Created by sin on 2018/1/23.
 */

public class WifiAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> datas;
    public WifiAdapter(Context context, ArrayList<String> wifiList) {
        this.mContext=context;
        this.datas=wifiList;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_wifi_info, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_wifi_name.setText(datas.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tv_wifi_name;
        ImageView icon_wifi_signal;
        ImageView icon_wifi_lock;
        ViewHolder(View view) {
            tv_wifi_name = (TextView) view.findViewById(R.id.tv_wifi_name);
            icon_wifi_signal = (ImageView) view.findViewById(R.id.icon_wifi_signal);
            icon_wifi_lock = (ImageView) view.findViewById(R.id.icon_wifi_lock);
        }
    }
}