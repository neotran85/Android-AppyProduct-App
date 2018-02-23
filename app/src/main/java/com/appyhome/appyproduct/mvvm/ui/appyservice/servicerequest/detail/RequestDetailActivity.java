package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestType;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.confirm.RequestConfirmedActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.edit.RequestEditActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCaptureActivity;
import com.google.android.gms.vision.barcode.Barcode;

import javax.inject.Inject;

public class RequestDetailActivity extends BaseActivity<ActivityRequestDetailBinding, RequestItemViewModel> implements RequestItemNavigator, View.OnClickListener {

    private static final int REQUEST_BARCODE_CAPTURE = 9001;
    @Inject
    RequestItemViewModel mRequestItemViewModel;
    ActivityRequestDetailBinding mBinder;
    private int mType = 0;
    private String mIdNumber = "";

    private int[] mIdTitle = {R.string.title_request_summary, R.string.title_order_summary,
            R.string.title_receipt_summary};

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RequestDetailActivity.class);
        return intent;
    }

    @Override
    public void onResume() {
        super.onResume();
        processDetailData(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestItemViewModel);
        mRequestItemViewModel.setNavigator(this);
        setTitle(getString(R.string.titlte_summary));
        activeBackButton();
        ViewUtils.setOnClickListener(this, mBinder.llAddServices,
                mBinder.llConfirmation, mBinder.llRefundServices);
    }

    private void processDetailData(Intent data) {
        if (data != null && data.hasExtra("id")) {
            mIdNumber = data.getStringExtra("id");
            mType = data.getIntExtra("type", RequestType.TYPE_REQUEST);
            mRequestItemViewModel.fetchData(mIdNumber, mType);
            setTitle(getString(mIdTitle[mType]));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llConfirmation:
                openExtraActivity(RequestConfirmedActivity.MODE_CONFIRM);
                break;
            case R.id.llAddServices:
                openQRCodeScanActivity();
                break;
            case R.id.llRefundServices:
                openExtraActivity(RequestConfirmedActivity.MODE_REFUND);
                break;
        }
    }

    private void openEditDetailActivity() {
        Intent intent = getIntent();
        intent.setClass(this, RequestEditActivity.class);
        intent.putExtra("id", mIdNumber);
        intent.putExtra("type", mType);
        intent.putExtra("edit_code", mRequestItemViewModel.getEditCode());
        startActivity(intent);
    }

    private void openExtraActivity(int mode) {
        Intent intent = getIntent();
        intent.setClass(this, RequestConfirmedActivity.class);
        intent.putExtra("id", mIdNumber);
        intent.putExtra("type", mType);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public RequestItemViewModel getViewModel() {
        return mRequestItemViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_detail;
    }

    @Override
    public void doAfterDataUpdated() {
        // GET EDIT CODE SAFELY
    }

    @Override
    public void handleErrorService(Throwable a) {

    }

    public void openQRCodeScanActivity() {
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
        startActivityForResult(intent, REQUEST_BARCODE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    if (barcode.displayValue.equals(mRequestItemViewModel.getEditCode())) {
                        // CODE MATCHED & ALLOW USER TO EDIT THE DETAIL
                        allowUserEditDetail();
                        return;
                    } else {
                        AlertManager.getInstance(this).showLongToast(getString(R.string.barcode_error_not_matched));
                        return;
                    }
                }
            }
        }
        AlertManager.getInstance(this).showLongToast(getString(R.string.barcode_error_general));
    }

    private void allowUserEditDetail() {
        AlertManager.getInstance(this).showLongToast(getString(R.string.barcode_success));
        openEditDetailActivity();
    }
}
