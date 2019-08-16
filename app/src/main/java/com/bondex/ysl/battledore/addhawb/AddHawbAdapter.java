package com.bondex.ysl.battledore.addhawb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
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
import com.bondex.ysl.battledore.util.Constant;
import com.bondex.ysl.databaselibrary.hawb.HAWBBean;
import com.bondex.ysl.liblibrary.ui.IconText;
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * date: 2019/6/6
 * Author: ysl
 * description:
 */
public class AddHawbAdapter extends RecyclerView.Adapter<AddHawbAdapter.ViewHolder> {

    private ArrayList<HAWBBean> list = null;
    private boolean selectAll;
    private int groupPosition;
    private AddHawbItemListener listener;

    private ArrayMap<Integer, Boolean> selected = new ArrayMap<>();

    public AddHawbAdapter(ArrayList<HAWBBean> list, AddHawbItemListener listener) {
        if (list == null) list = new ArrayList<>();

        this.listener = listener;
        this.list = list;
        selected.clear();

    }

    public void updateList(ArrayList<HAWBBean> list) {

        if (list == null) list = new ArrayList<>();

        this.list = list;

        selected.clear();
        notifyDataSetChanged();
    }

    public void setSelect(boolean isSelect) {
        selectAll = isSelect;

        if (!selectAll) {

            for (Map.Entry<Integer, Boolean> me : selected.entrySet()) {

                selected.put(me.getKey(), false);
            }

        }

        notifyDataSetChanged();
    }

    public ArrayMap<Integer, Boolean>  getSelectList(){

        return selected;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int position) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_hawb_layout, parent, false);

        ViewHolder holder = new ViewHolder(view);
        holder.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int postion = (int) buttonView.getTag();
                selected.put(postion, isChecked);
                Log.i(Constant.TAG,selected.toString());
            }
        });

        holder.itAdd.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void click(View v) {

                int position = (int) v.getTag();

                if (listener != null) listener.onListItemClick(position);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.ck.setTag(position);

        if (selectAll) {
            holder.ck.setChecked(true);
        } else if (selected.containsKey(position)) {
            holder.ck.setChecked(selected.get(position));
        } else holder.ck.setChecked(false);

        HAWBBean bean = list.get(position);
        holder.tvHawb.setText(bean.getHawb());
        holder.tvMHwab.setText(bean.getmBillCode());
        holder.tvQty.setText(bean.getQty() + "件" + bean.getWeight() + "kg" + bean.getVolume() + "m³");
        holder.tvFlight.setText(bean.getFlight() + "(" + bean.getDetination() + ")");
        holder.tvDate.setText(bean.getDate());

        holder.itAdd.setTag(position);

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
        private TextView tvHawb, tvMHwab, tvFlight, tvQty, tvDate;
        private IconText itAdd;


        public ViewHolder(@NonNull View view) {
            super(view);

            ck = view.findViewById(R.id.item_add_hawb_ck_hawb);
            tvHawb = view.findViewById(R.id.item_add_hawb_hawb);
            tvMHwab = view.findViewById(R.id.item_add_hawb_mhawb);
            tvFlight = view.findViewById(R.id.item_add_hawb_flight);
            tvQty = view.findViewById(R.id.item_add_hawb_qty);
            tvDate = view.findViewById(R.id.item_add_hawb_date);
            itAdd = view.findViewById(R.id.item_add_hawb_delete);
        }
    }

}
