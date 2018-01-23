package com.sineva.rosapidemo.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sineva.rosapidemo.R;
import com.sineva.rosapidemo.entity.LedColorBean;

import java.util.List;

/**
 * Created by sin on 2018/1/22.
 */

public class ColorSelectPopupWindow<T> extends PopupWindow {
    private final String TAG="ColorSelectPopupWindow";
    private Context mContext;

    private View view;
    private ListView lv;
    private List<LedColorBean> list;
    private ColorSelectPopupWindow.OnItemSelectLintener mOnItemSelectLintener;

    public ColorSelectPopupWindow(final Context context, final View mView, final List<LedColorBean> list, final OnItemSelectLintener onItemSelectLintener) {
        this.mContext=context;
        this.list=list;
        this.mOnItemSelectLintener=onItemSelectLintener;
        view = LayoutInflater.from(mContext).inflate(R.layout.popup_select_color_dialog, null);
        setContentView(view);
        lv= view.findViewById(R.id.lv_color_list);
//        初始化 popupW
//        设置高度为 自适应
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//       将view添加到  popup里面

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        Log.e(TAG, "ColorSelectPopupWindow: 333");
        // 设置外部可点击
        setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = ColorSelectPopupWindow.this.view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });



        lv.setAdapter(new MySpinnerArrayAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectLintener.onItemSelecter(list.get(position),mView);
            }
        });


    }
    // 适配器
    class MySpinnerArrayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
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
                convertView = inflater.inflate(R.layout.item_color_spinner, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv_color_name.setText(list.get(position).getColorName());
            viewHolder.img_color_bg.setBackgroundColor(list.get(position).getColorNumber());
            return convertView;
        }

        class ViewHolder {
            TextView tv_color_name;
            ImageView img_color_bg;
            ViewHolder(View view) {
                tv_color_name = (TextView) view.findViewById(R.id.tv_color_name);
                img_color_bg = (ImageView) view.findViewById(R.id.img_color_bg);
            }
        }
    }



    public interface OnItemSelectLintener{
        void onItemSelecter(LedColorBean ledColorBean,View view);
    }
    public void setOnItemSelectLintener(ColorSelectPopupWindow.OnItemSelectLintener onItemSelectLintener){
        this.mOnItemSelectLintener=onItemSelectLintener;
    }

}
