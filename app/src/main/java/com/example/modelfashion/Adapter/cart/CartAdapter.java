package com.example.modelfashion.Adapter.cart;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.modelfashion.Model.request.CreateBillRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.database.MyProductCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHoder> {
    private List<MyProductCart> productArrayList = new ArrayList<>();
    private CartOnClick cartOnClick;

    public void setListData(List<MyProductCart> list) {
        this.productArrayList = list;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.productArrayList.clear();
        notifyDataSetChanged();
    }

    public void setOnClick(CartOnClick cartOnClick) {
        this.cartOnClick = cartOnClick;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private TextView nameProduct, priceProduct, sizeProduct, delete;
        private Button btnIncrease, btnDecrease;
        private ImageView imgCart;
        private TextView amount;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            imgCart = itemView.findViewById(R.id.img_cart);
            nameProduct = itemView.findViewById(R.id.tv_name_product);
            priceProduct = itemView.findViewById(R.id.tv_price_product);
            sizeProduct = itemView.findViewById(R.id.tv_size_product);
            amount = itemView.findViewById(R.id.tv_amount);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            delete = itemView.findViewById(R.id.tv_delete);


        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        AtomicInteger minteger = new AtomicInteger(productArrayList.get(position).getProductQuantity());
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        if (productArrayList.get(position).getDiscount() == 0) {
            String money_format = formatter.format((productArrayList.get(position).getProductPrice()));
            holder.priceProduct.setText("Giá: " + money_format + " VNĐ");
        }else {
            String money_format_sale = formatter.format((productArrayList.get(position).getProductPriceSale()));
            holder.priceProduct.setText("Giá: " + money_format_sale + " VNĐ");
        }

        holder.nameProduct.setText("Sản phẩm: " + productArrayList.get(position).getProductName());
        holder.sizeProduct.setText("Size: " + productArrayList.get(position).getProductSize());
        Glide.with(holder.btnIncrease.getContext()).load(productArrayList.get(position).getProductImage()).into(holder.imgCart);
        holder.delete.setOnClickListener(view -> {
            MyProductCart myProductCart = productArrayList.get(position);
            cartOnClick.OnClickDelete(position, myProductCart);
        });

        holder.amount.setText("" + productArrayList.get(position).getProductQuantity());

        holder.btnIncrease.setOnClickListener(view -> {
            if (minteger.get() >= 9){
                Toast.makeText(holder.btnIncrease.getContext(), "Số lượng tối đa là 10", Toast.LENGTH_SHORT).show();
                holder.amount.setText("10");
            }else {
                minteger.set(minteger.get() + 1);
                holder.amount.setText("" + minteger);
                cartOnClick.OnClickIncreaseQuantity(position, productArrayList.get(position));
            }
        });
        holder.btnDecrease.setOnClickListener(view -> {
            minteger.set(minteger.get() - 1);
            if(minteger.get() > 0 ){
                minteger.set(minteger.get() - 1);
                holder.amount.setText("" + minteger);
                cartOnClick.OnClickDecreaseQuantity(position, productArrayList.get(position));
            }else if (minteger.get() <= 0){
                cartOnClick.OnClickDelete(position, productArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public void increaseAmount(int position) {
        productArrayList.get(position).setProductQuantity(productArrayList.get(position).getProductQuantity() + 1);
        notifyItemChanged(position);
    }

    public void decreaseAmount(int position) {
        productArrayList.get(position).setProductQuantity(productArrayList.get(position).getProductQuantity() - 1);
        notifyItemChanged(position);
    }

    public void setAmount(int position, int newAmount) {
        productArrayList.get(position).setProductQuantity(newAmount);
        notifyItemChanged(position);
    }

    public void removeProduct(int position) {
        productArrayList.remove(position);
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CreateBillRequest billInformation(String userId) {
        List<String> listProduct = new ArrayList<>();
        List<String> listQuantity = new ArrayList<>();
        List<String> listSize = new ArrayList<>();
        List<String> listPrice = new ArrayList<>();
        List<String> listPriceSale = new ArrayList<>();
        String discountVoucherPrice = "0";

        AtomicLong totalPrice = new AtomicLong();
        for (int i = 0; i < productArrayList.size(); i++) {
            MyProductCart myProductCart = productArrayList.get(i);
            listProduct.add(myProductCart.getProductName());
            listQuantity.add(String.valueOf(myProductCart.getProductQuantity()));
            listSize.add(myProductCart.getProductSize());
            totalPrice.addAndGet((long) myProductCart.getProductPrice() * myProductCart.getProductQuantity());
            listPrice.add(String.valueOf(myProductCart.getProductPrice()));
            listPriceSale.add(String.valueOf(myProductCart.getProductPriceSale()));
            discountVoucherPrice = String.valueOf(myProductCart.getDiscount()) + "";
        }

//        productArrayList.forEach(myProductCart -> {
//            listProduct.add(myProductCart.getProductName());
//            listQuantity.add(String.valueOf(myProductCart.getProductQuantity()));
//            listSize.add(myProductCart.getProductSize());
//            totalPrice.addAndGet((long) myProductCart.getProductPrice() * myProductCart.getProductQuantity());
//            listPrice.add(String.valueOf(myProductCart.getProductPrice()));
//            listPriceSale.add(String.valueOf(myProductCart.getProductPriceSale()));
//            discountVoucherPrice = String.valueOf(myProductCart.getDiscount()) + "";
//        });

        String price = String.valueOf(productArrayList.get(0).getProductPrice());
        String image = productArrayList.get(0).getProductImage();

        return new CreateBillRequest(userId, listProduct, listQuantity, listSize, String.valueOf(totalPrice.get()), price, listPrice, listPriceSale, discountVoucherPrice, image);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Long getTotal() {
        long total = 0L;
        for (int i = 0; i < productArrayList.size(); i++) {
            total += (long) productArrayList.get(i).getProductPrice() * productArrayList.get(i).getProductQuantity();
        }
        return total;
    }


    public interface CartOnClick {
        void OnClickDelete(int position, MyProductCart myProductCart);
        void OnClickIncreaseQuantity(int position, MyProductCart myProductCart);
        void OnClickDecreaseQuantity(int position, MyProductCart myProductCart);
//        void OnTypeQuantityListener(int position, MyProductCart myProductCart, int newAmount);
    }

}
