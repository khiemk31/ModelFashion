package com.example.modelfashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelfashion.Model.Product;
import com.example.modelfashion.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrProductType;
    ArrayList<Product> arrProduct;
    public ProductListAdapter(Context context, ArrayList<String> arrProductType, ArrayList<Product> arrProduct){
        this.context = context;
        this.arrProductType = arrProductType;
        this.arrProduct = arrProduct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_product_items_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ArrayList<Product> productsFiltered = new ArrayList<>();
        for(int j = 0; j < arrProduct.size(); j++){
            if(arrProduct.get(j).getProductType().equals(arrProductType.get(i))){
                productsFiltered.add(arrProduct.get(j));
            }
        }
        ProductAdapter productAdapter = new ProductAdapter(context,productsFiltered);
        viewHolder.recyclerView.setAdapter(productAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        productAdapter.onItemClickListener(new ProductAdapter.OnItemClick() {
            @Override
            public void imgClick(int position, Product product) {
                onItemClick.imgClick(position, product);
            }

            @Override
            public void imgAddToCartClick(int position, Product product) {
                onItemClick.imgAddToCartClick(position, product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProductType.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.recyclerView = itemView.findViewById(R.id.rv_product_item);
        }
    }

    private OnItemClickListener onItemClick;

    public void onItemClickListener(OnItemClickListener listener){
        this.onItemClick = listener;
    }

    public interface OnItemClickListener{
        void imgClick(int position, Product product);
        void imgAddToCartClick(int position, Product product);
    }
}
