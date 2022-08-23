package com.example.modelfashion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.ProductDetailActivity;
import com.example.modelfashion.Model.sale.ProductSale;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class AllSaleAdapter extends RecyclerView.Adapter<AllSaleAdapter.SaleHolder> {
    Context context;
    ArrayList<ProductSale> productSales;
    PreferenceManager preferenceManager;

    public AllSaleAdapter(Context context, ArrayList<ProductSale> productSales) {
        this.context = context;
        this.productSales = productSales;
    }

    public void setList(ArrayList<ProductSale> list){
        if(list!=null){
            productSales.clear();
            this.productSales.addAll(list);
            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public SaleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_sale, parent, false);
        return new SaleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleHolder holder, int position) {
        ProductSale productSale = productSales.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Glide.with(context).load(productSale.getProduct_image()).into(holder.img_product_sale);
        holder.tv_discount_sale.setText(decimalFormat.format(Double.parseDouble(String.valueOf(productSale.getDiscount()))) + "%");
        holder.tv_name_sale.setText(productSale.getProduct_name());
        holder.tv_price_old.setText(decimalFormat.format(Double.parseDouble(String.valueOf(productSale.getPrice())))+" VNĐ");
        holder.tv_price_sale.setText(decimalFormat.format(Double.parseDouble(getPriceSale(productSale.getPrice(), productSale.getDiscount()))) + " VNĐ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager = new PreferenceManager(context);
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("user_id", preferenceManager.getString(Constants.KEY_ID));
                intent.putExtra(Constants.KEY_PRODUCT_NAME,productSale.getProduct_name());
                intent.putExtra(Constants.KEY_PRODUCT_ID,productSale.getProduct_id());
                context.startActivity(intent);

            }
        });

    }
    public void sortAToZ() {
        Collections.sort(this.productSales, (p1, p2) -> p1.getProduct_name().compareToIgnoreCase(p2.getProduct_name()));
        notifyDataSetChanged();
    }

    public void sortZToA() {
        Collections.sort(this.productSales, (p1, p2) -> p2.getProduct_name().compareToIgnoreCase(p1.getProduct_name()));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (productSales.size() > 10) {
            return 10;
        } else {
            return productSales.size();
        }
    }

    private String getPriceSale(int price, int discount) {
        String priceSale = "";
        int p = price - ((int) (price * discount) / 100);
        priceSale = String.valueOf(p);
        return priceSale;
    }

    public class SaleHolder extends RecyclerView.ViewHolder {
        ImageView img_product_sale;
        TextView tv_discount_sale, tv_name_sale, tv_price_old, tv_price_sale;

        public SaleHolder(@NonNull View itemView) {
            super(itemView);
            this.img_product_sale = itemView.findViewById(R.id.img_product_main_avatar);
            this.tv_discount_sale = itemView.findViewById(R.id.tv_discount_sale);
            this.tv_name_sale = itemView.findViewById(R.id.tv_my_product_name);
            this.tv_price_old = itemView.findViewById(R.id.tv_my_product_price);
            this.tv_price_sale = itemView.findViewById(R.id.tv_my_product_price_discount);

        }
    }


}
