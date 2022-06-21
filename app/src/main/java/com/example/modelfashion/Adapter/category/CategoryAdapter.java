package com.example.modelfashion.Adapter.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelfashion.Model.response.category.MyCategory;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public CategoryAdapter() {
    }

    private List<MyCategory> listCategory = new ArrayList<>();;
    private ItemClickListener mClickListener;
    private int index;

    public void setListCategory(List<MyCategory> list) {
        this.listCategory.clear();
        this.listCategory = list;
        notifyDataSetChanged();
    }

    public String getCategoryName(int position) {return listCategory.get(position).getCategoryName();}

    public List<MyCategory> getListCategory() {
        return this.listCategory;
    }

    public String getCategoryId(int position) {
        return listCategory.get(position).getCategoryId();
    }

    public MyCategory getMyCategory(int position) {
        return listCategory.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_left_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCategory category = listCategory.get(position);
        holder.myTextView.setText(category.getCategoryName());
        if (position == index) {
            holder.myTextView.setBackground(holder.myTextView.getContext().getResources().getDrawable(R.drawable.bg_item_price_select));
//            holder.myTextView.setTextColor(holder.myTextView.getContext().getResources().getColor(R.color.white));
        }else {
            holder.myTextView.setBackground(holder.myTextView.getContext().getResources().getDrawable(R.drawable.bg_item_price));
//            holder.myTextView.setTextColor(holder.myTextView.getContext().getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tv_category_name);
            myTextView.setOnClickListener(view -> {
                mClickListener.onItemClick(view, getAdapterPosition());
            });
        }
    }

    public void highLightSelectedItem(int position) {
        index = position;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
