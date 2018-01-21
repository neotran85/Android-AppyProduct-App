package com.appyhome.appyproduct.mvvm.utils.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.appyhome.appyproduct.mvvm.R;

public class AlertManager {
    private static AlertManager mInstance;
    private Toast mToast;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    private AlertManager(Context context) {
        mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mContext = context;
    }

    public static AlertManager getInstance(Context context) {
        if (mInstance == null || mInstance.mContext != context) {
            mInstance = new AlertManager(context);
        }
        return mInstance;
    }

    public void closeLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }

    public void showLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.cancel();
        mProgressDialog = ProgressDialog.show(mContext, "",
                mContext.getString(R.string.message_loading), true);
    }

    public void showQuickToast(String text) {
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showLongToast(String text) {
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }
}
