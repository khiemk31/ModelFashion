package com.example.modelfashion.Adapter.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelfashion.R;
import com.example.modelfashion.Model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public CategoryAdapter() {
    }

    private List<Category> listCategory;
    private ItemClickListener mClickListener;

    public void setListCategory(List<Category> list) {
        this.listCategory = list;
        notifyDataSetChanged();
    }

    public List<Category> getListCategory() {
        return this.listCategory;
    }

    public Category getCategory(int position) {
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
        Category category = listCategory.get(position);
        holder.myTextView.setText(category.getCategoryName());
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
            itemView.setOnClickListener(view -> {
                mClickListener.onItemClick(view, getAdapterPosition());
            });
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
