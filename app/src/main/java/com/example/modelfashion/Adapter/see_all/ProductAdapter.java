package com.example.modelfashion.Adapter.see_all;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.modelfashion.Model.response.main_screen.Product;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.Model.response.my_product.ProductByCategory;
import com.example.modelfashion.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> listProduct = new ArrayList<>();

    public void setListProduct(List<Product> list) {
        this.listProduct.clear();
        this.listProduct = list;
        notifyDataSetChanged();
    }

    public void addItems(List<Product> listLoadMore) {
        this.listProduct.addAll(listLoadMore);
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.listProduct.clear();
        notifyDataSetChanged();
    }

    public List<Product> getListProduct() {
        return this.listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_see_all_by_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = listProduct.get(position);

        holder.tv_my_product_price.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_my_product_name.setText(decimalFormat.format(Double.parseDouble(String.valueOf(product.getPrice())))+"đ");
        holder.itemView.setOnClickListener(view -> {
            mClickListener.onItemClick(position, product);
        });
//        holder.imgAdd.setOnClickListener(view -> {
//            mClickListener.onAddToCartClick(position, product);
//        });
        if (product.getProductImage().contains("http:")) {
            Glide.with(holder.img_product_main_avatar.getContext())
                    .load(product.getProductImage().replace("http:", "https:"))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .placeholder(R.drawable.test_img)
                    .into(holder.img_product_main_avatar);
        } else {
            Glide.with(holder.img_product_main_avatar.getContext())
                    .load(product.getProductImage())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .placeholder(R.drawable.test_img)
                    .into(holder.img_product_main_avatar);
        }
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_main_avatar;
        TextView tv_my_product_name, tv_my_product_price;

        ViewHolder(View itemView) {
            super(itemView);
            img_product_main_avatar = itemView.findViewById(R.id.img_product_main_avatar);
            tv_my_product_name = itemView.findViewById(R.id.tv_my_product_name);
            tv_my_product_price = itemView.findViewById(R.id.tv_my_product_price);

        }
    }

    private ItemClickListener mClickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, Product productPreview);
    }
}
