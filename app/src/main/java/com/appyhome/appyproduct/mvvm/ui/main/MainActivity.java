package com.appyhome.appyproduct.mvvm.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.BuildConfig;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.others.QuestionCardData;
import com.appyhome.appyproduct.mvvm.databinding.ActivityMainBinding;
import com.appyhome.appyproduct.mvvm.ui.about.AboutFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.home.HomeFragment;
import com.appyhome.appyproduct.mvvm.ui.myprofile.MyProfileFragment;
import com.appyhome.appyproduct.mvvm.ui.mywishlist.MyWishListFragment;
import com.appyhome.appyproduct.mvvm.ui.userpage.UserPageFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    ActivityMainBinding mActivityMainBinding;
    private MainViewModel mMainViewModel;
    private TabLayout mTabLayout;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        setUp();
        //showHomeFragment();
        //showMyProfileFragment();
        //showUserPageFragment();
        showMyWishListFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG);
        if (fragment == null) {
            super.onBackPressed();
        } else {
            onFragmentDetached(AboutFragment.TAG);
        }
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
        AppConstants.initiate(this);
        mTabLayout = mActivityMainBinding.tabs;
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mMainViewModel.updateAppVersion(version);
        mMainViewModel.onNavMenuCreated();
        subscribeToLiveData();
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

    private void showMyProfileFragment() {
        this.getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.screenView, MyProfileFragment.newInstance(), MyProfileFragment.TAG)
                .commit();
    }
    private void showHomeFragment() {
        this.getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.screenView, HomeFragment.newInstance(), HomeFragment.TAG)
                .commit();
    }

    private void showUserPageFragment() {
        this.getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.screenView, UserPageFragment.newInstance(), UserPageFragment.TAG)
                .commit();
    }
    private void showMyWishListFragment() {
        this.getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.screenView, MyWishListFragment.newInstance(), MyWishListFragment.TAG)
                .commit();
    }
}
