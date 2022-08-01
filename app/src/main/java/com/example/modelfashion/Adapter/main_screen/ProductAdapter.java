package com.example.modelfashion.Adapter.main_screen;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Model.response.main_screen.Product;
import com.example.modelfashion.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> list = new ArrayList<>();

    private static final int VIEW_DISCOUNT = 2;
    private static final int VIEW_NORMAL = 1;

    public void addItems(List<Product> list) {
        if (list!=null) {
            this.list.clear();
            this.list = list;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_DISCOUNT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_discount_layout, parent, false);
            return  new ProductDisCountViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
            return  new ProductAdapterViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = list.get(position);

        if (holder.getItemViewType() == VIEW_NORMAL) {
            ProductAdapterViewHolder normal = (ProductAdapterViewHolder) holder;
            Glide.with(normal.itemView.getContext()).load(product.getProductImage()).into(normal.img_product_main_avatar);
            normal.tv_my_product_name.setText(product.getProductName());
            normal.tv_my_product_price.setText(formatString(product.getPrice()));

            holder.itemView.setOnClickListener(v -> {
                itemClickListener.onItemClick(position, product);
            });
        } else if (holder.getItemViewType() == VIEW_DISCOUNT) {
            ProductDisCountViewHolder discount = (ProductDisCountViewHolder) holder;
            Glide.with(discount.itemView.getContext()).load(product.getProductImage()).into(discount.img_product_sale);
            discount.tv_discount_sale.setText(product.getDiscount()+"%");
            discount.tv_name_sale.setText(product.getProductName());
            discount.tv_price_old.setText(formatString(product.getPrice()));
            discount.tv_price_sale.setText(formatString((int) (product.getPrice() * (1 - (product.getDiscount() / 100f)))) + " đ");
        }
    }

    public Product getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getDiscount() == 0) {
            return 1;
        }
        else return 2;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_main_avatar;
        TextView tv_my_product_name;
        TextView tv_my_product_price;

        ProductAdapterViewHolder(View itemView) {
            super(itemView);

            img_product_main_avatar = itemView.findViewById(R.id.img_product_main_avatar);
            tv_my_product_name = itemView.findViewById(R.id.tv_my_product_name);
            tv_my_product_price = itemView.findViewById(R.id.tv_my_product_price);
        }
    }

    class ProductDisCountViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_sale;
        TextView tv_discount_sale, tv_name_sale, tv_price_old, tv_price_sale;

        ProductDisCountViewHolder(View itemView) {
            super(itemView);

            img_product_sale = itemView.findViewById(R.id.img_product_sale);
            tv_discount_sale = itemView.findViewById(R.id.tv_discount_sale);
            tv_name_sale = itemView.findViewById(R.id.tv_name_sale);
            tv_price_old = itemView.findViewById(R.id.tv_price_old);
            tv_price_sale = itemView.findViewById(R.id.tv_price_sale);
        }
    }

    public static String formatString(int number) {
        DecimalFormat df = new DecimalFormat(",###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        return df.format(number);
    }

    public interface ItemClickListener {
        void onItemClick(int position, Product product);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
