package com.example.modelfashion.Adapter.main_screen;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Model.response.main_screen.Product;
import com.example.modelfashion.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder> {

    private List<Product> list = new ArrayList<>();

    public void addItems(List<Product> list) {
        if (list!=null) {
            this.list.clear();
            this.list = list;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return  new ProductAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterViewHolder holder, int position) {
        Product product = list.get(position);

        Glide.with(holder.itemView.getContext()).load(product.getProductImage()).into(holder.img_product_main_avatar);
        holder.tv_my_product_name.setText(product.getProductName());
        holder.tv_my_product_price.setText(formatString(product.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClick(position, product);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_main_avatar;
        TextView tv_my_product_name;
        TextView tv_my_product_price;

        ProductAdapterViewHolder(View itemView) {
            super(itemView);

            img_product_main_avatar = itemView.findViewById(R.id.img_product_main_avatar);
            tv_my_product_name = itemView.findViewById(R.id.tv_my_product_name);
            tv_my_product_price = itemView.findViewById(R.id.tv_my_product_price);


        }
    }

    public static String formatString(int number) {
        DecimalFormat df = new DecimalFormat(",###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        return df.format(number);
    }

    public interface ItemClickListener {
        void onItemClick(int position, Product product);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
