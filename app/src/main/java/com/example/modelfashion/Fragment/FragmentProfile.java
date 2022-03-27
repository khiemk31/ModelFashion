package com.example.modelfashion.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modelfashion.Activity.ProfileActivity;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Activity.SignIn.SignUpActivity;
import com.example.modelfashion.History.ViewHistory.HistoryActivity;
import com.example.modelfashion.OrderStatus.ViewOrderStatus.OrderStatusActivity;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.makeramen.roundedimageview.RoundedImageView;


public class FragmentProfile extends Fragment {
    PreferenceManager preferenceManager;
    TextView tv_name, tv_user, tv_login, btn_profile, btn_cart, btn_history, btn_logout, tv_signUp;
    LinearLayout btn_status,btn_status2,btn_status3;
    RoundedImageView img;
    TextView btn_feedback;
    LinearLayout ll_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        img = view.findViewById(R.id.img_frag_profile_avatar);
        tv_name = view.findViewById(R.id.tv_frag_Profile_Name);
        tv_user = view.findViewById(R.id.tv_frag_Profile_user);
        tv_login = view.findViewById(R.id.tv_frag_Profile_Login);
        tv_signUp = view.findViewById(R.id.tv_frag_Profile_Sign_Up);
        ll_login = view.findViewById(R.id.ll_login);
        btn_profile = view.findViewById(R.id.btn_frag_Profile_Profile);
        btn_cart = view.findViewById(R.id.btn_frag_Profile_cart);
        btn_history = view.findViewById(R.id.btn_frag_Profile_history);
        btn_logout = view.findViewById(R.id.btn_frag_Profile_Logout);
        btn_status = view.findViewById(R.id.btn_frag_Profile_status);
        btn_status2 = view.findViewById(R.id.btn_frag_Profile_status2);
        btn_status3 = view.findViewById(R.id.btn_frag_Profile_status3);

        preferenceManager = new PreferenceManager(getContext());
        loadDetails();
        setListener();

        return view;
    }

    //load dữ liệu lên màn hình
    private void loadDetails() {
        if (preferenceManager.getString(Constants.KEY_PROFILE_USER).equals("")) {
            tv_user.setVisibility(View.GONE);
            tv_name.setVisibility(View.GONE);
            ll_login.setVisibility(View.VISIBLE);
        } else {
            tv_user.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            ll_login.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);
            tv_signUp.setVisibility(View.VISIBLE);
        }
    }

    //thêm chức năng vào các nút bấm
    private void setListener() {
        tv_login.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SignInActivity.class);
            startActivity(intent);
        });

        tv_signUp.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SignUpActivity.class);
            startActivity(intent);
        });

        btn_logout.setOnClickListener(v -> {
            preferenceManager.clear();
            Intent intent = new Intent(getContext(), SignInActivity.class);
            startActivity(intent);
        });
        btn_profile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });
        btn_history.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            intent.putExtra("numberStatus",4);
            startActivity(intent);
        });
        btn_status.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            intent.putExtra("numberStatus",1);
            startActivity(intent);
        });
        btn_status2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            intent.putExtra("numberStatus",2);
            startActivity(intent);
        });
        btn_status3.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            intent.putExtra("numberStatus",3);
            startActivity(intent);
        });


    }


}