package com.example.modelfashion.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
            holder.content_faqs.setText(faQs.getContent());
            if (faQs.isOpen()){
                Glide.with(context).load(R.drawable.ic_open_faqs).into(holder.open_close_faqs);
                holder.content_faqs.setVisibility(View.VISIBLE);
            }else {
                Glide.with(context).load(R.drawable.ic_close_faqs).into(holder.open_close_faqs);
                holder.content_faqs.setVisibility(View.GONE);
            }
            holder.open_close_faqs.setOnClickListener(new View.OnClickListener() {
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
