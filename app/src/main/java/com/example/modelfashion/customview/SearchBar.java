package com.example.modelfashion.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
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

    private EditText edt;
    private ImageView imgClear, img_right_ic_search_view;

    public SearchBar(@NonNull Context context) {
        super(context);

    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = inflate(getContext(), R.layout.view_search, this);

        // get attributes
        @SuppressLint("CustomViewStyleable") TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MySearchView);
        Drawable rightIcon = attributes.getDrawable(R.styleable.MySearchView_right_drawable);
        String rightIconString = attributes.getString(R.styleable.MySearchView_right_drawable);

        // init view
         edt = view.findViewById(R.id.edt_search_view);
         imgClear = view.findViewById(R.id.img_clear_text);
        img_right_ic_search_view = view.findViewById(R.id.img_right_ic_search_view);

        imgClear.setOnClickListener(view1 -> {
            edt.setText("");
            imgClear.setVisibility(GONE);
            searchListener.onClearClick();
        });

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    imgClear.setVisibility(GONE);
                } else {
                    imgClear.setVisibility(VISIBLE);
                }
                searchListener.afterTextChanged(editable.toString());
            }
        });

        if (rightIcon != null) {
            img_right_ic_search_view.setImageDrawable(rightIcon);
            img_right_ic_search_view.setVisibility(View.VISIBLE);
            img_right_ic_search_view.setOnClickListener(v -> {
                searchListener.rightIconClick();
            });
        }

        attributes.recycle();
    }

    public void clearSearchContent(){
        imgClear.setVisibility(GONE);
        edt.setText("");
    }

    private SearchListener searchListener;

    public void onSearchBarClick(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public interface SearchListener {
        void onClearClick();

        void afterTextChanged(String content);

        void rightIconClick();
    }

}