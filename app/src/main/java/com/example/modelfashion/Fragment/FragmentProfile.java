package com.example.modelfashion.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Activity.ProfileActivity;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Activity.SignIn.SignUpActivity;
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.History.ViewHistory.HistoryActivity;
import com.example.modelfashion.Model.response.User.User;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;


public class FragmentProfile extends Fragment {
    PreferenceManager preferenceManager;
    TextView tv_name, tv_user, tv_login, btn_history, tv_signUp;
    LinearLayout btn_status, btn_status2, btn_status3;
    RoundedImageView img;
    LinearLayout layout_btn, layout_name;
    Boolean isLogin;
    String user_id;
    RelativeLayout layout_status_order, btn_profile, btn_cart, btn_logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        img = view.findViewById(R.id.img_frag_profile_avatar);
        tv_name = view.findViewById(R.id.tv_frag_Profile_Name);
        tv_user = view.findViewById(R.id.tv_frag_Profile_user);
        tv_login = view.findViewById(R.id.tv_frag_Profile_Login);
        tv_signUp = view.findViewById(R.id.tv_frag_Profile_Sign_Up);
        layout_btn = view.findViewById(R.id.layout_btn);
        layout_name = view.findViewById(R.id.layout_name);
        layout_status_order = view.findViewById(R.id.layout_status_order);
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

        isLogin = preferenceManager.getBoolean(Constants.KEY_CHECK_LOGIN);
        if (isLogin == false) {

            layout_btn.setVisibility(View.VISIBLE);
            img.setVisibility(View.GONE);
            layout_name.setVisibility(View.GONE);
        } else {
                    layout_btn.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    layout_name.setVisibility(View.VISIBLE);
                    tv_user.setText(preferenceManager.getString(Constants.KEY_TAI_KHOAN));
                    tv_name.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
                    Glide.with(getActivity())
                            .load(preferenceManager.getString(Constants.KEY_AVARTAR))
                            .into(img);
        }
    }

    //thêm chức năng vào các nút bấm
    private void setListener() {
        layout_name.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });
        img.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });
        tv_login.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SignInActivity.class);
            startActivity(intent);
        });

        tv_signUp.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SignUpActivity.class);
            startActivity(intent);
        });

        btn_logout.setOnClickListener(v -> {
            openDialog();
        });
        btn_profile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });
        btn_history.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            intent.putExtra("numberStatus", 4);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
        });
        btn_status.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            intent.putExtra("numberStatus", 1);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
        });

        btn_status2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            intent.putExtra("numberStatus", 2);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
        });
        btn_status3.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            intent.putExtra("numberStatus", 3);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set Title and Message:
        builder.setTitle("Đăng xuất")
                .setMessage("Bạn có muốn đăng xuất không?");

        //
        builder.setCancelable(true);
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.create().dismiss();
            }
        });
        // Create "Positive" button with OnClickListener.
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //progressLoadingCommon.showProgressLoading(getActivity());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.KEY_SAVE_USER, Context.MODE_MULTI_PROCESS);
                sharedPreferences.edit().remove(Constants.KEY_GET_USER).commit();
                preferenceManager.clear();
                preferenceManager.putBoolean(Constants.KEY_CHECK_LOGIN,false);
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                prefsEditor.putBoolean(Constants.KEY_CHECK_LOGIN, false);
                prefsEditor.apply();
                img.setImageResource(R.drawable.bg_gradient_blue);
                loadDetails();
                Intent intent = new Intent(getContext(),SignInActivity.class);
                startActivity(intent);
            }
        });
        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
        preferenceManager.putBoolean(Constants.KEY_LOGIN_STARUS, false);
    }
}

