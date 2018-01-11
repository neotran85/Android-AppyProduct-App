package com.appyhome.appyproduct.mvvm.ui.notification;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentNotificationBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class NotificationFragment extends BaseFragment<FragmentNotificationBinding, NotificationViewModel> implements NotificationNavigator {

    public static final String TAG = "NotificationFragment";

    @Inject
    NotificationViewModel mNotificationViewModel;
    FragmentNotificationBinding mFragmentNotificationBinding;

    public static NotificationFragment newInstance() {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
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
        mNotificationViewModel.setNavigator(this);
        mFragmentNotificationBinding = getViewDataBinding();
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
    public int getLayoutId() {
        return R.layout.fragment_notification;
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
