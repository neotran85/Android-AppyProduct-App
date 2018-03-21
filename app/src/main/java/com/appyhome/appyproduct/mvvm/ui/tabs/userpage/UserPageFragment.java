package com.appyhome.appyproduct.mvvm.ui.tabs.userpage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.FragmentUserPageBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;

import javax.inject.Inject;

public class UserPageFragment extends BaseFragment<FragmentUserPageBinding, UserPageViewModel> implements UserPageNavigator {

    public static final String TAG = "UserPageFragment";

    @Inject
    UserPageViewModel mUserPageViewModel;

    FragmentUserPageBinding mBinder;

    @Inject
    int mLayoutId;

    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    public static UserPageFragment newInstance() {
        Bundle args = new Bundle();
        UserPageFragment fragment = new UserPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchToolbarViewHolder.onBind(0);
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
        mUserPageViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mUserPageViewModel);
        mBinder.setNavigator(this);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder((BaseActivity) this.getActivity(), mBinder.llCartIcon, false, false);
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
    public void backToHomeScreen() {
        Intent i = MainActivity.getStartIntent(this.getActivity());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void logout() {
        getViewModel().logout();
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }
}
