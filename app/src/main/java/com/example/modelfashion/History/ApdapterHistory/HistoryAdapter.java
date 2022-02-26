package com.example.modelfashion.History.ApdapterHistory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.History.MHistory.ModelHistory;
import com.example.modelfashion.R;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    List<ModelHistory> listModel;
    Context context;

    public HistoryAdapter(Context context,List<ModelHistory> listModel) {
        this.listModel = listModel;
        this.context = context;

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
        view = LayoutInflater.from(context).inflate(R.layout.item_history,viewGroup,false);
        TextView item_history_ma = view.findViewById(R.id.item_history_ma);
        TextView item_history_time = view.findViewById(R.id.item_history_time);
        LinearLayout ll_item_history = view.findViewById(R.id.ll_item_history);
        item_history_ma.setText(listModel.get(i).getmIDHistory());
        item_history_time.setText(listModel.get(i).getmTimeOrder());
        return view;
    }
}
