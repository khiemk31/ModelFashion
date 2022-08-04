package com.example.modelfashion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelfashion.Fragment.CartFragment;
import com.example.modelfashion.Model.Voucher;
import com.example.modelfashion.R;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherHolder>{
    Context context;
    List<Voucher> list;

    public VoucherAdapter(Context context, List<Voucher> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VoucherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_voucher,parent,false);
        return new VoucherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherHolder holder, int position) {
        Voucher voucher = list.get(position);
        holder.voucher_title.setText(voucher.getmTitleVoucher());
        holder.voucher_hsd.setText("HSD:"+voucher.getmHSD());
        holder.tv_select_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartFragment.IDVoucher = voucher.getmIDVoucher();
                Intent intent = new Intent("send_data_to_fragment");
                intent.putExtra("action","closevoucher");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VoucherHolder extends RecyclerView.ViewHolder{
        TextView voucher_title,voucher_hsd,tv_select_voucher;

        public VoucherHolder(@NonNull View itemView) {
            super(itemView);
            voucher_title = itemView.findViewById(R.id.voucher_title);
            voucher_hsd = itemView.findViewById(R.id.voucher_hsd);
            tv_select_voucher = itemView.findViewById(R.id.tv_select_voucher);

        }
    }
}
