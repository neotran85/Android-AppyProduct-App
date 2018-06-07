package com.appyhome.appyproduct.mvvm.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityMainBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.FavoriteFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.main.tab.AppyTabNavigator;
import com.appyhome.appyproduct.mvvm.ui.tabs.home.HomeFragment;
import com.appyhome.appyproduct.mvvm.ui.tabs.notification.NotificationFragment;
import com.appyhome.appyproduct.mvvm.ui.tabs.tracking.TrackingFragment;
import com.appyhome.appyproduct.mvvm.ui.tabs.userpage.UserPageFragment;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, AppyTabNavigator {

    public final static String TAB_HOME = "tab_home";
    public final static String TAB_NOTIFICATION = "tab_notification";
    public final static String TAB_TRACKING = "tab_tracking";
    public final static String TAB_WISH_LIST = "tab_wish_list";
    public final static String TAB_MY_PROFILE = "tab_my_profile";
    public final static String TAB = "tab";

    public final static int REQUEST_LOGIN_FOR_REQUEST_TRACKING = 1111;
    public final static int REQUEST_LOGIN_FOR_MY_PROFILE = 1112;
    public final static int REQUEST_LOGIN_FOR_MY_WISH_LIST = 1114;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityMainBinding mBinder;

    @Inject
    MainViewModel mMainViewModel;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        AppConstants.initiate(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        setUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeRealmDatabase();
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
        mBinder.tabLayout.setUp(this, this);
        Intent intent = getIntent();
        String tab = intent.getStringExtra(TAB);
        if (tab != null && tab.length() > 0) {
            clickTab(tab);
        } else {
            clickTab(TAB_HOME);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_LOGIN_FOR_MY_PROFILE:
                    clickTab(TAB_MY_PROFILE);
                    break;
                case REQUEST_LOGIN_FOR_REQUEST_TRACKING:
                    clickTab(TAB_TRACKING);
                    break;
                case REQUEST_LOGIN_FOR_MY_WISH_LIST:
                    clickTab(TAB_WISH_LIST);
                    break;
            }
        } else {
            clickTab(TAB_HOME);
        }
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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    private void showFragment(BaseFragment fragment, String tag) {
        this.showFragment(fragment, tag, R.id.screenView);
    }

    @Override
    public void showAlert(String message) {
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(message);
    }

    public void clickTab(String tagView) {
        View view = mBinder.tabLayout.findViewWithTag(tagView);
        mBinder.tabLayout.clickTab(view);
    }

    @Override
    public void onClickTab(View view) {
        switch (view.getId()) {
            case R.id.rlHome:
                showFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                break;
            case R.id.rlNotification:
                showFragment(NotificationFragment.newInstance(), NotificationFragment.TAG);
                break;
            case R.id.rlRequest:
                if (!askForLogin(getString(R.string.please_login_to_view_service_request),
                        REQUEST_LOGIN_FOR_REQUEST_TRACKING)) {
                    showFragment(TrackingFragment.newInstance(), TrackingFragment.TAG);
                }
                break;
            case R.id.rlWishList:
                if (!askForLogin(getString(R.string.please_login_view_wishlist), REQUEST_LOGIN_FOR_MY_WISH_LIST)) {
                    showFragment(FavoriteFragment.newInstance(), FavoriteFragment.TAG);
                }
                break;
            case R.id.rlMyProfile:
                if (!askForLogin(getString(R.string.please_login_see_your_profile), REQUEST_LOGIN_FOR_MY_PROFILE)) {
                    showFragment(UserPageFragment.newInstance(), UserPageFragment.TAG);
                }
                break;
        }
    }
}
