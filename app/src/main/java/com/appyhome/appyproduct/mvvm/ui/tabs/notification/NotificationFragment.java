package com.appyhome.appyproduct.mvvm.ui.tabs.notification;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.FragmentNotificationBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class NotificationFragment extends BaseFragment<FragmentNotificationBinding, NotificationViewModel> implements NotificationNavigator {

    public static final String TAG = "NotificationFragment";

    @Inject
    NotificationViewModel mNotificationViewModel;

    @Inject
    NotificationAdapter mNotificationAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    FragmentNotificationBinding mBinder;

    @Inject
    int mLayoutId;

    public static NotificationFragment newInstance() {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUp() {
        setTitle("Notification");
        mNotificationViewModel.setNavigator(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mNotificationViewModel);
        mBinder.notificationRecyclerView.setLayoutManager(mLayoutManager);
        mBinder.notificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinder.notificationRecyclerView.setAdapter(mNotificationAdapter);
    }

    @Override
    public NotificationViewModel getViewModel() {
        return mNotificationViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
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
}
