package com.appyhome.appyproduct.mvvm.ui.servicerequest;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentRequestBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.detail.RequestDetailActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;

import java.util.ArrayList;

import javax.inject.Inject;

public class RequestFragment extends BaseFragment<FragmentRequestBinding, RequestViewModel> implements RequestNavigator, View.OnClickListener, RequestAdapter.OnItemClickListener {

    public static final String TAG = "RequestFragment";

    @Inject
    RequestViewModel mRequestViewModel;

    private RequestAdapter mRequestAdapter;
    private RequestAdapter mOrdersAdapter;
    private RequestAdapter mCloseAdapter;

    FragmentRequestBinding mBinder;

    private Button mCurrentButton;

    private RecyclerView mCurrentRecyclerView;

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btClosed:
                changeTabSelection((Button) view, mBinder.closedRecyclerView);
                break;
            case R.id.btOrders:
                changeTabSelection((Button) view, mBinder.ordersRecyclerView);
                break;
            case R.id.btRequest:
                changeTabSelection((Button) view, mBinder.requestRecyclerView);
                break;
        }
    }

    private void changeTabSelection(Button view, RecyclerView recyclerView) {
        if (view != null) {
            if (mCurrentButton != null) {
                mCurrentButton.setBackgroundColor(ContextCompat.getColor(this.getActivity(), R.color.white));
                mCurrentButton.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.semi_gray));
            }
            view.setBackgroundColor(ContextCompat.getColor(this.getActivity(), R.color.colorAccent));
            view.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.white));
            mCurrentButton = view;
        }
        if (mCurrentRecyclerView != null)
            mCurrentRecyclerView.setVisibility(View.GONE);
        mCurrentRecyclerView = recyclerView;
        mCurrentRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUp() {
        setTitle("Requests Service");
        mRequestViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestViewModel);

        mBinder.requestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mBinder.requestRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mBinder.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mBinder.ordersRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mBinder.closedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mBinder.closedRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRequestViewModel.fetchData(RequestType.TYPE_REQUEST);
        mRequestViewModel.fetchData(RequestType.TYPE_ORDER);
        mRequestViewModel.fetchData(RequestType.TYPE_CLOSED);

        mRequestAdapter = new RequestAdapter(null, RequestType.TYPE_REQUEST);
        mCloseAdapter = new RequestAdapter(null, RequestType.TYPE_CLOSED);
        mOrdersAdapter = new RequestAdapter(null, RequestType.TYPE_ORDER);

        mBinder.requestRecyclerView.setAdapter(mRequestAdapter);
        mBinder.ordersRecyclerView.setAdapter(mOrdersAdapter);
        mBinder.closedRecyclerView.setAdapter(mCloseAdapter);

        mBinder.ordersRecyclerView.setVisibility(View.GONE);
        mBinder.closedRecyclerView.setVisibility(View.VISIBLE);
        mBinder.requestRecyclerView.setVisibility(View.GONE);

        mBinder.btClosed.setOnClickListener(this);
        mBinder.btRequest.setOnClickListener(this);
        mBinder.btOrders.setOnClickListener(this);

        changeTabSelection(mBinder.btRequest, mBinder.requestRecyclerView);
        mRequestAdapter.setOnItemClickListener(this);
        mOrdersAdapter.setOnItemClickListener(this);
        mCloseAdapter.setOnItemClickListener(this);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showResultList(ArrayList<RequestItemViewModel> array, int type) {
        AppLogger.d("showResultList " + type);
        if (type == RequestType.TYPE_REQUEST) {
            mRequestAdapter.updateData(array);
            mRequestAdapter.notifyDataSetChanged();
        }
        if (type == RequestType.TYPE_ORDER) {
            mOrdersAdapter.updateData(array);
            mOrdersAdapter.notifyDataSetChanged();
        }
        if (type == RequestType.TYPE_CLOSED) {
            mCloseAdapter.updateData(array);
            mCloseAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onItemClick(View view, int type) {
        Intent intent = RequestDetailActivity.getStartIntent(this.getActivity());
        String detail = view.getTag().toString();
        AppLogger.d("onItemClick: " + detail);
        intent.putExtra("detail", detail);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
