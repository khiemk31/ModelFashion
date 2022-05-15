package com.example.modelfashion.Adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelfashion.Model.response.category.MyCategory;
import com.example.modelfashion.Model.response.my_product.MyProductByCategory;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Pair<MyCategory, ArrayList<MyProductByCategory>>> data = new ArrayList<>();

    public ProductListAdapter() {
    }

    public void clearAllData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public void setListProduct(List<Pair<MyCategory, ArrayList<MyProductByCategory>>> data) {
        this.data.clear();
        this.data = data;
        notifyDataSetChanged();
    }

    public void addListProduct(List<Pair<MyCategory, ArrayList<MyProductByCategory>>> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_product_items_layout, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProductAdapter productAdapter = new ProductAdapter();
        viewHolder.recyclerView.setAdapter(productAdapter);
        productAdapter.setListProduct(data.get(i).second);

        viewHolder.tv_product_item.setText(data.get(i).first.getCategoryName());

        viewHolder.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        productAdapter.onItemClickListener(new ProductAdapter.OnItemClick() {
            @Override
            public void imgClick(int position, MyProductByCategory product) {
                onItemClick.imgClick(position, product);
            }

            @Override
            public void imgAddToCartClick(int position, MyProductByCategory product) {
                onItemClick.imgAddToCartClick(position, product);
            }
        });

        viewHolder.tv_xem_tat_ca.setOnClickListener(view -> {
            onItemClick.imgWatchAll(i, data.get(i).first);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        View view;
        TextView tv_product_item, tv_xem_tat_ca;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.recyclerView = itemView.findViewById(R.id.rv_product_item);
            tv_product_item = itemView.findViewById(R.id.tv_product_item);
            tv_xem_tat_ca = itemView.findViewById(R.id.tv_xem_tat_ca);
        }
    }

    private OnItemClickListener onItemClick;

    public void onItemClickListener(OnItemClickListener listener){
        this.onItemClick = listener;
    }

    public interface OnItemClickListener{
        void imgClick(int position, MyProductByCategory product);
        void imgAddToCartClick(int position, MyProductByCategory product);
        void imgWatchAll(int position, MyCategory myCategory);
    }
}
