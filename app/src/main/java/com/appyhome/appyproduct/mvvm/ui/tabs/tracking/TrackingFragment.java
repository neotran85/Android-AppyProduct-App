package com.appyhome.appyproduct.mvvm.ui.tabs.tracking;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.FragmentTrackingBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list.ListProductOrderActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.ServiceTrackingActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class TrackingFragment extends BaseFragment<FragmentTrackingBinding, TrackingViewModel> implements TrackingNavigator {

    public static final String TAG = "TrackingFragment";

    @Inject
    TrackingViewModel mViewModel;

    FragmentTrackingBinding mBinder;

    @Inject
    int mLayoutId;

    public static TrackingFragment newInstance() {
        Bundle args = new Bundle();
        TrackingFragment fragment = new TrackingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
    }

    @Override
    public TrackingViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void openOrderTracking() {
        Intent intent = ListProductOrderActivity.getStartIntent(this.getActivity());
        startActivity(intent);
    }

    @Override
    public void openServiceTracking() {
        Intent intent = ServiceTrackingActivity.getStartIntent(this.getActivity());
        startActivity(intent);
    }
}
