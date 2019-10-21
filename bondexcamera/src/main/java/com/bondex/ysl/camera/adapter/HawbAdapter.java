package com.bondex.ysl.camera.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bondex.ysl.camera.R;
import com.bondex.ysl.databaselibrary.hawb.HAWBBean;

import java.util.ArrayList;

/**
 * date: 2019/8/6
 * Author: ysl
 * description:
 */
public class HawbAdapter extends BaseAdapter {

    private ArrayList<HAWBBean> list;
    private Context context;
    private ArrayMap<Integer, Boolean> selected = new ArrayMap<>();
    private ColorStateList normal, select;
    private int selectPosition = -1;


    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public ArrayList<HAWBBean> getList() {
        return list;
    }

    public ArrayMap<Integer, Boolean> getSelected() {
        return selected;
    }


    public void setSelected(ArrayMap<Integer, Boolean> selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    public HawbAdapter(ArrayList<HAWBBean> list, Context context) {
        this.list = list;
        this.context = context;
        if (context == null) return;
        normal = context.getResources().getColorStateList(R.color.white_back);
        select = context.getResources().getColorStateList(R.color.rect_red);

        for (int i = 0; i < list.size(); ++i) {

            selected.put(i, false);
        }


    }

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

    private class ViewHolder {

        private TextView tv;
        private AppCompatCheckBox ck;


    }

    private ViewHolder holder;

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {

            view = LayoutInflater.from(context).inflate(R.layout.item_hawbs_layout, parent, false);
            holder = new ViewHolder();

            holder.tv = view.findViewById(R.id.item);
            holder.ck = view.findViewById(R.id.ck);


            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = (int) v.getTag();

                    selectPosition = position;
                    notifyDataSetChanged();
                }
            });


            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();

        }

        holder.tv.setTag(position);

        if (selectPosition >= 0 && selectPosition == position) {

            holder.tv.setTextColor(select);

        } else
            holder.tv.setTextColor(normal);


        holder.ck.setChecked(selected.get(position));

        holder.tv.setText(list.get(position).getHawb());
        return view;
    }
}
