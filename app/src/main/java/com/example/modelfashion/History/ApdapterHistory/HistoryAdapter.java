package com.example.modelfashion.History.ApdapterHistory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.modelfashion.History.ViewHistory.DetailHistoryActivity;
import com.example.modelfashion.History.ViewHistory.HistoryActivity;
import com.example.modelfashion.Model.MHistory.ModelHistory;
import com.example.modelfashion.Model.MHistory.ProductHistory;
import com.example.modelfashion.R;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        String price0 = numberFormat.format(Double.parseDouble(listModel.get(i).getProductHistoryList().get(0).getmPriceProduct()));
        ImageView img_subproduct0 = view.findViewById(R.id.img_subproduct0);
        TextView tv_name_subproduct0 = view.findViewById(R.id.tv_name_subproduct0);
        TextView tv_sumproduct0 = view.findViewById(R.id.tv_sumproduct0);
        TextView tv_size_subproduct0 = view.findViewById(R.id.tv_size_subproduct0);
        TextView tv_price0 = view.findViewById(R.id.tv_price0);
        TextView tv_sumSP = view.findViewById(R.id.tv_sumSP);
        TextView tv_sumPrice = view.findViewById(R.id.tv_sumPrice);
        TextView tv_detail = view.findViewById(R.id.tv_detail);
        TextView item_history_ma = view.findViewById(R.id.item_history_ma);
        TextView item_history_time = view.findViewById(R.id.item_history_time);
        LinearLayout ll_item_history = view.findViewById(R.id.ll_item_history);
        item_history_ma.setText("Mã đơn: "+listModel.get(i).getmIDHistory());
        item_history_time.setText("Ngày nhận: "+listModel.get(i).getmTimeOrder());
        Glide.with(context).load(listModel.get(i).getProductHistoryList().get(0).getmImgeProduct()).into(img_subproduct0);
        tv_name_subproduct0.setText(listModel.get(i).getProductHistoryList().get(0).getmNameProduct());
        tv_sumproduct0.setText("x"+listModel.get(i).getProductHistoryList().get(0).getmSumProduct());
        tv_size_subproduct0.setText(listModel.get(i).getProductHistoryList().get(0).getmSizeProduct());
        tv_price0.setText(price0);
        tv_sumSP.setText(String.valueOf(listModel.get(i).getProductHistoryList().size())+" Sản phẩm");
        tv_sumPrice.setText("Tổng: "+sumPrice(listModel.get(i).getProductHistoryList()));
        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailHistoryActivity.class);
                intent.putExtra("index",i);
                intent.putExtra("view","History");
                context.startActivity(intent);

            }
        });
        return view;
    }

    private String sumPrice(List<ProductHistory> list){
        int sum = 0;
        for (int i = 0;i<list.size();i++){
            sum+=Integer.parseInt(list.get(i).getmPriceProduct())*Integer.parseInt(list.get(i).getmSumProduct());
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        String sumP = numberFormat.format(Double.parseDouble(String.valueOf(sum)));
        return sumP;
    }
}
