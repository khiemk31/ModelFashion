package com.example.modelfashion.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Model.FAQs;
import com.example.modelfashion.R;

import java.util.List;

public class FAQsAdapter extends RecyclerView.Adapter<FAQsAdapter.FAQsHolder> {
    Context context;
    List<FAQs> list;

    public FAQsAdapter(Context context, List<FAQs> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FAQsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_faqs,parent,false);
        return new FAQsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQsHolder holder, @SuppressLint("RecyclerView") int position) {
            FAQs faQs = list.get(position);
            holder.title_faqs.setText(faQs.getTitle());

            if(position==0){
                String phone = context.getString(R.string.phone);
                String email = context.getString(R.string.emailFAQs);
                int startPhone = -1;
                int startEmail = -1;
                ClickableSpan clickableSpanPhone = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        context.startActivity(intent);
                        view.invalidate();
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        if (holder.content_faqs.isPressed()) {
                            ds.setColor(Color.BLUE);
                        } else {
                            ds.setColor(Color.BLUE);
                        }
                        holder.content_faqs.invalidate();
                    }
                };
                ClickableSpan clickableSpanEmail = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        String uriText = "mailto:" +email+
                                "?subject=" + "" +
                                "&body=" +" ";
                        Uri uri = Uri.parse(uriText);
                        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                        sendIntent.setData(uri);
                        context.startActivity(Intent.createChooser(sendIntent, "Send Email"));
                        view.invalidate();
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        if (holder.content_faqs.isPressed()) {
                            ds.setColor(Color.BLUE);
                        } else {
                            ds.setColor(Color.BLUE);
                        }
                        holder.content_faqs.invalidate();
                    }
                };
                holder.content_faqs.setHighlightColor(Color.TRANSPARENT);
                Spannable spannablePhone = new SpannableString(faQs.getContent());
                startPhone = faQs.getContent().toString().indexOf(phone,startPhone+1);
                startEmail = faQs.getContent().toString().indexOf(email,startEmail+1);
                spannablePhone.setSpan(clickableSpanPhone,startPhone,startPhone+phone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannablePhone.setSpan(clickableSpanEmail,startEmail,startEmail+email.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.content_faqs.setText(spannablePhone,TextView.BufferType.SPANNABLE);
                holder.content_faqs.setMovementMethod(LinkMovementMethod.getInstance());
            }
            if (faQs.isOpen()){
                Glide.with(context).load(R.drawable.ic_open_faqs).into(holder.open_close_faqs);
                holder.content_faqs.setVisibility(View.VISIBLE);
            }else {
                Glide.with(context).load(R.drawable.ic_close_faqs).into(holder.open_close_faqs);
                holder.content_faqs.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateFAQs(position);
                }
            });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateFAQs(int index){
        for (int i = 0 ;i<list.size();i++){
            if(i==index){
                if(list.get(i).isOpen()){
                    list.get(i).setOpen(false);
                }else {
                    list.get(i).setOpen(true);
                }
            }else {
                list.get(i).setOpen(false);
            }
        }
        notifyDataSetChanged();
    }

    public class FAQsHolder extends RecyclerView.ViewHolder{
        ImageView open_close_faqs;
        TextView title_faqs,content_faqs;

        public FAQsHolder(@NonNull View itemView) {
            super(itemView);
            open_close_faqs = itemView.findViewById(R.id.open_close_faqs);
            title_faqs = itemView.findViewById(R.id.title_faqs);
            content_faqs = itemView.findViewById(R.id.content_faqs);

        }
    }
}
