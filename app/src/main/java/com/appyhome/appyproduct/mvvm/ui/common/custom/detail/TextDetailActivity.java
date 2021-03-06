package com.appyhome.appyproduct.mvvm.ui.common.custom.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.ActivityDetailTextBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;

public class TextDetailActivity extends BaseActivity<ActivityDetailTextBinding, TextDetailViewModel> {

    @Inject
    TextDetailViewModel mTextDetailViewModel;

    ActivityDetailTextBinding mActivityDetailTextBinding;

    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, TextDetailActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailTextBinding = getViewDataBinding();
        mTextDetailViewModel.setNavigator(this);
        mActivityDetailTextBinding.setViewModel(mTextDetailViewModel);
        Intent intent = getIntent();
        mTextDetailViewModel.setUp(intent);
        activeBackButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public TextDetailViewModel getViewModel() {
        return mTextDetailViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

}
