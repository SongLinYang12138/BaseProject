package com.bondex.ysl.battledore.addhawb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.bondex.ysl.battledore.R;
import com.bondex.ysl.battledore.plan.PlanAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * date: 2019/6/6
 * Author: ysl
 * description:
 */
public class AddHawbAdapter extends RecyclerView.Adapter<AddHawbAdapter.ViewHolder> {

    private List<String> list = null;
    private boolean isSelelct;
    private int groupPosition;


    private LinkedHashMap<Integer, Boolean> selectMap = new LinkedHashMap<>();

    public AddHawbAdapter(List<String> list) {
        if (list == null) list = new ArrayList<>();


        this.list = list;
    }

    public void updateList(List<String> list) {

        if (list == null) list = new ArrayList<>();

        this.list = list;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int position) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_hawb_layout, parent, false);

        ViewHolder holder = new ViewHolder(view);
        holder.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ck.setChecked(selectMap.get(position) == null ? false : selectMap.get(position));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox ck;
        private TextView tvHawb;


        public ViewHolder(@NonNull View view) {
            super(view);

            ck = view.findViewById(R.id.item_add_hawb_ck_hawb);
            tvHawb = view.findViewById(R.id.item_add_hawb_hawb);
        }
    }

}
