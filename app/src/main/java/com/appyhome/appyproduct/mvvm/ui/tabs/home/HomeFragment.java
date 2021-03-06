package com.appyhome.appyproduct.mvvm.ui.tabs.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.databinding.FragmentHomeBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.topic.ProductTopicFragment;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1.ServicesStep1Activity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.CompletedJobListener;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator, View.OnClickListener, CompletedJobListener {

    public static final String TAG = "HomeFragment";
    private final int[] mAppyServicesIds = {R.id.ibAirConServicing, R.id.ibElectricalService,
            R.id.ibHomeCleaning, R.id.ibPlumbingService};
    @Inject
    HomeViewModel mHomeViewModel;

    FragmentHomeBinding mBinder;

    @Inject
    int mLayoutId;

    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchToolbarViewHolder.onBind(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibAirConServicing:
                openBookingSteps(ServiceOrderUserInput.SERVICE_AIR_CON_CLEANING);
                break;
            case R.id.ibHomeCleaning:
                openBookingSteps(ServiceOrderUserInput.SERVICE_HOME_CLEANING);
                break;
            default: // Coming soon...
                if (isActivityRunning())
                    AlertManager.getInstance(getActivity()).showComingSoonDialog();
        }
    }

    private void openBookingSteps(int type) {
        mHomeViewModel.getDataManager().getServiceOrderUserInput().clear();
        mHomeViewModel.getDataManager().getServiceOrderUserInput().setUpData(type);
        startActivity(ServicesStep1Activity.getStartIntent(this.getContext()));
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
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mHomeViewModel);
        mBinder.setNavigator(this);
        setUpToolbar();
        ViewUtils.setOnClickListener(mBinder.serviceView, this, mAppyServicesIds);
        addProductTopicsFragment();
        getViewModel().fetchBanners();
    }

    private void setUpToolbar() {
        Toolbar vToolbar = mBinder.toolbar;
        vToolbar.setNavigationIcon(null);
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(vToolbar);
            activity.getSupportActionBar().setDisplayUseLogoEnabled(false);
            activity.getSupportActionBar().setTitle("");
        }
        mSearchToolbarViewHolder = new SearchToolbarViewHolder((BaseActivity) this.getActivity(), vToolbar, true, false, "");
        mSearchToolbarViewHolder.onBind(0);
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
    public void onJobCompleted(Object data) {
        mBinder.svContent.scrollTo(0, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void addProductTopicsFragment() {
        ProductTopicFragment fragment = ProductTopicFragment.newInstance();
        fragment.setCompletedJobListener(this);
        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(mBinder.categoryView.getId(), fragment, ProductTopicFragment.TAG)
                .commit();
    }

}
