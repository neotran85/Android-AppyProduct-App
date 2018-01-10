package com.appyhome.appyproduct.mvvm.ui.myprofile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentMyProfileBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class MyProfileFragment extends BaseFragment<FragmentMyProfileBinding, MyProfileViewModel> implements MyProfileNavigator {

    public static final String TAG = "MyProfileFragment";

    @Inject
    MyProfileViewModel mMyProfileViewModel;
    FragmentMyProfileBinding mFragmentMyProfileBinding;

    public static MyProfileFragment newInstance() {
        Bundle args = new Bundle();
        MyProfileFragment fragment = new MyProfileFragment();
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
        mMyProfileViewModel.setNavigator(this);
        mFragmentMyProfileBinding = getViewDataBinding();
    }

    @Override
    public MyProfileViewModel getViewModel() {
        return mMyProfileViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_profile;
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
