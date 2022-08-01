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
    private RelativeLayout btn_contact,btn_cancleBill;
    private LinearLayout detai_FAQS_contact,detai_FAQS_cancleBill;
    private int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqsactivity);
        btn_contact = findViewById(R.id.btn_act_faqs_Contact);
        btn_cancleBill= findViewById(R.id.btn_act_faqs_cancelBill);
        detai_FAQS_contact = findViewById(R.id.detai_FAQS_contact);
        detai_FAQS_cancleBill=findViewById(R.id.detai_FAQS_cancelBill);
        //https://github.com/Ramotion/folding-cell-android
        setListener();
    }

    public void setListener() {
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_to_bottom);
        Animation animation_rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        btn_contact.setOnClickListener(v -> {
            if (temp == 0) {
                findViewById(R.id.next_FAQS_contact).startAnimation(animation_rotate);
                detai_FAQS_contact.setVisibility(View.VISIBLE);
                detai_FAQS_contact.startAnimation(animation2);
                temp++;
            } else {
                detai_FAQS_contact.setVisibility(View.GONE);
                temp--;
            }
        });
        btn_cancleBill.setOnClickListener(v -> {
            if (temp == 0) {
                findViewById(R.id.next_FAQS_cancelBill).startAnimation(animation_rotate);
                detai_FAQS_cancleBill.setVisibility(View.VISIBLE);
                detai_FAQS_cancleBill.startAnimation(animation2);
                temp++;
            } else {
                detai_FAQS_cancleBill.setVisibility(View.GONE);
                temp--;
            }
        });
    }
}