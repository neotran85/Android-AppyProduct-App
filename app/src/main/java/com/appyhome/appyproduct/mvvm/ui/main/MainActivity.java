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
import com.appyhome.appyproduct.mvvm.ui.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.ui.myprofile.MyProfileFragment;
import com.appyhome.appyproduct.mvvm.ui.mywishlist.MyWishListFragment;
import com.appyhome.appyproduct.mvvm.ui.notification.NotificationFragment;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestFragment;
import com.crashlytics.android.Crashlytics;

import java.util.List;

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

    public final static int REQUEST_LOGIN_FOR_REQUEST_TRACKING= 1111;
    public final static int REQUEST_LOGIN_FOR_MY_PROFILE = 1112;

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
                if(resultCode == RESULT_OK) {
                    onClick(mBinder.rlMyProfile);
                }
                break;
            case REQUEST_LOGIN_FOR_REQUEST_TRACKING:
                if(resultCode == RESULT_OK) {
                    onClick(mBinder.rlRequest);
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
                if (mMainViewModel.isUserLoggedIn()) {
                    switchTabSelection(view);
                    showFragment(RequestFragment.newInstance(), RequestFragment.TAG);
                } else {
                    openLoginActivity(getString(R.string.login_required_message) + " see your service requests.",
                            REQUEST_LOGIN_FOR_REQUEST_TRACKING);
                }
                break;
            case R.id.rlWishList:
                switchTabSelection(view);
                showFragment(MyWishListFragment.newInstance(), MyWishListFragment.TAG);
                break;
            case R.id.rlMyProfile:
                if (mMainViewModel.isUserLoggedIn()) {
                    switchTabSelection(view);
                    showFragment(MyProfileFragment.newInstance(), MyProfileFragment.TAG);
                } else {
                    openLoginActivity(getString(R.string.login_required_message) + " see your profile.",
                            REQUEST_LOGIN_FOR_MY_PROFILE);
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
        if (oldView != null) {
            View oldHighlightView = oldView.findViewWithTag("highlight");
            oldHighlightView.setVisibility(View.GONE);
        }
        if (newView != null) {
            View newHighLightView = newView.findViewWithTag("highlight");
            newHighLightView.setVisibility(View.VISIBLE);
        }
    }
}
