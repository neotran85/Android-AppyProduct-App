package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.edit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestEditBinding;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.PaymentManager;
import com.molpay.molpayxdk.MOLPayActivity;

import javax.inject.Inject;

public class RequestEditActivity extends BaseActivity<ActivityRequestEditBinding, RequestEditViewModel> implements RequestItemNavigator, View.OnClickListener {

    @Inject
    RequestEditViewModel mRequestEditViewModel;
    ActivityRequestEditBinding mBinder;
    @Inject
    int mLayoutId;
    private Double mPrice = 0.0;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RequestEditActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestEditViewModel);
        mRequestEditViewModel.setNavigator(this);
        setTitle(getString(R.string.adding_services));
        activeBackButton();
        mRequestEditViewModel.setUpData(getIntent());
        mBinder.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (checkIfValidationOK())
                    openBankPaymentActivity();
                break;
        }
    }

    private boolean checkIfValidationOK() {
        if (mBinder.etAdditionalInfo.getText().length() <= 0) {
            AlertManager.getInstance(this).showLongToast(getString(R.string.enter_service_name));
            return false;
        }
        if (mBinder.etAdditionalAmount.getText().length() <= 0) {
            AlertManager.getInstance(this).showLongToast(getString(R.string.enter_amount));
            return false;
        }
        // Get price
        String amount = mBinder.etAdditionalAmount.getText().toString();
        mPrice = Double.valueOf(amount);
        if (mPrice <= 0) {
            AlertManager.getInstance(this).showLongToast(getString(R.string.enter_amount_greater_zero));
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
    public void handleErrorService(Throwable a) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
    }

    public void openBankPaymentActivity() {
        PaymentManager.getInstance().startMolpayActivity(this,
                mPrice.toString(), mRequestEditViewModel.getIdNumber(),
                mRequestEditViewModel.getPhoneNumberOfUser(),
                mRequestEditViewModel.getEmailOfUser(),
                mRequestEditViewModel.getNameOfUser());
    }

    @Override
    public void doAfterDataUpdated() {
        finish();
    }

    private void doAfterPaymentCompleted(String txn_ID) {
        mRequestEditViewModel.editOrder(mBinder.etAdditionalInfo.getText().toString(),
                mBinder.etAdditionalAmount.getText().toString(), txn_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MOLPayActivity.MOLPayXDK && resultCode == Activity.RESULT_OK) {
            String txn_id = mRequestEditViewModel.getTXN_IDWhenPaymentReturned(data);
            if (txn_id != null) {
                doAfterPaymentCompleted(txn_id);
                AlertManager.getInstance(this).showLongToast(getString(R.string.payment_success));
            } else {
                AlertManager.getInstance(this).showLongToast(getString(R.string.payment_error_something));
            }
        }
    }
}
