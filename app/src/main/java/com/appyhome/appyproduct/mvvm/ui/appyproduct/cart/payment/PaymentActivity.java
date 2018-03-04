package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductPaymentBinding;
import com.appyhome.appyproduct.mvvm.databinding.ActivitySampleBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class PaymentActivity extends BaseActivity<ActivityProductPaymentBinding, PaymentViewModel> implements PaymentNavigator, View.OnClickListener {

    ActivityProductPaymentBinding mBinder;

    @Inject
    public PaymentViewModel mMainViewModel;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PaymentActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMainViewModel);
        mBinder.setNavigator(this);
        mMainViewModel.setNavigator(this);
        mMainViewModel.fetchPaymentMethods();
        ViewUtils.setOnClickListener(this, mBinder.llMolpay, mBinder.llVisa);
    }

    @Override
    public void gotoNextStep() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llVisa:
                mMainViewModel.setDefaultPaymentMethod(PaymentViewModel.PAYMENT_VISA);
                break;
            case R.id.llMolpay:
                mMainViewModel.setDefaultPaymentMethod(PaymentViewModel.PAYMENT_MOLPAY);
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public PaymentViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_payment;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }
}
