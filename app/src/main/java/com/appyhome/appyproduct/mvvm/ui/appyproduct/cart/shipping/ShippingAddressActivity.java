package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductShippingBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressAdapter;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import dagger.android.support.HasSupportFragmentInjector;
import io.realm.RealmResults;

public class ShippingAddressActivity extends BaseActivity<ActivityProductShippingBinding, ShippingAddressViewModel> implements ShippingAddressNavigator {

    @Inject
    ShippingAddressViewModel mMainViewModel;
    ActivityProductShippingBinding mBinder;
    AddressAdapter mAdapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ShippingAddressActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        setUpRecyclerViewList(mBinder.rvAddressList);
        mMainViewModel.getAllShippingAddress();
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

    @Override
    public void showAddressList(RealmResults<Address> addresses) {
        mAdapter = new AddressAdapter(addresses);
        mBinder.rvAddressList.setAdapter(mAdapter);
    }

    private void setUpRecyclerViewList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public ShippingAddressViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_shipping;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }
}
