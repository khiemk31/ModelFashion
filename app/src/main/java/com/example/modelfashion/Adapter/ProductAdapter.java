package com.example.modelfashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Model.Product;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<MyProductByCategory> arrProduct = new ArrayList<>();

    public void setListProduct(List<MyProductByCategory> list) {
        this.arrProduct.clear();
        this.arrProduct = list;
        notifyDataSetChanged();
    }

    public MyProductByCategory getProduct(int position) {
        return arrProduct.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item_layout,viewGroup, false);
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
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String money_format = formatter.format((arrProduct.get(i).getPrice()));
        Glide.with(viewHolder.img.getContext()).load(arrProduct.get(i).getProductImage()).placeholder(R.drawable.test_img2).into(viewHolder.img);
        viewHolder.tvProductName.setText(arrProduct.get(i).getProductName());
        viewHolder.tvPrice.setText(money_format+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageButton imgAddToCart;
        TextView tvProductName, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_product_main_avatar);
            tvProductName = itemView.findViewById(R.id.tv_my_product_name);
            tvPrice = itemView.findViewById(R.id.tv_my_product_price);
            imgAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }

    private OnItemClick onItemClick;

    public void onItemClickListener(OnItemClick listener) {
        this.onItemClick = listener;
    }

    interface OnItemClick {
        void imgClick(int position, MyProductByCategory product);
        void imgAddToCartClick(int position, MyProductByCategory product);
    }
}
