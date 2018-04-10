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
import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.FavoriteFragment;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.tabs.home.HomeFragment;
import com.appyhome.appyproduct.mvvm.ui.tabs.notification.NotificationFragment;
import com.appyhome.appyproduct.mvvm.ui.tabs.userpage.UserPageFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, View.OnClickListener {

    public final static int TAB_HOME = 0;
    public final static int TAB_NOTIFICATION = 1;
    public final static int TAB_REQUEST = 2;
    public final static int TAB_WISH_LIST = 3;
    public final static int TAB_MY_PROFILE = 4;
    public final static String TAB = "tab";

    public final static int REQUEST_LOGIN_FOR_REQUEST_TRACKING = 1111;
    public final static int REQUEST_LOGIN_FOR_MY_PROFILE = 1112;
    public final static int REQUEST_LOGIN_FOR_MY_WISH_LIST = 1113;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityMainBinding mBinder;

    @Inject
    MainViewModel mMainViewModel;

    private View currentTab;

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
    protected void onResume() {
        super.onResume();
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
        ViewUtils.setOnClickListener(this, mBinder.rlHome, mBinder.rlMyProfile,
                mBinder.rlNotification, mBinder.rlRequest, mBinder.rlWishList);
        currentTab = mBinder.rlHome;
        showFragment(HomeFragment.newInstance(), HomeFragment.TAG);
        Intent intent = getIntent();
        int tab = intent.getIntExtra(TAB, -1);
        switch (tab) {
            case TAB_HOME:
                onClick(mBinder.rlHome);
                break;
            case TAB_NOTIFICATION:
                onClick(mBinder.rlNotification);
                break;
            case TAB_REQUEST:
                onClick(mBinder.rlRequest);
                break;
            case TAB_WISH_LIST:
                onClick(mBinder.rlWishList);
                break;
            case TAB_MY_PROFILE:
                onClick(mBinder.rlMyProfile);
                break;
            default:
                onClick(mBinder.rlHome);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOGIN_FOR_MY_PROFILE:
                if (resultCode == RESULT_OK) {
                    onClick(mBinder.rlMyProfile);
                }
                break;
            case REQUEST_LOGIN_FOR_REQUEST_TRACKING:
                if (resultCode == RESULT_OK) {
                    onClick(mBinder.rlRequest);
                }
                break;
            case REQUEST_LOGIN_FOR_MY_WISH_LIST:
                if (resultCode == RESULT_OK) {
                    onClick(mBinder.rlWishList);
                }
                break;
        }
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
                if (!askForLogin(getString(R.string.please_login_to_view_service_request),
                        REQUEST_LOGIN_FOR_REQUEST_TRACKING)) {
                    switchTabSelection(view);
                    showFragment(RequestFragment.newInstance(), RequestFragment.TAG);
                }
                break;
            case R.id.rlWishList:
                if (!askForLogin(getString(R.string.please_login_view_wishlist), REQUEST_LOGIN_FOR_MY_WISH_LIST)) {
                    switchTabSelection(view);
                    showFragment(FavoriteFragment.newInstance(), FavoriteFragment.TAG);
                }
                break;
            case R.id.rlMyProfile:
                if (!askForLogin(getString(R.string.please_login_see_your_profile), REQUEST_LOGIN_FOR_MY_PROFILE)) {
                    switchTabSelection(view);
                    showFragment(UserPageFragment.newInstance(), UserPageFragment.TAG);
                }
                break;
        }
    }

    private void openLoginActivity(String message, int requestCode) {
        Intent intent = LoginActivity.getStartIntent(this);
        intent.putExtra("message", message);
        startActivityForResult(intent, requestCode);
    }

    private void switchTabSelection(View view) {
        highLightTab(currentTab, view);
        currentTab = view;
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

    private void highLightTab(View oldView, View newView) {
        if (oldView != null) {
            View oldHighlightView = oldView.findViewWithTag("highlight");
            oldHighlightView.setVisibility(View.GONE);
        }
        if (newView != null) {
            View newHighLightView = newView.findViewWithTag("highlight");
            newHighLightView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }
}
