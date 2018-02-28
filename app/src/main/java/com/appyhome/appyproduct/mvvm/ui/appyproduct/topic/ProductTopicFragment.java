package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.databinding.FragmentHomeBinding;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductTopicBinding;
import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.CategoryActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1.ServicesStep1Activity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class ProductTopicFragment extends BaseFragment<FragmentProductTopicBinding, ProductTopicViewModel> implements ProductTopicNavigator {

    public static final String TAG = "SampleTopicFragment";

    @Inject
    ProductTopicViewModel mViewModel;
    FragmentProductTopicBinding mBinder;

    public static ProductTopicFragment newInstance() {
        Bundle args = new Bundle();
        ProductTopicFragment fragment = new ProductTopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUp() {
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
    }

    @Override
    public ProductTopicViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_topic;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
