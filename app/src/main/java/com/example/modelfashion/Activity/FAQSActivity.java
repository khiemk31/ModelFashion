package com.example.modelfashion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.modelfashion.R;


public class FAQSActivity extends AppCompatActivity {
    private RelativeLayout btn_contact;
    private LinearLayout detai_FAQS_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqsactivity);
        btn_contact= findViewById(R.id.btn_act_faqs_Contact);
        detai_FAQS_contact= findViewById(R.id.detai_FAQS_contact);

        //https://github.com/Ramotion/folding-cell-android
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_to_bottom);
        btn_contact.setOnClickListener(v -> {
                    detai_FAQS_contact.setVisibility(View.VISIBLE);
                    detai_FAQS_contact.startAnimation(animation2);
        });
    }
}