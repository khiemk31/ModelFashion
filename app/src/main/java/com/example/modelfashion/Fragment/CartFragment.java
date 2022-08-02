package com.example.modelfashion.Fragment;

import static com.example.modelfashion.Utility.Constants.KEY_CHECK_LOGIN;
import static com.example.modelfashion.Utility.Constants.KEY_ID;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.modelfashion.Activity.CartActivity;
import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Adapter.cart.CartAdapter;
import com.example.modelfashion.History.ApiHistory.ApiHistory;
import com.example.modelfashion.Model.request.CreateBillRequest;
import com.example.modelfashion.Model.response.User.CheckUserActiveRequest;
import com.example.modelfashion.Model.response.bill.Address;
import com.example.modelfashion.Model.response.bill.UpdateAdress;
import com.example.modelfashion.Model.response.bill.UserID;
import com.example.modelfashion.Model.response.my_product.CartProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.Sizes;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.PreferenceManager;
import com.example.modelfashion.Utility.Utils;
import com.example.modelfashion.database.AppDatabase;
import com.example.modelfashion.database.MyProductCart;
import com.example.modelfashion.network.Repository;
import com.example.modelfashion.rx.RxBus;
import com.example.modelfashion.rx.RxEvent;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;

public class CartFragment extends Fragment {
    private View initView;
    private RecyclerView recyclerView;
    private ArrayList<MyProduct> arrProduct = new ArrayList<>();
    private ArrayList<CartProduct> arrCart = new ArrayList<>();
    private ArrayList<Sizes> arrSize = new ArrayList<>();
    private ArrayList<String> arr_size_id = new ArrayList<>();
    private ArrayList<String> arr_product_name = new ArrayList<>();
    private String user_id, total_money;
    private TextView tvTotal,tv_address;
    public static Button btn_payment;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    private Boolean check_load_successful = false;
    private CompositeDisposable disposable = new CompositeDisposable();
    private PreferenceManager sharedPref;
    private Repository repository;
    private RadioButton rdo_cash,rdo_momo;
    private int payment_methods = 0;
    private String addRess = "";
    private TextView btn_change_address;


    CartAdapter adapter = new CartAdapter();

    //momo
    private Integer amount = 100000;
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "FShop";
    private String merchantCode = "MOMOLWUA20220517";
    private String merchantNameLabel = "FPT plytechnich";
    private String description = "Thanh toán đơn hàng Model Fashion";

