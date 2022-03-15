package com.example.modelfashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.modelfashion.Model.Product;
import com.example.modelfashion.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Product> arrProduct;
    public ProductAdapter(Context context, ArrayList<Product> arrProduct){
        this.context = context;
        this.arrProduct = arrProduct;
    }

    public Product getProduct(int position){
        return arrProduct.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.img.setOnClickListener(view -> {
            onItemClick.imgClick(i, arrProduct.get(i));
        });

        viewHolder.imgAddToCart.setOnClickListener(view -> {
            onItemClick.imgAddToCartClick(i, arrProduct.get(i));
        });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageButton imgAddToCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_product_main_avatar);
            imgAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }

    private OnItemClick onItemClick;

    public void onItemClickListener(OnItemClick listener){
        this.onItemClick = listener;
    }

    interface OnItemClick{
        void imgClick(int position, Product product);
        void imgAddToCartClick(int position, Product product);
    }
}
