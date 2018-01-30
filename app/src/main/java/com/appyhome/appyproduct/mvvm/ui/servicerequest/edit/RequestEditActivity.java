package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestEditBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemNavigator;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.PaymentManager;
import com.molpay.molpayxdk.MOLPayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class RequestEditActivity extends BaseActivity<ActivityRequestEditBinding, RequestEditViewModel> implements RequestItemNavigator, View.OnClickListener {

    @Inject
    RequestEditViewModel mRequestEditViewModel;
    ActivityRequestEditBinding mBinder;

    private Double mPrice = 0.0;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RequestEditActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestEditViewModel);
        mRequestEditViewModel.setNavigator(this);
        setTitle("Adding Services");
        activeBackButton();
        mRequestEditViewModel.setUpData(getIntent());
        mBinder.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if(checkIfValidationOK())
                    openBankPaymentActivity();
                break;
        }
    }

    private boolean checkIfValidationOK() {
        if(mBinder.etAdditionalInfo.getText().length() <= 0) {
            AlertManager.getInstance(this).showLongToast("Please enter the service name.");
            return false;
        }
        if(mBinder.etAdditionalAmount.getText().length() <= 0) {
            AlertManager.getInstance(this).showLongToast("Please enter an amount.");
            return false;
        }
        // Get price
        String amount = mBinder.etAdditionalAmount.getText().toString();
        mPrice = Double.valueOf(amount);
        if(mPrice <= 0) {
            AlertManager.getInstance(this).showLongToast("Please enter an amount greater than zero.");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public RequestEditViewModel getViewModel() {
        return mRequestEditViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_edit;
    }

    @Override
    public void handleErrorService(Throwable a) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
    }

    public void openBankPaymentActivity() {
        PaymentManager.getInstance().startPaymentActivity(this,
                mPrice.toString(), mRequestEditViewModel.getIdNumber(),
                mRequestEditViewModel.getPhoneNumberOfUser(), mRequestEditViewModel.getEmailOfUser());
    }

    @Override
    public void doAfterDataUpdated() {
        finish();
    }

    public void doAfterPaymentCompleted(String txn_ID) {
        mRequestEditViewModel.editOrder(mBinder.etAdditionalInfo.getText().toString(),
                mBinder.etAdditionalAmount.getText().toString(), txn_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MOLPayActivity.MOLPayXDK && resultCode == RESULT_OK) {
            AppLogger.d(MOLPayActivity.MOLPAY, "MOLPay result = " + data.getStringExtra(MOLPayActivity.MOLPayTransactionResult));
            try {
                JSONObject result = new JSONObject(data.getStringExtra(MOLPayActivity.MOLPayTransactionResult));
                if(result.getString("status_code").equals("00")) {
                    // PAYMENT SUCCESS
                    String txn_ID = result.getString("txn_ID");
                    AppLogger.d(txn_ID);
                    doAfterPaymentCompleted(txn_ID);
                    AlertManager.getInstance(this).showLongToast(getString(R.string.payment_success));
                }
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AlertManager.getInstance(this).showLongToast(getString(R.string.payment_error_something));
        }

    }
}
