package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartPaymentBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.ConfirmationActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class PaymentActivity extends BaseActivity<ActivityProductCartPaymentBinding, PaymentViewModel> implements PaymentNavigator {

    @Inject
    public PaymentViewModel mViewModel;

    ActivityProductCartPaymentBinding mBinder;

    boolean isEditMode = false;

    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PaymentActivity.class);
        return intent;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        mViewModel.fetchPaymentMethods();
        isEditMode = getIntent().getBooleanExtra("edit_mode", false);
        mViewModel.isEditMode.set(isEditMode);
    }

    @Override
    public void gotoNextStep() {
        if (isEditMode)
            finish();
        else startActivity(ConfirmationActivity.getStartIntent(this));
    }

    @Override
    public void setDefaultPaymentMethod(View view) {
        switch (view.getId()) {
            case R.id.llVisa:
                getViewModel().setDefaultPaymentMethod(PaymentViewModel.PAYMENT_VISA);
                break;
            case R.id.llMolpay:
                getViewModel().setDefaultPaymentMethod(PaymentViewModel.PAYMENT_MOLPAY);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public PaymentViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }
}
