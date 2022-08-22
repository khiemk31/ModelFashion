package com.example.modelfashion.Adapter.see_all;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.modelfashion.Model.response.main_screen.Product;
import com.example.modelfashion.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> listProduct = new ArrayList<>();
    private List<Product> listStatic = new ArrayList<>();
    private List<Product> listStaticAll = new ArrayList<>();

    private static final int VIEW_DISCOUNT = 2;
    private static final int VIEW_NORMAL = 1;

    public void setListProduct(List<Product> list) {
        this.listStatic = list;
        this.listProduct.clear();
        this.listProduct = list;
        notifyDataSetChanged();
    }

    public void listStaticAll(List<Product> list) {
        this.listStatic = list;
    }

    public void addItems(List<Product> listLoadMore) {
        this.listProduct.addAll(listLoadMore);
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.listProduct.clear();
        notifyDataSetChanged();
    }

    public void sortAToZ() {
        Collections.sort(this.listProduct, (p1, p2) -> p1.getProductName().compareToIgnoreCase(p2.getProductName()));
        notifyDataSetChanged();
    }

    public void sortZToA() {
        Collections.sort(this.listProduct, (p1, p2) -> p2.getProductName().compareToIgnoreCase(p1.getProductName()));
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void search(String word) {
        this.listProduct = this.listStaticAll.stream().filter(x -> x.getProductName().toLowerCase().contains(word.toLowerCase())).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    public List<Product> getListProduct() {
        return this.listProduct;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_DISCOUNT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_discount_see_all_by_category, parent, false);
            return new DiscountViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_see_all_by_category, parent, false);
            return new NormalViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = listProduct.get(position);

        if (holder.getItemViewType() == VIEW_NORMAL) {
            NormalViewHolder holderNormal = (NormalViewHolder) holder;
            holderNormal.tv_my_product_name.setText(product.getProductName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holderNormal.tv_my_product_price.setText(decimalFormat.format(Double.parseDouble(String.valueOf(product.getPrice()))) + "đ");
            holderNormal.itemView.setOnClickListener(view -> {
                mClickListener.onItemClick(position, product);
            });
            if (product.getProductImage().contains("http:")) {
                Glide.with(holderNormal.img_product_main_avatar.getContext())
                        .load(product.getProductImage().replace("http:", "https:"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .placeholder(R.drawable.test_img)
                        .into(holderNormal.img_product_main_avatar);
            } else {
                Glide.with(holderNormal.img_product_main_avatar.getContext())
                        .load(product.getProductImage())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .placeholder(R.drawable.test_img)
                        .into(holderNormal.img_product_main_avatar);
            }
        } else  {
            DiscountViewHolder holderDiscount = (DiscountViewHolder) holder;

            holderDiscount.tv_my_product_name.setText(product.getProductName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holderDiscount.tv_my_product_price.setText(decimalFormat.format(Double.parseDouble(String.valueOf(product.getPrice()))) + "đ");
            holderDiscount.itemView.setOnClickListener(view -> {
                mClickListener.onItemClick(position, product);
            });
            if (product.getProductImage().contains("http:")) {
                Glide.with(holderDiscount.img_product_main_avatar.getContext())
                        .load(product.getProductImage().replace("http:", "https:"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .placeholder(R.drawable.test_img)
                        .into(holderDiscount.img_product_main_avatar);
            } else {
                Glide.with(holderDiscount.img_product_main_avatar.getContext())
                        .load(product.getProductImage())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .placeholder(R.drawable.test_img)
                        .into(holderDiscount.img_product_main_avatar);
            }
            holderDiscount.tv_my_product_price_discount.setText(formatString((int) (product.getPrice() * (1 - (product.getDiscount() / 100f)))) + " đ");
            holderDiscount.tv_discount_sale.setText(product.getDiscount() + "%");
        }

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_main_avatar;
        TextView tv_my_product_name, tv_my_product_price;

        NormalViewHolder(View itemView) {
            super(itemView);
            img_product_main_avatar = itemView.findViewById(R.id.img_product_main_avatar);
            tv_my_product_name = itemView.findViewById(R.id.tv_my_product_name);
            tv_my_product_price = itemView.findViewById(R.id.tv_my_product_price);

        }
    }

    class DiscountViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_main_avatar;
        TextView tv_my_product_name, tv_my_product_price, tv_my_product_price_discount, tv_discount_sale;

        DiscountViewHolder(View itemView) {
            super(itemView);
            img_product_main_avatar = itemView.findViewById(R.id.img_product_main_avatar);
            tv_my_product_name = itemView.findViewById(R.id.tv_my_product_name);
            tv_my_product_price = itemView.findViewById(R.id.tv_my_product_price);
            tv_my_product_price_discount = itemView.findViewById(R.id.tv_my_product_price_discount);
            tv_discount_sale = itemView.findViewById(R.id.tv_discount_sale);

        }
    }

    public Product getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getDiscount() == 0) {
            return 1;
        } else return 2;
    }

    private ItemClickListener mClickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public static String formatString(int number) {
        DecimalFormat df = new DecimalFormat(",###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        return df.format(number);
    }

    public interface ItemClickListener {
        void onItemClick(int position, Product productPreview);
    }
}
