package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductTrackingBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class ProductTrackingActivity extends BaseActivity<ActivityProductTrackingBinding, ProductTrackingViewModel> implements ProductTrackingNavigator {

    @Inject
    public ProductTrackingViewModel mMainViewModel;

    ActivityProductTrackingBinding mBinder;

    @Inject
    int mLayoutId;

    private long mOrderId = 0;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductTrackingActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void showOrder(ProductOrder order) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        mOrderId = getIntent().getLongExtra("order_id", 0);
        mMainViewModel.getOrderById(mOrderId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public ProductTrackingViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(message);
    }
}
