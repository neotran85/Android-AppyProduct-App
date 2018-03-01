package com.appyhome.appyproduct.mvvm.ui.common.sample.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentSampleBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class SampleTopicFragment extends BaseFragment<FragmentSampleBinding, SampleViewModel> implements SampleNavigator {

    public static final String TAG = "SampleTopicFragment";

    @Inject
    SampleViewModel mViewModel;
    FragmentSampleBinding mBinder;

    public static SampleTopicFragment newInstance() {
        Bundle args = new Bundle();
        SampleTopicFragment fragment = new SampleTopicFragment();
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
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
    }

    @Override
    public SampleViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sample;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
