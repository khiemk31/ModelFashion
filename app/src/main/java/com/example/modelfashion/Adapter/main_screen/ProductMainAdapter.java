package com.example.modelfashion.Adapter.main_screen;

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
import com.example.modelfashion.Model.response.main_screen.ProductMain;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.List;

public class ProductMainAdapter extends RecyclerView.Adapter<ProductMainAdapter.ProductMainViewHolder> {

    private List<ProductMain> listProduct = new ArrayList<>();

    public void refreshList(List<ProductMain> list) {
        this.listProduct.clear();
        this.listProduct = list;
        notifyDataSetChanged();
    }

    public void addLoadMore(List<ProductMain> list) {
        this.listProduct.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductMainAdapter.ProductMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMainViewHolder holder, int position) {
        ProductMain product = listProduct.get(position);

        holder.tv_my_product_name.setText(product.getProductName());
        holder.tv_my_product_price.setText("" + product.getPrice());
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


        holder.itemView.setOnClickListener(view -> {
            mClickListener.onItemClick(position, product);
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class ProductMainViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_main_avatar;
        TextView tv_my_product_name;
        TextView tv_my_product_price;

        ProductMainViewHolder(View itemView) {
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
        void onItemClick(int position, ProductMain productMain);
    }
}
