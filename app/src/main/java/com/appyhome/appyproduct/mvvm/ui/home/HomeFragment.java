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
import com.appyhome.appyproduct.mvvm.ui.login.LoginActivity;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator {

    public static final String TAG = "HomeFragment";

    @Inject
    HomeViewModel mHomeViewModel;
    FragmentHomeBinding mFragmentHomeBinding;
    private Toolbar mToolbar;
    private ImageButton mToolbarCartButton;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
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
        mHomeViewModel.setNavigator(this);
        mFragmentHomeBinding = getViewDataBinding();

        mToolbar = mFragmentHomeBinding.toolbar;
        mToolbarCartButton = mFragmentHomeBinding.toolbar.findViewById(R.id.toolbarCartButton);
        mToolbar.setNavigationIcon(null);
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(mToolbar);
            activity.getSupportActionBar().setDisplayUseLogoEnabled(false);
            activity.getSupportActionBar().setTitle("");
        }

        mFragmentHomeBinding.serviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookingServiceActivity();
            }
        });

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
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this.getContext()));
        this.getActivity().finish();
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }
    @Override
    public void openBookingServiceActivity() {
        startActivity(ServicesStep1Activity.getStartIntent(this.getContext()));
        ServiceOrderInfo.getInstance().setUpData(ServiceOrderInfo.SERVICE_AIR_CON_CLEANING);
    }
}
