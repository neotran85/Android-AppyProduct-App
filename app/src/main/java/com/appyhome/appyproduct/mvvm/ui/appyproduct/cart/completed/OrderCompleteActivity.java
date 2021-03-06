package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartCompletedBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list.ListProductOrderActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class OrderCompleteActivity extends BaseActivity<ActivityProductCartCompletedBinding, OrderCompleteViewModel> implements OrderCompleteNavigator {

    @Inject
    public OrderCompleteViewModel mMainViewModel;
    ActivityProductCartCompletedBinding mBinder;
    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, OrderCompleteActivity.class);
        return intent;
    }

    @Override
    public void onBackPressed() {
        returnHomeScreen();
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
        mBinder.setNavigator(this);
        getViewModel().setNavigator(this);
        getViewModel().updateUserCartAgain();
        getViewModel().orderPref.set("Order Id: " + getOrderId() + "");
    }

    @Override
    public void returnHomeScreen() {
        Intent i = MainActivity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private long getOrderId() {
        return getIntent().getLongExtra("order_id", 0);
    }

    @Override
    public void viewOrders() {
        Intent intent = ListProductOrderActivity.getStartIntent(this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public OrderCompleteViewModel getViewModel() {
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

    @Override
    public void onFetchUserInfo_Done() {

    }

    @Override
    public void onFetchUserInfo_Failed() {

    }
}
