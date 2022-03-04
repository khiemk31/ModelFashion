package com.example.modelfashion.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.modelfashion.Adapter.cart.CartAdapter;
import com.example.modelfashion.Model.Product;
import com.example.modelfashion.R;
import java.util.ArrayList;

public class CartFragment extends Fragment {
    private View initView;
    private ArrayList<Product> productArrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = initView.findViewById(R.id.list_product_cart);
        fakeDataProduct();
        setAdapter();
        return initView;
    }

    private void setAdapter() {
        CartAdapter adapter = new CartAdapter(productArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void fakeDataProduct() {
        productArrayList.add(new Product(1, "Sản Phẩm 1", "", "650,000đ", "", "L", 1));
        productArrayList.add(new Product(2, "Sản Phẩm 2", "", "750,000đ", "", "M", 1));
        productArrayList.add(new Product(3, "Sản Phẩm 3", "", "850,000đ", "", "XL", 1));
        productArrayList.add(new Product(4, "Sản Phẩm 4", "", "950,000đ", "", "2XL", 1));
        productArrayList.add(new Product(5, "Sản Phẩm 5", "", "1050,000đ", "", "3XL", 1));
    }
}