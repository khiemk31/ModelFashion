package com.example.modelfashion.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modelfashion.Adapter.ProductListAdapter;
import com.example.modelfashion.Model.Product;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.Arrays;

public class WomenPageFragment extends Fragment {
    private RecyclerView rvWomenPage;
    private Context mContext;
    public WomenPageFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_women_page, container, false);
        rvWomenPage = view.findViewById(R.id.rv_women_page_fm);
        ArrayList<Product> arrProduct = new ArrayList<>();
        ArrayList<String> arrProductType = new ArrayList<String>(Arrays.asList("Áo","Quần","Ba Lô"));
        arrProduct.add(new Product(1,"Áo 1","","100.000 đ","","Áo",0));
        arrProduct.add(new Product(2,"Áo 2","","200.000 đ","","Áo",0));
        arrProduct.add(new Product(3,"Quần 1","","100.000 đ","","Quần",0));
        arrProduct.add(new Product(4,"Quần 2","","200.000 đ","","Quần",0));
        arrProduct.add(new Product(5,"Quần 3","","300.000 đ","","Quần",0));
        arrProduct.add(new Product(6,"Ba lô 1","","100.000 đ","","Ba Lô",0));
        ProductListAdapter productListAdapter = new ProductListAdapter(mContext, arrProductType, arrProduct);
        rvWomenPage.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        rvWomenPage.setAdapter(productListAdapter);

        return view;
    }
}