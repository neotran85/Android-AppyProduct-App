package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.visa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityVisaPaymentBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class VisaPaymentActivity extends BaseActivity<ActivityVisaPaymentBinding, VisaPaymentViewModel> implements VisaPaymentNavigator {

    @Inject
    public VisaPaymentViewModel mMainViewModel;
    ActivityVisaPaymentBinding mBinder;
    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, VisaPaymentActivity.class);
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
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
    }

    private boolean checkIfAllFilled() {
        if (mBinder.etNameCard.getText().length() == 0 || mBinder.etNumberCard.getText().length() == 0
                || mBinder.etCVV.getText().length() == 0 || mBinder.etMonth.getText().length() == 0
                || mBinder.etYeart.getText().length() == 0) {
            showAlert(getString(R.string.visa_fill_all_pls));
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public VisaPaymentViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message, R.style.AppyToast_VisaCard);
    }
}
