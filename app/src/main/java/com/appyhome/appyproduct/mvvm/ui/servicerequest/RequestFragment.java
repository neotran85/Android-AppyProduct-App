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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import javax.inject.Inject;

public class RequestFragment extends BaseFragment<FragmentRequestBinding, RequestViewModel> implements RequestNavigator {

    public static final String TAG = "RequestFragment";

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
        mBinder.setViewModel(mRequestViewModel);
        mBinder.requestRecyclerView.setLayoutManager(mLayoutManager);
        mBinder.requestRecyclerView.setItemAnimator(new DefaultItemAnimator());
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

    @Override
    public void showResultList(ArrayList<RequestItemViewModel> array) {
        mRequestAdapter = new RequestAdapter(array);
        mBinder.requestRecyclerView.setAdapter(mRequestAdapter);
        mRequestAdapter.notifyDataSetChanged();
    }
}
