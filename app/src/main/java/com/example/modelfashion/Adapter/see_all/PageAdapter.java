package com.example.modelfashion.Adapter.see_all;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelfashion.R;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.ViewHolder> {

    private int index = 0;

    private int pageCount = 1;

    public void setPageCount(int pages) {
        this.pageCount = 1;
        this.pageCount = pages;
        notifyDataSetChanged();
    }

    public void clearPageCount() {
        this.pageCount = 0;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_page.setText((position+1) + "");
        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClick(position);
        });

        if (index == position) {
            holder.csl_container_page.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.bg_page_select));
        } else  {
            holder.csl_container_page.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.bg_page_unselect));
        }

    }

    @Override
    public int getItemCount() {
        return pageCount;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_page;
        ConstraintLayout csl_container_page;

        ViewHolder(View itemView) {
            super(itemView);
            tv_page = itemView.findViewById(R.id.tv_page);
            csl_container_page = itemView.findViewById(R.id.csl_container_page);

        }
    }

    public void setColor(int position) {
        this.index = position;
        notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void onItemClick(int position);
    }

    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
