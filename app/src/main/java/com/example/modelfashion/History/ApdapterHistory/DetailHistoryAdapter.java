package com.example.modelfashion.History.ApdapterHistory;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Adapter.category.ClothesAdapter;
import com.example.modelfashion.History.ViewHistory.DetailHistoryActivity;
import com.example.modelfashion.Model.MHistory.ProductHistory;
import com.example.modelfashion.Model.response.bill.BillDetail;
import com.example.modelfashion.Model.response.bill.BillProducts;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailHistoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<BillProducts> arr_bill_detail;

    private ClothesAdapter.ItemClickListener mClickListener;
//    List<ProductHistory> productHistoryList;
//
//    public DetailHistoryAdapter(Context context, List<ProductHistory> productHistoryList) {
//        this.context = context;
//        this.productHistoryList = productHistoryList;
//    }
    public DetailHistoryAdapter(Context context, ArrayList<BillProducts> arr_bill_detail){
        this.context = context;
        this.arr_bill_detail = arr_bill_detail;

    }
    @Override
    public int getCount() {
        return arr_bill_detail.size();
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
        view = LayoutInflater.from(context).inflate(R.layout.item_subproducts,viewGroup,false);
        ImageView img_subproduct = view.findViewById(R.id.img_subproduct);
        TextView tv_name_subproduct = view.findViewById(R.id.tv_name_subproduct);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_size_subproduct = view.findViewById(R.id.tv_size_subproduct);
        TextView tv_sumproduct = view.findViewById(R.id.tv_sumproduct);
        TextView tv_price_sale = view.findViewById(R.id.tv_price_sale);
        RelativeLayout rl_view_subproduct = view.findViewById(R.id.rl_view_subproduct);
//        rl_view_subproduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ProductDetailActivity.class);
//                intent.putExtra("user_id", DetailHistoryActivity.user_id);
//                intent.putExtra(KEY_PRODUCT_NAME,arr_product.get(i).getProduct_name());
//                intent.putExtra(KEY_PRODUCT_ID,arr_product.get(i).getProduct_id());
//                context.startActivity(intent);
//            }
//        });
        DecimalFormat format = new DecimalFormat("###,###,###");
        //Set data
        Glide.with(context).load(arr_bill_detail.get(i).getProduct_image()).into(img_subproduct);
        tv_name_subproduct.setText(arr_bill_detail.get(i).getProduct_name());

        tv_size_subproduct.setText(arr_bill_detail.get(i).getSize());
        tv_sumproduct.setText("x"+arr_bill_detail.get(i).getQuantity());
        if(Integer.parseInt(arr_bill_detail.get(i).getPrice_sale()) == 0){
            tv_price_sale.setVisibility(View.GONE);
            tv_price.setText(format.format(Double.parseDouble(arr_bill_detail.get(i).getPrice())) + " đ");
        }else {
            tv_price.setText(format.format(Double.parseDouble(arr_bill_detail.get(i).getPrice_sale())) + " đ");
            tv_price_sale.setText(format.format(Double.parseDouble(arr_bill_detail.get(i).getPrice())) + " đ");
        }

        return view;
    }
}
