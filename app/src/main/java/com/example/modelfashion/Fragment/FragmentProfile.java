package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_ADDRESS;
import static com.example.modelfashion.Utility.Constants.KEY_AVARTAR;
import static com.example.modelfashion.Utility.Constants.KEY_BIRTHDAY;
import static com.example.modelfashion.Utility.Constants.KEY_CHECK_LOGIN;
import static com.example.modelfashion.Utility.Constants.KEY_FULL_NAME;
import static com.example.modelfashion.Utility.Constants.KEY_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PHONE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.CartActivity;
import com.example.modelfashion.Activity.FAQSActivity;
import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Activity.ProfileActivity;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Activity.SignIn.SignUpActivity;
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.History.ViewHistory.HistoryActivity;
import com.example.modelfashion.Model.response.User.CheckUserActiveRequest;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.example.modelfashion.Utility.Utils;
import com.example.modelfashion.network.Repository;
import com.makeramen.roundedimageview.RoundedImageView;

import io.reactivex.disposables.CompositeDisposable;


public class FragmentProfile extends Fragment {
    PreferenceManager preferenceManager;
    TextView tv_name, tv_user, tv_login, btn_history, tv_signUp;
    LinearLayout btn_status, btn_status2, btn_status3;
    RoundedImageView img;
    LinearLayout layout_btn, layout_name;
    Boolean isLogin;
    String user_id = "1";
    RelativeLayout rl_logout;
    RelativeLayout layout_status_order, btn_profile, btn_cart, btn_logout,btn_frag_Profile_FAQS;
    ProgressLoadingCommon progressLoadingCommon;
    CompositeDisposable disposable = new CompositeDisposable();
    public static FragmentProfile newInstance(String text) {

        FragmentProfile f = new FragmentProfile();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

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
        btn_logout = view.findViewById(R.id.rl_logout);
        btn_frag_Profile_FAQS = view.findViewById(R.id.btn_frag_Profile_FAQS);
        btn_status = view.findViewById(R.id.btn_frag_Profile_status);
        btn_status2 = view.findViewById(R.id.btn_frag_Profile_status2);
        btn_status3 = view.findViewById(R.id.btn_frag_Profile_status3);
        preferenceManager = new PreferenceManager(requireContext());

        loadDetails();
        setListener();
        return view;
    }

    //load dữ liệu lên màn hình
    private void loadDetails() {
        if (!preferenceManager.getBoolean(KEY_CHECK_LOGIN)) {
            btn_logout.setVisibility(View.GONE);
            layout_btn.setVisibility(View.VISIBLE);
            layout_name.setVisibility(View.GONE);
            img.setImageResource(R.drawable.bg_gradient_blue);
        } else {
            btn_logout.setVisibility(View.VISIBLE);
            layout_btn.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            layout_name.setVisibility(View.VISIBLE);
            tv_user.setText(preferenceManager.getString(KEY_PHONE));
            tv_name.setText(preferenceManager.getString(KEY_FULL_NAME));
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
            if (preferenceManager.getBoolean(KEY_CHECK_LOGIN)) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), SignInActivity.class);
                startActivity(intent);
            }

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
            showDialogLogout();
        });
        btn_profile.setOnClickListener(v -> {
            if (!preferenceManager.getBoolean(KEY_CHECK_LOGIN))  {
                showDialogLogIn();
                // Toast.makeText(this, "Bạn chưa thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        btn_history.setOnClickListener(v -> {
            isLogin = preferenceManager.getBoolean(Constants.KEY_CHECK_LOGIN);
            if (isLogin == false) {
                showDialogLogIn();
                // Toast.makeText(this, "Bạn chưa thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            } else {
                user_id = preferenceManager.getString(Constants.KEY_ID);
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("numberStatus", 4);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
        btn_status.setOnClickListener(v -> {
            isLogin = preferenceManager.getBoolean(Constants.KEY_CHECK_LOGIN);
            if (isLogin == false) {
                showDialogLogIn();
                // Toast.makeText(this, "Bạn chưa thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            } else {
                user_id = preferenceManager.getString(Constants.KEY_ID);
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("numberStatus", 1);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        btn_status2.setOnClickListener(v -> {
            isLogin = preferenceManager.getBoolean(Constants.KEY_CHECK_LOGIN);
            if (isLogin == false) {
                showDialogLogIn();
                // Toast.makeText(this, "Bạn chưa thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            } else {
                user_id = preferenceManager.getString(Constants.KEY_ID);
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("numberStatus", 2);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
        btn_status3.setOnClickListener(v -> {
            isLogin = preferenceManager.getBoolean(Constants.KEY_CHECK_LOGIN);
            if (isLogin == false) {
                showDialogLogIn();
                // Toast.makeText(this, "Bạn chưa thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            } else {
                user_id = preferenceManager.getString(Constants.KEY_ID);
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("numberStatus", 3);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
        btn_cart.setOnClickListener(v -> {
            isLogin = preferenceManager.getBoolean(Constants.KEY_CHECK_LOGIN);
            if (isLogin == false) {
                showDialogLogIn();
                // Toast.makeText(this, "Bạn chưa thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            } else {
                user_id = preferenceManager.getString(Constants.KEY_ID);
                Intent intent = new Intent(getContext(), CartActivity.class);
                intent.putExtra("use_id", user_id);
                startActivity(intent);
            }
        });
        btn_frag_Profile_FAQS.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), FAQSActivity.class));
        });
    }

    private void showDialogLogout() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_yes_logout, tv_no_logout;
        tv_yes_logout = dialog.findViewById(R.id.tv_yes_logout);
        tv_no_logout = dialog.findViewById(R.id.tv_no_logout);
        tv_no_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //            progressLoadingCommon.showProgressLoading(getActivity());
                preferenceManager.putBoolean(KEY_CHECK_LOGIN, false);
                preferenceManager.putString(KEY_AVARTAR, "");
                preferenceManager.putString(KEY_FULL_NAME, "");
                preferenceManager.putString(KEY_PHONE, "");
                preferenceManager.putString(KEY_ADDRESS, "");
                preferenceManager.putString(KEY_BIRTHDAY, "");
                preferenceManager.putString(KEY_ID, "");

                img.setImageResource(R.drawable.bg_gradient_blue);
                loadDetails();
                dialog.dismiss();
                getActivity().finish();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);


            }
        });

        dialog.show();
    }

    private void showDialogLogIn() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_quest_login);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_yes_login, tv_no_login;
        tv_yes_login = dialog.findViewById(R.id.tv_yes_login);
        tv_no_login = dialog.findViewById(R.id.tv_no_login);
        tv_no_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignInActivity.class));
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        tv_name.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
        Glide.with(getActivity())
                .load(preferenceManager.getString(Constants.KEY_AVARTAR))
                .into(img);
        loadDetails();
    }
}