    private BroadcastReceiver broadCastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            String check = intent.getStringExtra("action");
            if (check.matches("addbill")){
                addBill();
            }
        }
    };

    public static CartFragment newInstance(String text) {

        CartFragment f = new CartFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }






    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView = inflater.inflate(R.layout.fragment_cart, container, false);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(broadCastReceiver,new IntentFilter("send_data_to_fragment"));

        recyclerView = initView.findViewById(R.id.list_product_cart);
        tvTotal = initView.findViewById(R.id.total_money);
        btn_payment = initView.findViewById(R.id.btn_payment);
        refreshLayout = initView.findViewById(R.id.refresh_layout);
        progressBar = initView.findViewById(R.id.progress_bar);
        rdo_cash = initView.findViewById(R.id.rdo_cash);
        rdo_momo = initView.findViewById(R.id.rdo_momo);
        tv_address = initView.findViewById(R.id.tv_address);
        btn_change_address = initView.findViewById(R.id.btn_change_address);
        sharedPref = new PreferenceManager(requireContext());
        repository = new Repository(requireContext());

        rxListener();

        rdo_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_methods = 0;
            }
        });
        rdo_momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_methods =1;
            }
        });

        setAdapter();
        getProductInCart();

        btn_payment.setOnClickListener(v -> {

            // check dang nhap
            String id = sharedPref.getString(KEY_ID);
            Log.d("ahuhu", "sharedPref.getString(KEY_ID): " + id);
            if (!sharedPref.getBoolean(KEY_CHECK_LOGIN)) {
                showDialogRequireLogin();
            } else {
                if (adapter.getItemCount() == 0) {
                    Toast.makeText(requireContext(), "Trong giỏ hàng không có sản phẩm", Toast.LENGTH_SHORT).show();
                }else if(addRess ==""){
                    Toast.makeText(requireContext(), "Chưa có địa chỉ", Toast.LENGTH_SHORT).show();
                }else {
//                    requestPayment();
                    createBill();
                }
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            getProductInCart();
            tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
        });
        btn_change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdress();
            }
        });
        if (sharedPref.getBoolean(KEY_CHECK_LOGIN)) {
             getAddress();
        }
        return initView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void rxListener() {
        disposable.add(
                RxBus.listen(RxEvent.addItemToCart.getClass())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(okResponse -> {
                            // refresh
                            getProductInCart();
                            tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
                        }, throwable -> {
                            Log.d("ahuhu", "listene: error: " + throwable.getMessage());
                        })
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver((BroadcastReceiver) broadCastReceiver);
    }

    private void showDialogAdress(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_update_address);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edt_address = dialog.findViewById(R.id.edt_address);
        TextView tv_no_update = dialog.findViewById(R.id.tv_no_update);
        TextView tv_yes_update = dialog.findViewById(R.id.tv_yes_update);
        edt_address.setText(addRess);
        tv_no_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_address.getText().toString().isEmpty()){
                    Toast.makeText(requireContext(),"Bạn chưa nhập địa chỉ",Toast.LENGTH_SHORT).show();
                }else {
                    addRess = edt_address.getText().toString();
                    tv_address.setText(edt_address.getText());
                    //updateAddress();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    private void updateAddress(){
        UpdateAdress updateAdress = new UpdateAdress("1",addRess);
        ApiHistory.API_HISTORY.updateAddress(updateAdress).enqueue(new Callback<UpdateAdress>() {
            @Override
            public void onResponse(Call<UpdateAdress> call, Response<UpdateAdress> response) {

            }

            @Override
            public void onFailure(Call<UpdateAdress> call, Throwable t) {

            }
        });
    }

    private void getAddress(){
        String id = sharedPref.getString(KEY_ID);
        try {
//            showProgressBar(progressBar);
            ApiHistory.API_HISTORY.getAddress(id).enqueue(new Callback<Address>() {
                @Override
                public void onResponse(Call<Address> call, Response<Address> response) {
                    if (response.body() != null) {
                        addRess = response.body().getAddress();
                        tv_address.setText(response.body().getAddress());
//                        hideProgressBar(progressBar);
                    }
                }

                @Override
                public void onFailure(Call<Address> call, Throwable t) {
//                    hideProgressBar(progressBar);
                }
            });
        }catch (Exception e){

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createBill() {
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .create();
        alertDialog.setTitle("Thông báo");
        alertDialog.setCancelable(false);

        alertDialog.setMessage("Xác nhận đặt hàng");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Xác nhận", (dialogInterface, i) -> {
            if(payment_methods == 0) {
                if (tv_address.toString().trim().isEmpty()){
                    Toast.makeText(requireContext(), "Bạn cần nhập địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    checkUserActive();
                }
            }else {
                requestPayment();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hủy", (dialogInterface, i) -> {});
        alertDialog.show();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addBill(){
        CreateBillRequest temp = adapter.billInformation(sharedPref.getString(KEY_ID));
        CreateBillRequest real = new CreateBillRequest(temp.getUserId(), temp.getProductList(),
                temp.getListQuantity(), temp.getListSize(), temp.getTotalPrice(), temp.getPrice(),
                temp.getListPrice(), temp.getListPriceSale(), temp.getDiscountVoucherPrice(),
                temp.getProductImage(), addRess);  // thay bằng address từ api get address nhé

        disposable.add(repository.createBill(real)
                .doOnSubscribe(disposable1 -> {
                    showProgressBar(progressBar);
                })
                .subscribe(okResponse -> {
                    Log.d("ahuhu", "createBill: success: ");
                    hideProgressBar(progressBar);
                    Toast.makeText(requireContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(requireContext(), MainActivity.class));
                    disposable.add(AppDatabase.getInstance(requireContext()).cartDao().deleteAll().subscribe(() -> {
                    }, throwable -> {
                    }));
                    adapter.clearData();
                }, throwable -> {
                    hideProgressBar(progressBar);
                    Log.d("ahuhu", "createBill: error: " + throwable.getMessage());
                }));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkUserActive() {
        Repository repository = new Repository(getContext());
        disposable.add(repository.checkUserActive(new CheckUserActiveRequest(sharedPref.getString(KEY_ID)))
                .doOnSubscribe(disposable -> {
                    // hien loading
                }).subscribe(response -> {
                    if (!response.getActive()) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_user_check);
                        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.findViewById(R.id.tv_confirm_user_check).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        addBill();
                    }
                }, throwable -> {
                    String error = new Utils().getErrorBody(throwable).getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }));
    }

    private void showDialogRequireLogin() {
        Dialog dialog = new Dialog(requireContext());
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getProductInCart() {
        Single<List<MyProductCart>> list = AppDatabase.getInstance(requireContext()).cartDao().getAllProductInCart();
        disposable.add(list
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                }).subscribe(myProductCarts -> {
                    adapter.setListData(myProductCarts);
                    tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
                }, throwable -> {
                    Log.d("ahuhu", "getProductInCart: error" + throwable.toString());
                }));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAdapter() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(new CartAdapter.CartOnClick() {
            @Override
            public void OnClickDelete(int position, MyProductCart myProductCart) {
                deleteProductFromCart(position, myProductCart);
                tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
            }

            @Override
            public void OnClickIncreaseQuantity(int position, MyProductCart myProductCart) {
                adapter.increaseAmount(position);
                tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
            }

            @Override
            public void OnClickDecreaseQuantity(int position, MyProductCart myProductCart) {
                adapter.decreaseAmount(position);
                tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
            }

//            @Override
//            public void OnTypeQuantityListener(int position, MyProductCart myProductCart, int newAmount) {
//                new Handler().postDelayed(() -> {adapter.setAmount(position, newAmount);
//                    tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));}, 500);
//            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteProductFromCart(int position, MyProductCart myProductCart) {
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .create();
        alertDialog.setTitle("Thông báo");
        alertDialog.setCancelable(false);

        alertDialog.setMessage("Xóa khỏi giỏ hàng");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Xóa", (dialogInterface, i) -> {
            disposable.add(AppDatabase.getInstance(requireContext()).cartDao().removeProductFromCart(myProductCart)
                    .doOnSubscribe(disposable1 -> {})
                    .doFinally(() -> {})
                    .subscribe(() -> {
                        adapter.removeProduct(position);
                        tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
                    },throwable -> {}));
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hủy", (dialogInterface, i) -> {

                });

        alertDialog.show();
    }


    private String moneyFormat(Long amount){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("VND"));
        return format.format(amount);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    // goi request MoMo pay
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestPayment() {
        amount = Integer.parseInt(String.valueOf(adapter.getTotal()));
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);


        Map<String, Object> eventValue = new HashMap<>();

        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", merchantCode + "-" + UUID.randomUUID().toString()); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", merchantNameLabel);//gán nhãn
        eventValue.put("fee", fee); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
//        //Example extra data
//        JSONObject objExtraData = new JSONObject();
//        try {
//            objExtraData.put("site_code", "008");
//            objExtraData.put("site_name", "CGV Cresent Mall");
//            objExtraData.put("screen_code", 0);
//            objExtraData.put("screen_name", "Special");
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
//            objExtraData.put("movie_format", "2D");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        eventValue.put("extraData", objExtraData.toString());
//
//        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(getActivity(), eventValue);
    }



    void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
//        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
//        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


}