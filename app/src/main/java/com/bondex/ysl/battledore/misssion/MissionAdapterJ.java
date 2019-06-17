package com.bondex.ysl.battledore.misssion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bondex.ysl.battledore.R;
import com.bondex.ysl.battledore.ui.FillListView;

import java.util.ArrayList;
import java.util.List;

/**
 * date: 2019/6/6
 * Author: ysl
 * description:
 */
public class MissionAdapterJ extends BaseAdapter {

    private List<MissionBean> list = new ArrayList<>();

    private Context context;


    public MissionAdapterJ(List<MissionBean> list) {
        this.list = list;


    }


    public void setSelectAll(boolean isSelect) {

        for (int i = 0; i < list.size(); ++i) {

            list.get(i).setSelected(isSelect);
        }

        notifyDataSetInvalidated();
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
    public View getView(final int position, View view, final ViewGroup parent) {


        if (view == null) {

            holder = new ViewHolder();
            context = parent.getContext();

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_mission_layout, parent, false);

            holder.tvMain = view.findViewById(R.id.item_mission_main);
            holder.ckMain = view.findViewById(R.id.item_mission_ck_main);
            holder.recyclerview = view.findViewById(R.id.item_mission_recyclerview);

//            holder.adapterJ = new MissionChildAdapterj(list.get(position).getHawbs());
//            holder.adapterJ.setGroupPosition(position);
//            holder.adapterJ.setGroupPosition(position);
//            holder.recyclerview.setAdapter(holder.adapterJ);
//
//
//            holder.ckMain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    list.get(position).setSelected(isChecked);
//
//                    View childeView = parent.getChildAt(position);
//
//                    if (childeView != null) {
//
//                        ListView childItem = childeView.findViewById(R.id.item_mission_recyclerview);
//                        MissionChildAdapterj adapterj = (MissionChildAdapterj) childItem.getAdapter();
//                        adapterj.setSelelct(isChecked);
//                    }
//
//                }
//            });


            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }

        MissionBean bean = list.get(position);
        holder.tvMain.setTag(position);
        holder.ckMain.setChecked(bean.isSelected());


//        if (holder.recyclerview.getTag() == null) {

        final MissionChildAdapterj adapterj = new MissionChildAdapterj(list.get(position).getHawbs());
        adapterj.setSelelct(bean.isSelected());
        adapterj.setGroupPosition(position);

        holder.recyclerview.setAdapter(adapterj);


        holder.ckMain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                adapterj.setSelelct(isChecked);

            }
        });


//        } else {
//
//            MissionChildAdapterj adapterj = (MissionChildAdapterj) holder.recyclerview.getTag();
//            adapterj.setSelelct(bean.isSelected());
//            holder.adapterJ = adapterj;
//
//        }


        return view;
    }

    private class ViewHolder {

        CheckBox ckMain;
        private TextView tvMain;
        FillListView recyclerview;
        private MissionChildAdapterj adapterJ;


    }
}
