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
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<MyProduct> listProduct = new ArrayList<>();

    public void setListProduct(List<MyProduct> list) {
        this.listProduct.clear();
        this.listProduct = list;
        notifyDataSetChanged();
    }

    public List<MyProduct> getListProduct() {
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
        MyProduct product = listProduct.get(position);

        ArrayList<Integer> listDrawable = new ArrayList<>();
        listDrawable.add(R.drawable.bg_fade_1);
        listDrawable.add(R.drawable.bg_fade_2);
        listDrawable.add(R.drawable.bg_fade_3);
        Random generator = new Random();
        holder.viewBackground.setBackgroundResource(listDrawable.get(generator.nextInt(3)));

        holder.tvName.setText(product.getProduct_name());
        holder.tvPrice.setText(String.valueOf(product.getPriceFormat()));
        holder.itemView.setOnClickListener(view -> {
            mClickListener.onItemClick(position, product);
        });
        holder.imgAdd.setOnClickListener(view -> {
            mClickListener.onAddToCartClick(position, product);
        });
        if (product.getPhotos().get(0).contains("http:")) {
            Glide.with(holder.imgAva.getContext())
                    .load(product.getPhotos().get(0).replace("http:", "https:"))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .placeholder(R.drawable.test_img)
                    .into(holder.imgAva);
        } else {
            Glide.with(holder.imgAva.getContext())
                    .load(product.getPhotos().get(0))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .placeholder(R.drawable.test_img)
                    .into(holder.imgAva);
        }
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View viewBackground;
        ImageView imgAva, imgAdd;
        TextView tvName, tvPrice;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            imgAva = itemView.findViewById(R.id.img_product_avatar);
            viewBackground = itemView.findViewById(R.id.view_background);
            imgAdd = itemView.findViewById(R.id.img_add);

        }
    }

    private ItemClickListener mClickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, MyProduct productPreview);

        void onAddToCartClick(int position, MyProduct productPreview);
    }
}
