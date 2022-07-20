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

public class ProductSaleAdapter extends RecyclerView.Adapter<ProductSaleAdapter.SaleHolder> {
    Context context;
    ArrayList<ProductSale> productSales;
    PreferenceManager preferenceManager;

    public ProductSaleAdapter(Context context, ArrayList<ProductSale> productSales) {
        this.context = context;
        this.productSales = productSales;
    }

    @NonNull
    @Override
    public SaleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_sale, parent, false);
        return new SaleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleHolder holder, int position) {
        ProductSale productSale = productSales.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Glide.with(context).load(productSale.getProduct_image()).into(holder.img_product_sale);
        holder.tv_discount_sale.setText(decimalFormat.format(productSale.getDiscount()) + "%");
        holder.tv_name_sale.setText(productSale.getProduct_name());
        holder.tv_price_old.setText(decimalFormat.format(productSale.getPrice())+" đ");
        holder.tv_price_sale.setText(decimalFormat.format(getPriceSale(productSale.getPrice(), productSale.getDiscount())) + " đ");
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

    @Override
    public int getItemCount() {
        if (productSales.size() > 10) {
            return 10;
        } else {
            return productSales.size();
        }
    }

    private int getPriceSale(int price, int discount) {

        int p = price - ((int) (price * discount) / 100);

        return p;
    }

    public class SaleHolder extends RecyclerView.ViewHolder {
        ImageView img_product_sale;
        TextView tv_discount_sale, tv_name_sale, tv_price_old, tv_price_sale;

        public SaleHolder(@NonNull View itemView) {
            super(itemView);
            this.img_product_sale = itemView.findViewById(R.id.img_product_sale);
            this.tv_discount_sale = itemView.findViewById(R.id.tv_discount_sale);
            this.tv_name_sale = itemView.findViewById(R.id.tv_name_sale);
            this.tv_price_old = itemView.findViewById(R.id.tv_price_old);
            this.tv_price_sale = itemView.findViewById(R.id.tv_price_sale);

        }
    }


}
