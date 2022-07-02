package com.example.modelfashion.History.ApdapterHistory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.MainActivity;
import com.example.modelfashion.History.ApiHistory.ApiHistory;
import com.example.modelfashion.History.ViewHistory.DetailHistoryActivity;
import com.example.modelfashion.History.ViewHistory.HistoryActivity;
import com.example.modelfashion.Model.MHistory.ProductHistory;
import com.example.modelfashion.Model.response.bill.Bill;

import com.example.modelfashion.Model.response.bill.CancelBill;
import com.example.modelfashion.Model.response.bill.FeedbackBill;
import com.example.modelfashion.Model.response.bill.RefundOfOrder;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryAdapter extends BaseAdapter {

    //    List<ModelHistory> listModel;
    ArrayList<Bill> arr_bill;
    Context context;
    ArrayList<MyProduct> arr_my_product;
    String user_id;
    private ArrayList<Bill> bills;
    String reason = "";
    CancelBill cancelBill;
    Dialog dialog;
    AsyncTask<?, ?, ?> runningTask;

    public HistoryAdapter(Context context, ArrayList<Bill> arr_bill) {

        this.context = context;
        this.arr_bill = arr_bill;

    }
//    public HistoryAdapter(Context context,List<ModelHistory> listModel) {
//        this.listModel = listModel;
//        this.context = context;
//    }

    @Override
    public int getCount() {
        return arr_bill.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_history, viewGroup, false);

//        Locale locale = new Locale("vi","VN");
//        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
//        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
//        String price0 = numberFormat.format(Double.parseDouble(listModel.get(i).getProductHistoryList().get(0).getmPriceProduct()));
        //Tham chiếu
        ImageView img_subproduct0 = view.findViewById(R.id.img_subproduct0);
        TextView tv_status = view.findViewById(R.id.tv_status);
        TextView tv_name_subproduct0 = view.findViewById(R.id.tv_name_subproduct0);
        TextView tv_sumproduct0 = view.findViewById(R.id.tv_sumproduct0);
        TextView tv_size_subproduct0 = view.findViewById(R.id.tv_size_subproduct0);
        TextView tv_price0 = view.findViewById(R.id.tv_price0);
        TextView tv_sumSP = view.findViewById(R.id.tv_sumSP);
        TextView tv_sumPrice = view.findViewById(R.id.tv_sumPrice);
        TextView tv_detail = view.findViewById(R.id.tv_detail);
        TextView item_history_ma = view.findViewById(R.id.item_history_ma);
        TextView item_history_time = view.findViewById(R.id.item_history_time);
        TextView tv_feedback = view.findViewById(R.id.tv_feedback);
        TextView tv_refund_of_order = view.findViewById(R.id.tv_refund_of_order);
        LinearLayout ll_item_history = view.findViewById(R.id.ll_item_history);


        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        item_history_ma.setText("Mã đơn: " + arr_bill.get(i).getBill_id());

        Glide.with(context).load(arr_bill.get(i).getProduct_image()).into(img_subproduct0);
        tv_status.setText(arr_bill.get(i).getStatus());
        tv_name_subproduct0.setText(arr_bill.get(i).getProduct_name());
        tv_sumproduct0.setText("x" + arr_bill.get(i).getQuantity());
        tv_size_subproduct0.setText(arr_bill.get(i).getSize());
        tv_price0.setText(decimalFormat.format(Double.parseDouble(arr_bill.get(i).getPrice())) + " VNĐ");
        tv_sumSP.setText(arr_bill.get(i).getTotal_product() + " Sản phẩm");
        tv_sumPrice.setText("Tổng: " + decimalFormat.format(Integer.parseInt(arr_bill.get(i).getTotal_price())) + " VNĐ");

        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailHistoryActivity.class);
                intent.putExtra("bill_id", arr_bill.get(i).getBill_id());

                context.startActivity(intent);
            }
        });
        if (arr_bill.get(i).getStatus().matches("Hoàn Thành")) {
            item_history_time.setText("Ngày đặt: " + arr_bill.get(i).getCreated_at().substring(0, 10));
            tv_feedback.setVisibility(View.VISIBLE);
            tv_feedback.setText("Phản hồi");
            tv_refund_of_order.setVisibility(View.VISIBLE);
        } else if (arr_bill.get(i).getStatus().matches("Chờ Xác Nhận")) {
            item_history_time.setText("Ngày đặt: " + arr_bill.get(i).getCreated_at().substring(0, 10));
            tv_feedback.setVisibility(View.VISIBLE);
            tv_feedback.setText("Hủy đơn");
            tv_refund_of_order.setVisibility(View.GONE);

        } else {

            item_history_time.setText("Ngày đặt: " + arr_bill.get(i).getCreated_at().substring(0, 10));
            tv_feedback.setVisibility(View.VISIBLE);
            tv_feedback.setText("Hủy đơn");
            tv_feedback.setAlpha(0.5f);
            tv_feedback.setEnabled(false);
            tv_feedback.setVisibility(View.INVISIBLE);
            tv_refund_of_order.setVisibility(View.GONE);

        }
        tv_refund_of_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRefundOfOrder(arr_bill.get(i).getBill_id());
            }
        });
        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arr_bill.get(i).getStatus().matches("Hoàn Thành")) {
                    loadDialogFeedback(context, arr_bill.get(i).getBill_id());
                } else {
                    // showDialogCancelOrder();

                    showDialogCancelOrder(arr_bill.get(i).getBill_id());

                }

            }
        });
        return view;
    }


    private void sendCancelBill(CancelBill cancelBill) {
        ApiHistory.API_HISTORY.cancelBill(cancelBill).enqueue(new Callback<CancelBill>() {
            @Override
            public void onResponse(Call<CancelBill> call, Response<CancelBill> response) {
                HistoryActivity.rl_load.setVisibility(View.GONE);
                Toast.makeText(context, "Đã gửi yêu cầu hủy đơn", Toast.LENGTH_SHORT).show();
                senDataToActivity();
            }

            @Override
            public void onFailure(Call<CancelBill> call, Throwable t) {
                HistoryActivity.rl_load.setVisibility(View.GONE);
                Toast.makeText(context, "Yêu cầu hủy đơn thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendRefundOfOrder(RefundOfOrder refundOfOrder){
        ApiHistory.API_HISTORY.refundOfOrder(refundOfOrder).enqueue(new Callback<RefundOfOrder>() {
            @Override
            public void onResponse(Call<RefundOfOrder> call, Response<RefundOfOrder> response) {
                HistoryActivity.rl_load.setVisibility(View.GONE);
                Toast.makeText(context, "Đã gửi yêu cầu trả hàng", Toast.LENGTH_SHORT).show();
                senDataToActivity();
            }

            @Override
            public void onFailure(Call<RefundOfOrder> call, Throwable t) {
                HistoryActivity.rl_load.setVisibility(View.GONE);
                Toast.makeText(context, "Yêu cầu trả hàng thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendFeedbackBill(FeedbackBill feedbackBill){
        ApiHistory.API_HISTORY.feedbackBill(feedbackBill).enqueue(new Callback<FeedbackBill>() {
            @Override
            public void onResponse(Call<FeedbackBill> call, Response<FeedbackBill> response) {
                HistoryActivity.rl_load.setVisibility(View.GONE);
                Toast.makeText(context, "Đã gửi phản hồi", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<FeedbackBill> call, Throwable t) {
                HistoryActivity.rl_load.setVisibility(View.GONE);
                Toast.makeText(context, "Gửi phản hồi thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loadDialogFeedback(Context context, String maDH) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_feedback);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText edt_title = dialog.findViewById(R.id.edt_title);
        EditText edt_content = dialog.findViewById(R.id.edt_content);
        TextView btn_send = dialog.findViewById(R.id.btn_send);
        edt_title.setText("Phản hồi đơn hàng " + maDH);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content;
                content = edt_content.getText().toString().trim();
                if (content.isEmpty()) {
                    Toast.makeText(context, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
                } else {
//                    String uriText = "mailto:" + context.getString(R.string.email) +
//                            "?subject=" + "Feedback đơn hàng " + maDH +
//                            "&body=" + content;
//                    Uri uri = Uri.parse(uriText);
//                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
//                    sendIntent.setData(uri);
//                    context.startActivity(Intent.createChooser(sendIntent, "Send Email"));
                    sendFeedbackBill(new FeedbackBill(maDH,content));
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }

    int reason_check = 1;

    private void showDialogCancelOrder(String build_id) {
        reason_check = 1;
        reason = context.getString(R.string.reason_cancer_1);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_cancel_order);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        TextView tv_hotline, tv_email, tv_zalo;
        TextView tv_yes = dialog.findViewById(R.id.tv_Yes);
        ImageView img_1, img_2, img_3, img_4;
        EditText edt_reason_cacel_bill;
        tv_hotline = dialog.findViewById(R.id.tv_hotline);
        tv_email = dialog.findViewById(R.id.tv_email);
        tv_zalo = dialog.findViewById(R.id.tv_zalo);
        img_1 = dialog.findViewById(R.id.img_1);
        img_2 = dialog.findViewById(R.id.img_2);
        img_3 = dialog.findViewById(R.id.img_3);
        img_4 = dialog.findViewById(R.id.img_4);
        edt_reason_cacel_bill = dialog.findViewById(R.id.edt_reason_cacel_bill);

        loadThemeDot(reason_check, img_1, img_2, img_3, img_4);

        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason_check = 1;
                loadThemeDot(reason_check, img_1, img_2, img_3, img_4);
                edt_reason_cacel_bill.setText("");
                edt_reason_cacel_bill.setVisibility(View.GONE);
                reason = context.getString(R.string.reason_cancer_1);
            }
        });
        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason_check = 2;
                loadThemeDot(reason_check, img_1, img_2, img_3, img_4);
                edt_reason_cacel_bill.setText("");
                edt_reason_cacel_bill.setVisibility(View.GONE);
                reason = context.getString(R.string.reason_cancer_2);
            }
        });
        img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason_check = 3;
                loadThemeDot(reason_check, img_1, img_2, img_3, img_4);
                edt_reason_cacel_bill.setText("");
                edt_reason_cacel_bill.setVisibility(View.GONE);
                reason = context.getString(R.string.reason_cancer_3);
            }
        });
        img_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason_check = 4;
                loadThemeDot(reason_check, img_1, img_2, img_3, img_4);
                edt_reason_cacel_bill.setText("");
                edt_reason_cacel_bill.setVisibility(View.VISIBLE);
            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reason_check == 4) {
                    if (edt_reason_cacel_bill.getText().length() > 0) {
                        reason = edt_reason_cacel_bill.getText().toString().trim();
                        cancelBill = new CancelBill(build_id,reason);
                        //Toast.makeText(context,reason,Toast.LENGTH_SHORT).show();

                        showDialogConfirmCancerBill(cancelBill);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context,"Vui lòng nhập lý do hủy đơn",Toast.LENGTH_SHORT).show();
                    }


                }else {
                    cancelBill = new CancelBill(build_id,reason);
                    //Toast.makeText(context,reason,Toast.LENGTH_SHORT).show();
                    showDialogConfirmCancerBill(cancelBill);
                    dialog.dismiss();
                }


            }
        });


        dialog.show();

    }

    private void showDialogConfirmCancerBill(CancelBill cancelBill){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_cancer_bill);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.findViewById(R.id.btn_cancel_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendCancelBill(cancelBill);
                HistoryActivity.rl_load.setVisibility(View.VISIBLE);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void showDialogRefundOfOrder(String build_id) {
        reason_check = 1;
        reason = context.getString(R.string.gui_sai_hang);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_refund_of_order);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        TextView tv_hotline, tv_email, tv_zalo;
        TextView tv_yes = dialog.findViewById(R.id.tv_Yes);
        ImageView img_1, img_2, img_3, img_4;
        EditText edt_reason_cacel_bill;
        tv_hotline = dialog.findViewById(R.id.tv_hotline);
        tv_email = dialog.findViewById(R.id.tv_email);
        tv_zalo = dialog.findViewById(R.id.tv_zalo);
        img_1 = dialog.findViewById(R.id.img_1);
        img_2 = dialog.findViewById(R.id.img_2);
        img_3 = dialog.findViewById(R.id.img_3);
        img_4 = dialog.findViewById(R.id.img_4);
        edt_reason_cacel_bill = dialog.findViewById(R.id.edt_reason_cacel_bill);

        loadThemeDot(reason_check, img_1, img_2, img_3, img_4);

        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason_check = 1;
                reason = context.getString(R.string.gui_sai_hang);
                loadThemeDot(reason_check, img_1, img_2, img_3, img_4);
                edt_reason_cacel_bill.setText("");
                edt_reason_cacel_bill.setVisibility(View.GONE);
            }
        });
        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason_check = 2;
                reason = context.getString(R.string.chua_nhan_hang);
                loadThemeDot(reason_check, img_1, img_2, img_3, img_4);
                edt_reason_cacel_bill.setText("");
                edt_reason_cacel_bill.setVisibility(View.GONE);
            }
        });
        img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason_check = 3;
                reason = context.getString(R.string.hang_loi);
                loadThemeDot(reason_check, img_1, img_2, img_3, img_4);
                edt_reason_cacel_bill.setText("");
                edt_reason_cacel_bill.setVisibility(View.GONE);
            }
        });
        img_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason_check = 4;
                loadThemeDot(reason_check, img_1, img_2, img_3, img_4);
                edt_reason_cacel_bill.setText("");
                edt_reason_cacel_bill.setVisibility(View.VISIBLE);
            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reason_check==4){
                    if(edt_reason_cacel_bill.getText().toString().trim().length()>0){
                        reason = edt_reason_cacel_bill.getText().toString().trim();
                        sendRefundOfOrder(new RefundOfOrder(build_id,reason));
                        HistoryActivity.rl_load.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context,"Vui lòng nhập lý do trả hàng",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    sendRefundOfOrder(new RefundOfOrder(build_id,reason));
                    HistoryActivity.rl_load.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }

            }
        });


        dialog.show();

    }

    private void loadThemeDot(int index, ImageView img1, ImageView img2, ImageView img3, ImageView img4) {
        if (index == 1) {
            Glide.with(context).load(R.drawable.ic_dot_select_cancel_bill).into(img1);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img2);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img3);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img4);
        } else if (index == 2) {
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img1);
            Glide.with(context).load(R.drawable.ic_dot_select_cancel_bill).into(img2);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img3);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img4);
        } else if (index == 3) {
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img1);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img2);
            Glide.with(context).load(R.drawable.ic_dot_select_cancel_bill).into(img3);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img4);
        } else if (index == 4) {
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img1);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img2);
            Glide.with(context).load(R.drawable.ic_dot_unselect_cancel_bill).into(img3);
            Glide.with(context).load(R.drawable.ic_dot_select_cancel_bill).into(img4);
        }

    }


    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    private void senDataToActivity() {
        Intent intent = new Intent("send_data_to_activity");
        intent.putExtra("action", "load");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


}
