package com.bondex.ysl.battledore.misssion;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.bondex.ysl.battledore.R;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * date: 2019/6/6
 * Author: ysl
 * description:
 */
public class MissionChildAdapterj extends BaseAdapter {

    private List<String> list = null;
    private boolean isSelelct;
    private int groupPosition;

    private LinkedHashMap<Integer, Boolean> selectMap = new LinkedHashMap<>();

    public MissionChildAdapterj(List<String> list) {
        if (list == null) list = new ArrayList<>();


        this.list = list;
    }

    public void updateList(List<String> list) {

        if (list == null) list = new ArrayList<>();

        this.list = list;

        notifyDataSetChanged();
    }

    public void setSelelct(boolean isSelelc) {


        for (int i = 0; i < list.size(); ++i) {
            selectMap.put(i, isSelelc);
        }

        notifyDataSetChanged();
    }

    public void setGroupPosition(int groupPosition) {

        this.groupPosition = groupPosition;
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

    private ViewHolder holder;

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {


            holder = new ViewHolder();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mission_list_item_layout, parent, false);

            holder.ck = view.findViewById(R.id.item_mission_ck_hawb);
            holder.tvHawb = view.findViewById(R.id.item_mission_hawb);


            holder.ck.setChecked(selectMap.get(position) == null ? false : selectMap.get(position));


            holder.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Log.i("position ", " group " + groupPosition + "  child  " + position + " map " + selectMap.get(position));

                    selectMap.put(position,false);
                }
            });

            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }


        holder.ck.setChecked(selectMap.get(position) == null ? false : selectMap.get(position));

//        holder.tvHawb.setText(list.get(position));

        return view;
    }

    private class ViewHolder {

        private CheckBox ck;
        private TextView tvHawb;


    }

}
