package com.appyhome.appyproduct.mvvm.ui.tabs.mywishlist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentMyWishlistBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class MyWishListFragment extends BaseFragment<FragmentMyWishlistBinding, MyWishListViewModel> implements MyWishListNavigator {

    public static final String TAG = "MyWishListFragment";

    @Inject
    MyWishListViewModel mMyWishListViewModel;

    FragmentMyWishlistBinding mBinder;

    @Inject
    int mLayoutId;

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    public static MyWishListFragment newInstance() {
        Bundle args = new Bundle();
        MyWishListFragment fragment = new MyWishListFragment();
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
        setTitle("My Wish List");
        mMyWishListViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMyWishListViewModel);
    }

    @Override
    public MyWishListViewModel getViewModel() {
        return mMyWishListViewModel;
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
