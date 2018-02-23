package com.appyhome.appyproduct.mvvm.utils.manager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ui.common.browser.BrowserActivity;

public class AlertManager {
    private static AlertManager mInstance;
    private Toast mToast;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;

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

    public void showComingSoonDialog() {
        if (mAlertDialog == null) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            LayoutInflater li = LayoutInflater.from(mContext);
            View theView = li.inflate(R.layout.dialog_coming_soon, null);
            Button buttonClose = theView.findViewById(R.id.btClose);
            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAlertDialog != null) {
                        mAlertDialog.dismiss();
                    }
                }
            });
            dialog.setView(theView);
            mAlertDialog = dialog.show();
        } else {
            mAlertDialog.show();
        }
    }

    public void showDialog(String title, String detailText, DialogInterface.OnClickListener positiveListener) {
        if (mAlertDialog == null) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            dialogBuilder.setTitle(title);
            dialogBuilder.setMessage(detailText);
            dialogBuilder.setPositiveButton("YES", positiveListener);
            dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mAlertDialog != null)
                        mAlertDialog.dismiss();
                }
            });
            mAlertDialog = dialogBuilder.show();
        } else {
            mAlertDialog.show();
        }
    }

    public void openInformationBrowser(String title, String url) {
        Intent intent = BrowserActivity.getStartIntent(mContext);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        mContext.startActivity(intent);
    }
}
