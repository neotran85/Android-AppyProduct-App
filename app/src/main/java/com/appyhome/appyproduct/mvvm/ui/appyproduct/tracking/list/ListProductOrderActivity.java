package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductOrderListBinding;
import com.appyhome.appyproduct.mvvm.databinding.ActivitySampleBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class ListProductOrderActivity extends BaseActivity<ActivityProductOrderListBinding, ListProductOrderViewModel> implements ListProductOrderNavigator {

    @Inject
    public ListProductOrderViewModel mMainViewModel;
    ActivityProductOrderListBinding mBinder;
    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ListProductOrderActivity.class);
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
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public ListProductOrderViewModel getViewModel() {
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
