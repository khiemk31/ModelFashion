package com.example.modelfashion.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ProgressLoadingCommon {
    ProgressDialog progressdialog;
    public void showProgressLoading(Context context) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage("Vui lòng đợi");
        progressdialog.setCancelable(false);
        progressdialog.show();
    }

    public void hideProgressLoading(Context context){
        progressdialog = new ProgressDialog(context);
        progressdialog.dismiss();
    }
}
