package com.example.modelfashion.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.ProfileActivity;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Activity.SignIn.SignUpActivity;
import com.example.modelfashion.Common.ProgressLoadingCommon;
import com.example.modelfashion.History.ViewHistory.HistoryActivity;
import com.example.modelfashion.Model.response.User.User;
import com.example.modelfashion.OrderStatus.ViewOrderStatus.OrderStatusActivity;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.Utility.PreferenceManager;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import bolts.Bolts;


public class FragmentProfile extends Fragment {
    PreferenceManager preferenceManager;
    TextView tv_name, tv_user, tv_login, btn_profile, btn_cart, btn_status, btn_history, btn_logout, tv_signUp;
    RoundedImageView img;
    TextView btn_feedback;
    LinearLayout ll_login;
    Boolean isLogin;
    ProgressLoadingCommon progressLoadingCommon;
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
        btn_feedback = view.findViewById(R.id.btn_feedback);
        progressLoadingCommon = new ProgressLoadingCommon();
        preferenceManager = new PreferenceManager(getContext());
        loadDetails();
        setListener();

        return view;
    }

    //load dữ liệu lên màn hình
    private void loadDetails() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.KEY_SAVE_USER, Context.MODE_MULTI_PROCESS);
        isLogin = sharedPreferences.getBoolean(Constants.KEY_CHECK_LOGIN, true);
        if (isLogin == false) {
            User user = new User("", "", "", "", "", "", "");
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putString("user", user.toString());
            prefsEditor.apply();

            tv_user.setVisibility(View.GONE);
            tv_name.setVisibility(View.GONE);
            ll_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.GONE);
        } else {
            if (sharedPreferences.contains(Constants.KEY_GET_USER)) {
                String userData = sharedPreferences.getString(Constants.KEY_GET_USER, "");

                try {
                    JSONObject obj = new JSONObject(userData);

                    tv_user.setVisibility(View.VISIBLE);
                    tv_name.setVisibility(View.GONE);
                    ll_login.setVisibility(View.GONE);
                    btn_logout.setVisibility(View.VISIBLE);

                    tv_user.setText(obj.getString(Constants.KEY_TAI_KHOAN));
                    Glide.with(getActivity())
                            .load(obj.get(Constants.KEY_AVARTAR))
                            .into(img);

                    Log.d("My App", obj.toString());

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + userData + "\"");
                }
            }
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
            progressLoadingCommon.showProgressLoading(getActivity());
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.KEY_SAVE_USER, Context.MODE_MULTI_PROCESS);
            sharedPreferences.edit().remove(Constants.KEY_GET_USER).commit();
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putBoolean(Constants.KEY_CHECK_LOGIN, false);
            prefsEditor.apply();
            img.setImageResource(R.drawable.bg_gradient_blue);
            loadDetails();

        });
        btn_profile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });
        btn_history.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            startActivity(intent);
        });
        btn_status.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OrderStatusActivity.class);
            startActivity(intent);
        });

        btn_feedback.setOnClickListener(v -> {
            loadDialogFeedback();
        });
    }

    private void loadDialogFeedback() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_feedback);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText edt_title = dialog.findViewById(R.id.edt_title);
        EditText edt_content = dialog.findViewById(R.id.edt_content);
        TextView btn_send = dialog.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, content;
                title = edt_title.getText().toString().trim();
                content = edt_content.getText().toString().trim();
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(getContext(), "Tiêu đề và nội dung không được trống", Toast.LENGTH_SHORT).show();
                } else {
                    String uriText = "mailto:" + "khiemnxph10098@fpt.edt.vn" +
                            "?subject=" + title +
                            "&body=" + content;
                    Uri uri = Uri.parse(uriText);
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                    sendIntent.setData(uri);
                    startActivity(Intent.createChooser(sendIntent, "Send Email"));
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDetails();
    }
}