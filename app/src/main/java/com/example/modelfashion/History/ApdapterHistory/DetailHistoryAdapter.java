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
    ArrayList<BillDetail> arr_bill_detail;
    ArrayList<MyProduct> arr_product;
    private ClothesAdapter.ItemClickListener mClickListener;
//    List<ProductHistory> productHistoryList;
//
//    public DetailHistoryAdapter(Context context, List<ProductHistory> productHistoryList) {
//        this.context = context;
//        this.productHistoryList = productHistoryList;
//    }
    public DetailHistoryAdapter(Context context, ArrayList<BillDetail> arr_bill_detail, ArrayList<MyProduct> arr_product){
        this.context = context;
        this.arr_bill_detail = arr_bill_detail;
        this.arr_product = arr_product;
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
        RelativeLayout rl_view_subproduct = view.findViewById(R.id.rl_view_subproduct);
        rl_view_subproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("user_id", DetailHistoryActivity.user_id);
                intent.putExtra(KEY_PRODUCT_NAME,arr_product.get(i).getProduct_name());
                intent.putExtra(KEY_PRODUCT_ID,arr_product.get(i).getProduct_id());
                context.startActivity(intent);
            }
        });
        DecimalFormat format = new DecimalFormat("###,###,###");
        //Set data
        Glide.with(context).load(arr_product.get(i).getProduct_image()).into(img_subproduct);
        tv_name_subproduct.setText(arr_product.get(i).getProduct_name());
        tv_price.setText(format.format((arr_product.get(i).getPrice())*Integer.parseInt(arr_bill_detail.get(i).getQuantity()))+" VNĐ");
        tv_size_subproduct.setText(arr_bill_detail.get(i).getSize());
        tv_sumproduct.setText("Số lượng: "+arr_bill_detail.get(i).getQuantity());
//        ProductHistory productHistory = productHistoryList.get(i);
//        tv_name_subproduct.setText(productHistory.getmNameProduct());
//        Locale locale = new Locale("vi","VN");
//        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
//        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
//        String money = numberFormat.format(Double.parseDouble(productHistory.getmPriceProduct()));
//        tv_price.setText(money);
//        tv_size_subproduct.setText(productHistory.getmSizeProduct());
//        tv_sumproduct.setText("x"+productHistory.getmSumProduct());
//        Glide.with(context).load(productHistory.getmImgeProduct()).into(img_subproduct);
        return view;
    }
}
