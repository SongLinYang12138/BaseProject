package com.bondex.ysl.battledore.addhawb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.bondex.ysl.battledore.R;
import com.bondex.ysl.databaselibrary.hawb.HAWBBean;
import com.bondex.ysl.liblibrary.ui.IconText;
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * date: 2019/6/6
 * Author: ysl
 * description:
 */
public class AddHawbPlanAdapter extends RecyclerView.Adapter<AddHawbPlanAdapter.ViewHolder> {

    private ArrayList<HAWBBean> mylist = null;

    private AddHawbItemListener listener;


    private LinkedHashMap<Integer, Boolean> selectMap = new LinkedHashMap<>();

    public AddHawbPlanAdapter(ArrayList<HAWBBean> list, AddHawbItemListener listener) {
        if (list == null) list = new ArrayList<>();
        this.mylist = list;
        this.listener = listener;
    }

    public void updateList(ArrayList<HAWBBean> list) {

        if (list == null) list = new ArrayList<>();

        this.mylist = list;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int position) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_hawb_plan_layout, parent, false);

        ViewHolder holder = new ViewHolder(view);

        holder.itDelete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void click(View v) {

                int position = (int) v.getTag();

                listener.onPlanItemClick(position);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itDelete.setTag(position);

        HAWBBean bean = mylist.get(position);
        holder.tvHawb.setText(bean.getHawb());
        holder.tvMHwab.setText(bean.getmBillCode());
        holder.tvQty.setText(bean.getQty() + "件" + bean.getWeight() + "kg" + bean.getVolume() + "m³");
        holder.tvFlight.setText(bean.getFlight() + "(" + bean.getDetination() + ")");
        holder.tvDate.setText(bean.getDate());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {


        private TextView tvHawb, tvMHwab, tvFlight, tvQty, tvDate;

        private IconText itDelete;

        public ViewHolder(@NonNull View view) {
            super(view);


            tvHawb = view.findViewById(R.id.item_add_hawb_hawb);
            tvMHwab = view.findViewById(R.id.item_add_hawb_mhawb);
            tvFlight = view.findViewById(R.id.item_add_hawb_flight);
            tvQty = view.findViewById(R.id.item_add_hawb_qty);
            tvDate = view.findViewById(R.id.item_add_hawb_date);
            itDelete = view.findViewById(R.id.item_add_hawb_delete);
        }
    }

}
