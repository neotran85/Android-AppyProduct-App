package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductSearchBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchItemBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchTagBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.common.component.FlowLayout;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity<ActivityProductSearchBinding, SearchViewModel> implements SearchNavigator, SearchItemNavigator {

    @Inject
    public SearchViewModel mMainViewModel;
    ActivityProductSearchBinding mBinder;
    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    public void goBack() {
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
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        mBinder.flHistorySearch.addView(createTag("samsung s6"));
        mBinder.flHistorySearch.addView(createTag("samsung note 2"));
        mBinder.flHistorySearch.addView(createTag("iphone"));
        mBinder.flHistorySearch.addView(createTag("ipad pro 2018"));
        mBinder.flHistorySearch.addView(createTag("macbook pro 2018"));
        mBinder.flHistorySearch.addView(createTag("samsung tab 2018"));
    }

    @Override
    public void onItemClick(View view) {
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public SearchViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    private View createTag(String text) {
        ViewItemProductSearchTagBinding binding = ViewItemProductSearchTagBinding.inflate(getLayoutInflater(), null, false);
        binding.tvTag.setText(text);
        return binding.getRoot();
    }
}
