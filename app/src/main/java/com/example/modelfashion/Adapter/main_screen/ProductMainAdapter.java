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
import com.example.modelfashion.Model.response.main_screen.Product;
import com.example.modelfashion.Model.response.main_screen.ProductMain;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.SpacesItemDecoration;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kotlin.Triple;

public class ProductMainAdapter extends RecyclerView.Adapter<ProductMainAdapter.ProductMainViewHolder> {

    private List<Triple<String, Integer, List<Product>>> dataAdapter = new ArrayList<>();

    public void refreshList(List<Triple<String, Integer, List<Product>>> dataAdapter) {
        if (dataAdapter!=null) {
            this.dataAdapter.clear();
            this.dataAdapter = dataAdapter;
            notifyDataSetChanged();
        }
    }

//    public void addLoadMore(List<Product> list) {
//        this.listProduct.addAll(list);
//        notifyDataSetChanged();
//    }


    @NonNull
    @Override
    public ProductMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_items_layout, parent, false);
        return new ProductMainAdapter.ProductMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMainViewHolder holder, int position) {
//        Product product = listProduct.get(position);
        Triple<String, Integer, List<Product>> item = dataAdapter.get(position);

        holder.tv_product_item.setText(item.getFirst());

        ProductAdapter adapter = new ProductAdapter();
        adapter.addItems(item.getThird());
        holder.rv_product_item.addItemDecoration(new SpacesItemDecoration(10));
        holder.rv_product_item.setAdapter(adapter);

        adapter.setItemClickListener((position1, product) -> mClickListener.onItemClick(position1, product));


        holder.tv_xem_tat_ca.setOnClickListener(view -> {
            mClickListener.onSeeAllClick(position, item.getSecond());
        });

    }

    @Override
    public int getItemCount() {
        if(dataAdapter!=null) {
            return dataAdapter.size();
        }else {
            return 0;
        }
    }

    class ProductMainViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_product_item;
        TextView tv_product_item;
        TextView tv_xem_tat_ca;

        ProductMainViewHolder(View itemView) {
            super(itemView);

            rv_product_item = itemView.findViewById(R.id.rv_product_item);
            tv_product_item = itemView.findViewById(R.id.tv_product_item);
            tv_xem_tat_ca = itemView.findViewById(R.id.tv_xem_tat_ca);

        }
    }

    private ItemClickListener mClickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, Product product);
        void onSeeAllClick(int position, int id);
    }


}
