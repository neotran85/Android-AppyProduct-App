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
        mViewModel.updatePaymentMethods();
    }

    @Override
    public void gotoNextStep() {
        if (!getViewModel().isDefaultPaymentMethodsExist()) {
            showAlert(getString(R.string.pls_choose_payment_method));
            return;
        }
        mBinder.btnNext.setText(getString(R.string.verifying_order));
        getViewModel().doVerifyOrder();
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
        updateNextButtonText();
    }

    private void updateNextButtonText() {
        isEditMode = getIntent().getBooleanExtra("edit_mode", false);
        mBinder.btnNext.setText(isEditMode ? "SAVE" : "NEXT");
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

    @Override
    public void verifyOrder_PASSED() {
        updateNextButtonText();
        if (isEditMode)
            finish();
        else startActivity(ConfirmationActivity.getStartIntent(this));
    }

    @Override
    public void verifyOrder_FAILED(String message) {
        updateNextButtonText();
    }
}
