package com.example.modelfashion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelfashion.Activity.NotifiDetailActivity;
import com.example.modelfashion.Model.Notifi;
import com.example.modelfashion.R;

import java.util.List;

public class NotifiAdapter extends RecyclerView.Adapter<NotifiAdapter.myViewHolder> {
    Context context;
    List<Notifi> notifiList;

    public NotifiAdapter(Context context, List<Notifi> notifiList) {
        this.context = context;
        this.notifiList = notifiList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifi_app,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Notifi notifi = notifiList.get(position);
        holder.tv_title_notifi.setText(notifi.getTitleNotifi());
        holder.tv_content_notifi.setText(notifi.getContentNotifi());
        holder.tv_date_notifi.setText(notifi.getDateNotifi());
        holder.ll_item_notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotifiDetailActivity.class);
                intent.putExtra("title",notifi.getTitleNotifi());
                intent.putExtra("content",notifi.getContentNotifi());
                intent.putExtra("date",notifi.getDateNotifi());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifiList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title_notifi,tv_content_notifi,tv_date_notifi;
        ImageView img_menu_notifi;
        LinearLayout ll_item_notifi;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title_notifi = itemView.findViewById(R.id.tv_title_notifi);
            tv_content_notifi = itemView.findViewById(R.id.tv_content_notifi);
            tv_date_notifi = itemView.findViewById(R.id.tv_date_notifi);
            img_menu_notifi = itemView.findViewById(R.id.img_menu_notifi);
            ll_item_notifi = itemView.findViewById(R.id.ll_item_notifi);
        }
    }
}
