package com.appyhome.appyproduct.mvvm.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.BuildConfig;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.others.QuestionCardData;
import com.appyhome.appyproduct.mvvm.databinding.ActivityMainBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.home.HomeFragment;
import com.appyhome.appyproduct.mvvm.ui.myprofile.MyProfileFragment;
import com.appyhome.appyproduct.mvvm.ui.mywishlist.MyWishListFragment;
import com.appyhome.appyproduct.mvvm.ui.notification.NotificationFragment;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, View.OnClickListener {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    ActivityMainBinding mBinder;
    private MainViewModel mMainViewModel;

    private View currentTab;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConstants.initiate(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
        }
    }

    private void setUp() {
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mMainViewModel.updateAppVersion(version);
        mMainViewModel.onNavMenuCreated();
        subscribeToLiveData();
        mBinder.rlHome.setOnClickListener(this);
        mBinder.rlMyProfile.setOnClickListener(this);
        mBinder.rlNotification.setOnClickListener(this);
        mBinder.rlRequest.setOnClickListener(this);
        mBinder.rlWishList.setOnClickListener(this);
        currentTab = mBinder.rlHome;
        showFragment(HomeFragment.newInstance(), HomeFragment.TAG);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlHome:
                switchTabSelection(view);
                showFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                break;
            case R.id.rlNotification:
                switchTabSelection(view);
                showFragment(NotificationFragment.newInstance(), NotificationFragment.TAG);
                break;
            case R.id.rlRequest:
                switchTabSelection(view);
                showFragment(RequestFragment.newInstance(), RequestFragment.TAG);
                break;
            case R.id.rlWishList:
                switchTabSelection(view);
                showFragment(MyWishListFragment.newInstance(), MyWishListFragment.TAG);
                break;
            case R.id.rlMyProfile:
                switchTabSelection(view);
                showFragment(MyProfileFragment.newInstance(), MyProfileFragment.TAG);
                break;
        }
    }
    private void switchTabSelection(View view) {
        highLightTab(currentTab, view);
        currentTab = view;
    }
    private void subscribeToLiveData() {
        mMainViewModel.getQuestionCardData().observe(this, new Observer<List<QuestionCardData>>() {
            @Override
            public void onChanged(@Nullable List<QuestionCardData> questionCardDatas) {
                mMainViewModel.setQuestionDataList(questionCardDatas);
            }
        });
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    private void showFragment(BaseFragment fragment, String tag) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.screenView, fragment, tag)
                .commit();
    }

    private void highLightTab(View oldView, View newView) {
        View oldHighlightView = oldView.findViewWithTag("highlight");
        oldHighlightView.setVisibility(View.GONE);
        View newHighLightView = newView.findViewWithTag("highlight");
        newHighLightView.setVisibility(View.VISIBLE);
    }
}
