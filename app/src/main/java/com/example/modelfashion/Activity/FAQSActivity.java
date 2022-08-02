package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.modelfashion.Adapter.FAQsAdapter;
import com.example.modelfashion.Model.FAQs;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.List;


public class FAQSActivity extends AppCompatActivity {
    ImageView img_back_faqs;
    RecyclerView rcl_faqs;
    List<FAQs> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqsactivity);
        init();
        setDateFAQs();

    }

    private void init(){
        img_back_faqs = findViewById(R.id.img_back_faqs);
        rcl_faqs = findViewById(R.id.rcl_faqs);
        list = new ArrayList<>();

        img_back_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setDateFAQs(){
        list.clear();
        list.add(new FAQs(getString(R.string.title),getString(R.string.content),false));
        list.add(new FAQs(getString(R.string.title1),getString(R.string.content1),false));
        list.add(new FAQs(getString(R.string.title2),getString(R.string.content2),false));
        list.add(new FAQs(getString(R.string.title3),getString(R.string.content3),false));
        list.add(new FAQs(getString(R.string.title4),getString(R.string.content4),false));
        list.add(new FAQs(getString(R.string.title5),getString(R.string.content5),false));
        FAQsAdapter faQsAdapter = new FAQsAdapter(FAQSActivity.this,list);
        rcl_faqs.setAdapter(faQsAdapter);

    }
}