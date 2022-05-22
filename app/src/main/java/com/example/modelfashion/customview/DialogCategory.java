package com.example.modelfashion.customview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelfashion.Adapter.category.CategoryAdapter;
import com.example.modelfashion.Model.response.category.MyCategory;
import com.example.modelfashion.R;

import java.util.ArrayList;
import java.util.List;

public class DialogCategory extends DialogFragment {

    private CategoryAdapter categoryAdapter;
    private List<MyCategory> categoryList = new ArrayList<>();
    private int currentPosition = 0;
    private int currentPriceRange = 100;
    private int currentSortOrder = 100;

    public DialogCategory(List<MyCategory> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public void onStart() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_filter_category, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryAdapter = new CategoryAdapter();

        RecyclerView rcv_category_f = view.findViewById(R.id.rcv_category_f);
        TextView tv_price_100_500 = view.findViewById(R.id.tv_price_100_500);
        TextView tv_price_500_1tr = view.findViewById(R.id.tv_price_500_1tr);
        TextView tv_price_more_than_1tr = view.findViewById(R.id.tv_price_more_than_1tr);

        TextView tv_sort_order_asc = view.findViewById(R.id.tv_sort_order_asc);
        TextView tv_sort_order_desc = view.findViewById(R.id.tv_sort_order_desc);

        TextView tv_close_filterr = view.findViewById(R.id.tv_close_filterr);
        TextView tv_confirm_filterr = view.findViewById(R.id.tv_confirm_filterr);
        TextView tv_no_filterr = view.findViewById(R.id.tv_no_filterr);

        rcv_category_f.setAdapter(categoryAdapter);
        categoryAdapter.setListCategory(this.categoryList);

        tv_close_filterr.setOnClickListener(view1 -> {
            dismiss();
        });
        tv_no_filterr.setOnClickListener(view1 -> {
            dialogClickInterface.viewAllNoFilter();
            dismiss();
        });
        tv_confirm_filterr.setOnClickListener(view1 -> {
            String cateName = categoryAdapter.getCategoryName(currentPosition);
            long price1 = 0;
            long price2 = 0;
            if (currentPriceRange == 0) { // 100 - 500
                price1 = 100000;
                price2 = 500000;
            } else if (currentPriceRange == 1) { // 500 - 1tr
                price1 = 500000;
                price2 = 1000000;
            } else if (currentPriceRange == 2) {   // > 1tr
                price1 = 1000000;
                price2 = 10000000;
            }

            String sortOrder = "";
            if (currentSortOrder == 0) {
                sortOrder = "ASC";
            } else if (currentSortOrder == 1) {
                sortOrder = "DESC";
            }

            if (checkValidateOptions(cateName, price1, price2, sortOrder)) {
                dialogClickInterface.confirmClickListener(cateName, price1, price2, sortOrder);
                dismiss();
            } else if (((price1 == 0 && price2 == 0) && !sortOrder.equals(""))
            || (!(price1 == 0 && price2 == 0) && sortOrder.equals(""))){
                Toast.makeText(requireContext(), "Hãy chọn khoảng giá và thứ tự", Toast.LENGTH_SHORT).show();
            } else {
                dismiss();
                dialogClickInterface.viewProductByCategoryOnly(categoryAdapter.getCategoryId(currentPosition));
            }
        });

        initSortOrderListener(tv_sort_order_desc, tv_sort_order_asc);
        initPriceRangeListner(tv_price_100_500, tv_price_500_1tr, tv_price_more_than_1tr);
        initListener();

    }

    private boolean checkValidateOptions(String cateName, long price1, long price2, String sortOrder) {
        if (price1 == 0 && price2 == 0) {
            return false;
        }

        if (sortOrder.equals("")) {
            return false;
        }
        return true;
    }

    private void initSortOrderListener(TextView tv_sort_order_asc, TextView tv_sort_order_desc) {
        tv_sort_order_desc.setOnClickListener(view -> {
            tv_sort_order_desc.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price_select));
            tv_sort_order_asc.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price));
            currentSortOrder = 1;
        });
        tv_sort_order_asc.setOnClickListener(view -> {
            currentSortOrder = 0;
            tv_sort_order_asc.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price_select));
            tv_sort_order_desc.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price));
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initPriceRangeListner(TextView tv_price_100_500, TextView tv_price_500_1tr, TextView tv_price_more_than_1tr) {
        tv_price_100_500.setOnClickListener(view -> {
            currentPriceRange = 0;
            tv_price_100_500.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price_select));
            tv_price_500_1tr.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price));
            tv_price_more_than_1tr.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price));
        });

        tv_price_500_1tr.setOnClickListener(view -> {
            currentPriceRange = 1;
            tv_price_100_500.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price));
            tv_price_500_1tr.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price_select));
            tv_price_more_than_1tr.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price));
        });

        tv_price_more_than_1tr.setOnClickListener(view -> {
            currentPriceRange = 2;
            tv_price_100_500.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price));
            tv_price_500_1tr.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price));
            tv_price_more_than_1tr.setBackground(requireContext().getResources().getDrawable(R.drawable.bg_item_price_select));
        });
    }

    private void initListener() {
        categoryAdapter.setClickListener((view, position) -> {
            categoryAdapter.highLightSelectedItem(position);
            currentPosition = position;
        });


    }

    private DialogClickInterface dialogClickInterface;

    public void setClickListener(DialogClickInterface dialogClickInterface) {
        this.dialogClickInterface = dialogClickInterface;
    }

    public interface DialogClickInterface {
        void confirmClickListener(String categoryName, long price1, long price2, String sortOrder);
        void viewProductByCategoryOnly(String categoryId);
        void viewAllNoFilter();
    }

}
