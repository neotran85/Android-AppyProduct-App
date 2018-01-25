package com.appyhome.appyproduct.mvvm.ui.servicerequest.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;

public class RequestDetailActivity extends BaseActivity<ActivityRequestDetailBinding, RequestDetailViewModel> implements RequestDetailNavigator {

    @Inject
    RequestDetailViewModel mRequestDetailViewModel;

    ActivityRequestDetailBinding mBinder;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RequestDetailActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestDetailViewModel);
        mRequestDetailViewModel.setNavigator(this);
        Intent intent = getIntent();
        String data = intent.getStringExtra("detail");
        mRequestDetailViewModel.proceedData(data);
        setTitle("SUMMARY");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public RequestDetailViewModel getViewModel() {
        return mRequestDetailViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_detail;
    }

}
