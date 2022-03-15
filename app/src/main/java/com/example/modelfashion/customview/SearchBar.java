package com.example.modelfashion.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.modelfashion.Fragment.CategoryFragment;
import com.example.modelfashion.R;

public class SearchBar extends ConstraintLayout {

    public SearchBar(@NonNull Context context) {
        super(context);

    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = inflate(getContext(), R.layout.view_search, this);

        // get attributes
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MySearchView);
        Drawable rightIcon = attributes.getDrawable(R.styleable.MySearchView_right_drawable);
        String rightIconString = attributes.getString(R.styleable.MySearchView_right_drawable);

        // init view
        ImageView icRight = view.findViewById(R.id.img_right_ic_search_view);
        EditText edt = view.findViewById(R.id.edt_search_view);


        if (rightIcon != null) {
            icRight.setImageDrawable(rightIcon);
        }
        if (rightIconString.equals("none")) {
            icRight.setVisibility(GONE);
        }

        icRight.setOnClickListener(view1 -> {
            searchListener.onSearchClick(edt.getText().toString());
        });


        attributes.recycle();
    }

    private SearchListener searchListener;

    public void onSearchBarClick(SearchListener searchListener){
        this.searchListener = searchListener;
    }

    public interface SearchListener{
        void onSearchClick(String contentSearch);
    }

}