package com.example.modelfashion.Adapter.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.R;
import com.example.modelfashion.model.Product;

import java.util.List;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ViewHolder> {

    private List<Product> listProduct;
    private ItemClickListener mClickListener;

    public void setListProduct(List<Product> list) {
        this.listProduct = list;
        notifyDataSetChanged();
    }

    public List<Product> getListProduct() {
        return this.listProduct;
    }

    public Product getProduct(int position) {
        return listProduct.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = listProduct.get(position);
        holder.tvName.setText(product.getProductName());
        holder.tvPrice.setText(product.getProductPrice());
        Glide.with(holder.imgAva.getContext())
                .load(product.getProductAvatarUrl())
                .placeholder(R.drawable.test_img)
                .into(holder.imgAva);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgAva;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            imgAva = itemView.findViewById(R.id.img_clothes_avatar);
            itemView.setOnClickListener(view -> {
                mClickListener.onItemClick(view, getAdapterPosition());
            });
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
