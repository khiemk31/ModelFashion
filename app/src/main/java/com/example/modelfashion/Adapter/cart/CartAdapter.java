package com.example.modelfashion.Adapter.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.modelfashion.Model.Product;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHoder> {
    private ArrayList<Product> productArrayList;

    public CartAdapter(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private TextView nameProduct, priceProduct, sizeProduct, amount;
        private Button btnIncrease, btnDecrease;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.tv_name_product);
            priceProduct = itemView.findViewById(R.id.tv_price_product);
            sizeProduct = itemView.findViewById(R.id.tv_size_product);
            amount = itemView.findViewById(R.id.tv_amount);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
     AtomicInteger minteger= new AtomicInteger(1);
        holder.nameProduct.setText(productArrayList.get(position).getProductName());
        holder.priceProduct.setText(productArrayList.get(position).getProductPrice());
        holder.sizeProduct.setText(productArrayList.get(position).getProductType());

        holder.btnIncrease.setOnClickListener(view -> {
        minteger.set(minteger.get() + 1);
            holder.amount.setText(""+minteger);
        });
        holder.btnDecrease.setOnClickListener(view -> {
            minteger.set(minteger.get() - 1);
            holder.amount.setText(""+minteger);
        });
            holder.amount.setText(""+minteger);
    }



    @Override
    public int getItemCount() {
        return productArrayList.size();
    }


}
