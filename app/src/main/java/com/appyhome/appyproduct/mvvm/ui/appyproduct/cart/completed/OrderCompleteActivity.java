package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCartCompletedBinding;
import com.appyhome.appyproduct.mvvm.databinding.ActivitySampleBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class OrderCompleteActivity extends BaseActivity<ActivityProductCartCompletedBinding, OrderCompleteViewModel> implements OrderCompleteNavigator {

    ActivityProductCartCompletedBinding mBinder;

    @Inject
    public OrderCompleteViewModel mMainViewModel;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, OrderCompleteActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMainViewModel);
        mBinder.setNavigator(this);
        mMainViewModel.setNavigator(this);
    }

    @Override
    public void returnHomeScreen() {
        Intent i = MainActivity.getStartIntent(this);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void viewOrders() {
        returnHomeScreen();
    }


    @Override
    protected void onResume() {
        super.onResume();
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
    public int getLayoutId() {
        return R.layout.activity_product_cart_completed;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }
}
