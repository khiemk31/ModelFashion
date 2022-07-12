package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.modelfashion.Adapter.AllSaleAdapter;
import com.example.modelfashion.Fragment.MainFragment;
import com.example.modelfashion.Model.sale.ProductSale;
import com.example.modelfashion.R;

import java.util.ArrayList;

import javax.xml.transform.sax.SAXResult;

public class ViewSaleActivity extends AppCompatActivity {
    private RecyclerView rcl_view_sale;
    private ArrayList<ProductSale> productSaleSearch = new ArrayList<>();
    private String content = "";
    private EditText edt_search_sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sale);
        init();
    }
    private void init(){
        rcl_view_sale = findViewById(R.id.rcl_view_sale);
        edt_search_sale = findViewById(R.id.edt_search_sale);
        AllSaleAdapter allSaleAdapter = new AllSaleAdapter(ViewSaleActivity.this, MainFragment.productSales);
        rcl_view_sale.setAdapter(allSaleAdapter);
        edt_search_sale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                content = s.toString().trim().toLowerCase();
                SearchSaleAsyncTask searchSaleAsyncTask = new SearchSaleAsyncTask();
                searchSaleAsyncTask.execute();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        findViewById(R.id.back_view_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadSearch(){
        AllSaleAdapter allSaleAdapter = new AllSaleAdapter(ViewSaleActivity.this, productSaleSearch);
        rcl_view_sale.setAdapter(allSaleAdapter);
    }
    private void getListSearch(){
        productSaleSearch.clear();
        for (int i=0;i<MainFragment.productSales.size();i++){
            if(MainFragment.productSales.get(i).getProduct_name().toLowerCase().contains(content.toLowerCase())){
                productSaleSearch.add(MainFragment.productSales.get(i));
            }
        }

    }



    public class SearchSaleAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            getListSearch();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            loadSearch();
        }
    }
}