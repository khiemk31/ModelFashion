package com.example.modelfashion.Adapter.category;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Model.MyStyle;
import com.example.modelfashion.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.CategoryHolder>{
    Context context;
    List<MyStyle> myCategories;

    public MyCategoryAdapter(Context context, List<MyStyle> myCategories) {
        this.context = context;
        this.myCategories = myCategories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mycategory,parent,false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        MyStyle myCategory = myCategories.get(position);

        Glide.with(context).load(myCategory.getImgCategory()).into(holder.img_category);
        holder.tv_style.setText(myCategory.getNameCategory());
        holder.itemView.setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    public int getItemCount() {
        return myCategories.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{
        ImageView img_category;
        TextView tv_style;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            this.img_category = itemView.findViewById(R.id.img_category);
            this.tv_style = itemView.findViewById(R.id.tv_style);
        }
    }
}
