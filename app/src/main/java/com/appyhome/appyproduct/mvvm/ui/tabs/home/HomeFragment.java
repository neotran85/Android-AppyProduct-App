package com.appyhome.appyproduct.mvvm.ui.tabs.home;


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
import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1.ServicesStep1Activity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator, View.OnClickListener {

    public static final String TAG = "HomeFragment";
    private final int[] mAppyServicesIds = {R.id.ibAirConServicing, R.id.ibElectricalService,
            R.id.ibHomeCleaning, R.id.ibPlumbingService};
    private final int[] mAppyProductsIds = {R.id.ibBedAndBath, R.id.ibDecor,
            R.id.ibKitchen, R.id.ibAppliances, R.id.ibFurniture,
            R.id.ibHomeImprovement, R.id.ibLighting, R.id.ibStorageAndOrganisation};
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

    private void openLoginActivity(String message, int requestCode) {
        Intent intent = LoginActivity.getStartIntent(getActivity());
        intent.putExtra("message", message);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibAirConServicing:
                openBookingSteps(ServiceOrderUserInput.SERVICE_AIR_CON_CLEANING);
                break;
            case R.id.ibHomeCleaning:
                openBookingSteps(ServiceOrderUserInput.SERVICE_HOME_CLEANING);
                break;
            default: // Coming soon...
                AlertManager.getInstance(getActivity()).showComingSoonDialog();
        }
    }

    private void openBookingSteps(int type) {
        mHomeViewModel.getDataManager().getServiceOrderUserInput().clear();
        mHomeViewModel.getDataManager().getServiceOrderUserInput().setUpData(type);
        startActivity(ServicesStep1Activity.getStartIntent(this.getContext()));
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
        ViewUtils.setOnClickListener(mBinder.serviceView, this, mAppyServicesIds);
        ViewUtils.setOnClickListener(mBinder.categoryView, this, mAppyProductsIds);
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