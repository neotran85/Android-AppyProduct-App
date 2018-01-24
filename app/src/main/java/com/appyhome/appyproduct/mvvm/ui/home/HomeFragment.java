package com.appyhome.appyproduct.mvvm.ui.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentHomeBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step1.ServicesStep1Activity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator, View.OnClickListener {

    public static final String TAG = "HomeFragment";

    @Inject
    HomeViewModel mHomeViewModel;
    FragmentHomeBinding mBinder;
    private Toolbar mToolbar;
    private ImageButton mToolbarCartButton;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibAirConServicing:
                ServiceOrderInfo.getInstance().setUpData(ServiceOrderInfo.SERVICE_AIR_CON_CLEANING);
                startActivity(ServicesStep1Activity.getStartIntent(this.getContext()));
                break;
            case R.id.ibHomeCleaning:
                ServiceOrderInfo.getInstance().setUpData(ServiceOrderInfo.SERVICE_HOME_CLEANING);
                startActivity(ServicesStep1Activity.getStartIntent(this.getContext()));
                break;
        }
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
        mHomeViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mHomeViewModel);
        mToolbar = mBinder.toolbar;
        mToolbarCartButton = mBinder.toolbar.findViewById(R.id.toolbarCartButton);
        mToolbar.setNavigationIcon(null);
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(mToolbar);
            activity.getSupportActionBar().setDisplayUseLogoEnabled(false);
            activity.getSupportActionBar().setTitle("");
        }
        ViewUtils.setOnClickListener(mBinder.serviceView, R.id.ibHomeCleaning, this);
        ViewUtils.setOnClickListener(mBinder.serviceView, R.id.ibAirConServicing, this);
    }

    @Override
    public HomeViewModel getViewModel() {
        return mHomeViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
