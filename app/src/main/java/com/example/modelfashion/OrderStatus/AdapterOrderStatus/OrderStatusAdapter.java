package com.example.modelfashion.OrderStatus.AdapterOrderStatus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.modelfashion.History.MHistory.ModelHistory;
import com.example.modelfashion.R;

import java.util.List;

public class OrderStatusAdapter extends BaseAdapter {
    Context context;
    List<ModelHistory> listModel;


    public OrderStatusAdapter(Context context, List<ModelHistory> listModel) {
        this.context = context;
        this.listModel = listModel;
    }

    @Override
    public int getCount() {
        return listModel.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_orderstatus,viewGroup,false);
        TextView item_orderstatus_ma = view.findViewById(R.id.item_orderstatus_ma);
        TextView item_orderstatus_time = view.findViewById(R.id.item_orderstatus_time);
        TextView item_orderstatus_status = view.findViewById(R.id.item_orderstatus_status);
        item_orderstatus_ma.setText(listModel.get(i).getmIDHistory());
        item_orderstatus_time.setText(listModel.get(i).getmTimeOrder());
        item_orderstatus_status.setText(listModel.get(i).getmStatus());
        if(listModel.get(i).getmStatus().contains("Đang Chờ")){
            item_orderstatus_status.setTextColor(Color.parseColor("#FF0000"));
        }else if(listModel.get(i).getmStatus().contains("Đang Giao")) {
            item_orderstatus_status.setTextColor(Color.parseColor("#008E06"));
        }
        return view;

    }
}
