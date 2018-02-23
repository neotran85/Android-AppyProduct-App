package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest;


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
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.detail.RequestDetailActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;

import java.util.ArrayList;

import javax.inject.Inject;

public class RequestFragment extends BaseFragment<FragmentRequestBinding, RequestViewModel> implements RequestNavigator, View.OnClickListener, RequestAdapter.OnItemClickListener {

    public static final String TAG = "RequestFragment";

    @Inject
    RequestViewModel mRequestViewModel;
    FragmentRequestBinding mBinder;
    private Button mCurrentButton;
    private RecyclerView[] mArrayRecycleView = null;
    private RecyclerView mCurrentRecyclerView;
    private int[] mRequestTypes = {RequestType.TYPE_ORDER, RequestType.TYPE_REQUEST, RequestType.TYPE_CLOSED};
    private Button[] mTabs = null;

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
        changeTabSelection((Button) view, getRecycleByTab(view.getId()));
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

    private void setUpRecyclerView(RecyclerView rv, int type) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        RequestAdapter adapter = new RequestAdapter(null, type);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
    }

    private RecyclerView getRecycleByTab(int idTab) {
        int index = 0;
        for (int i = 0; i < mTabs.length; i++) {
            if (mTabs[i].getId() == idTab)
                index = i;
        }
        return mArrayRecycleView[index];
    }

    private RequestAdapter getAdapterByType(int type) {
        int index = 0;
        for (int i = 0; i < mRequestTypes.length; i++) {
            if (mRequestTypes[i] == type)
                index = i;
        }
        return (RequestAdapter) mArrayRecycleView[index].getAdapter();
    }

    private void setUp() {
        setTitle(getString(R.string.title_service_request_tracking));
        mRequestViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestViewModel);
        mArrayRecycleView = new RecyclerView[]{mBinder.requestRecyclerView, mBinder.ordersRecyclerView, mBinder.closedRecyclerView};
        for (int i = 0; i < mArrayRecycleView.length; i++) {
            setUpRecyclerView(mArrayRecycleView[i], mRequestTypes[i]);
        }
        mTabs = new Button[]{mBinder.btRequest, mBinder.btOrders, mBinder.btClosed};
        ViewUtils.setOnClickListener(this, mTabs);
        changeTabSelection(mBinder.btRequest, mBinder.requestRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < mRequestTypes.length; i++) {
            mRequestViewModel.fetchData(mRequestTypes[i]);
        }
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
        getAdapterByType(type).updateData(array);
        getAdapterByType(type).notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int type) {
        Intent intent = RequestDetailActivity.getStartIntent(this.getActivity());
        String idNumber = view.getTag().toString();
        intent.putExtra("id", idNumber);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
