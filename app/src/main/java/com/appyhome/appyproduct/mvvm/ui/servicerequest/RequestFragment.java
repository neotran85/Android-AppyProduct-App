package com.appyhome.appyproduct.mvvm.ui.servicerequest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.api.RequestResponse;
import com.appyhome.appyproduct.mvvm.databinding.FragmentRequestBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import java.util.ArrayList;

import javax.inject.Inject;

public class RequestFragment extends BaseFragment<FragmentRequestBinding, RequestViewModel> implements RequestNavigator {

    public static final String TAG = "NotificationFragment";

    @Inject
    RequestViewModel mRequestViewModel;

    private RequestAdapter mRequestAdapter;

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
        setTitle("Requests Service");
        mRequestViewModel.setNavigator(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinder = getViewDataBinding();
        mRequestAdapter = provideRequestAdapter();
        mBinder.setViewModel(mRequestViewModel);
        mBinder.requestRecyclerView.setLayoutManager(mLayoutManager);
        mBinder.requestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinder.requestRecyclerView.setAdapter(mRequestAdapter);
        mRequestViewModel.getAllAppointments();
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


    private RequestAdapter provideRequestAdapter() {
        ArrayList<RequestItemViewModel> array = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RequestItemViewModel item = new RequestItemViewModel(new RequestResponse.Request());
            array.add(item);
        }
        return new RequestAdapter(array);
    }
}
