package com.appyhome.appyproduct.mvvm.ui.tabs.userpage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentUserPageBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class UserPageFragment extends BaseFragment<FragmentUserPageBinding, UserPageViewModel> implements UserPageNavigator {

    public static final String TAG = "UserPageFragment";

    @Inject
    UserPageViewModel mUserPageViewModel;

    FragmentUserPageBinding mBinder;

    @Inject
    int mLayoutId;

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    public static UserPageFragment newInstance() {
        Bundle args = new Bundle();
        UserPageFragment fragment = new UserPageFragment();
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
        mUserPageViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mUserPageViewModel);
    }

    @Override
    public UserPageViewModel getViewModel() {
        return mUserPageViewModel;
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
