package com.appyhome.appyproduct.mvvm.ui.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentHomeBinding;
import com.appyhome.appyproduct.mvvm.ui.about.AboutFragment;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.ui.login.LoginActivity;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator {

    public static final String TAG = "HomeFragment";

    @Inject
    HomeViewModel mHomeViewModel;
    FragmentHomeBinding mFragmentHomeBinding;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;

    private ImageButton mToolbarCartButton;
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
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
        mHomeViewModel.setNavigator(this);
        mFragmentHomeBinding = getViewDataBinding();

        mDrawer = mFragmentHomeBinding.drawerView;
        mToolbar = mFragmentHomeBinding.toolbar;
        mNavigationView = mFragmentHomeBinding.navigationView;
        mNavigationView.setItemIconTintList(null);
        mToolbarCartButton = mFragmentHomeBinding.toolbar.findViewById(R.id.toolbarCartButton);
        mToolbar.setNavigationIcon(null);
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(mToolbar);
            activity.getSupportActionBar().setDisplayUseLogoEnabled(false);
            activity.getSupportActionBar().setTitle("");
        }

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this.getActivity(),
                mDrawer,
                null,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            default:
                                return false;
                        }
                    }
                });
    }

    private void lockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void unlockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void showAboutFragment() {
        lockDrawer();
        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
    }

    @Override
    public HomeViewModel getViewModel() {
        return mHomeViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
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
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this.getContext()));
        this.getActivity().finish();
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }
}
