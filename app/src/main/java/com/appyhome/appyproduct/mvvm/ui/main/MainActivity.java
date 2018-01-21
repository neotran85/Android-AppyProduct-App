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
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.home.HomeFragment;
import com.appyhome.appyproduct.mvvm.ui.myprofile.MyProfileFragment;
import com.appyhome.appyproduct.mvvm.ui.notification.NotificationFragment;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;

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
    ActivityMainBinding mBinder;
    private MainViewModel mMainViewModel;

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
        showFragment(HomeFragment.newInstance(), HomeFragment.TAG);
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
        TabLayout tabs = mBinder.tabs;
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                AppLogger.d("onTabSelected = " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        showFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                        break;
                    case 1:
                        showFragment(NotificationFragment.newInstance(), NotificationFragment.TAG);
                        break;
                    case 2:
                        break;
                    case 3:
                        showFragment(RequestFragment.newInstance(), RequestFragment.TAG);
                        break;
                    case 4:
                        showFragment(MyProfileFragment.newInstance(), MyProfileFragment.TAG);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });
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
}
