package com.appyhome.appyproduct.mvvm.ui.servicerequest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentRequestBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class RequestFragment extends BaseFragment<FragmentRequestBinding, RequestViewModel> implements RequestNavigator, View.OnClickListener {

    public static final String TAG = "NotificationFragment";

    @Inject
    RequestViewModel mRequestViewModel;

    @Inject
    RequestAdapter mRequestAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    FragmentRequestBinding mBinder;

    public static RequestFragment newInstance() {
        Bundle args = new Bundle();
        RequestFragment fragment = new RequestFragment();
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
        mRequestViewModel.setNavigator(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestViewModel);
        mBinder.requestRecyclerView.setLayoutManager(mLayoutManager);
        mBinder.requestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinder.requestRecyclerView.setAdapter(mRequestAdapter);
        mBinder.viewService.setOnClickListener(this);
        mBinder.viewOrder.setOnClickListener(this);
    }

    @Override
    public RequestViewModel getViewModel() {
        return mRequestViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_request;
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
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewOrder:
                mBinder.viewOrderTracking.setVisibility(View.VISIBLE);
                mBinder.viewSection.setVisibility(View.GONE);
                break;
            case R.id.viewService:
                mBinder.viewServiceTracking.setVisibility(View.VISIBLE);
                mBinder.viewSection.setVisibility(View.GONE);
                break;
        }
    }
}
