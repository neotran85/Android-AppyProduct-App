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
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress.NewAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import dagger.android.support.HasSupportFragmentInjector;
import io.realm.RealmResults;

public class ShippingAddressActivity extends BaseActivity<ActivityProductShippingBinding, ShippingAddressViewModel> implements ShippingAddressNavigator {

    @Inject
    ShippingAddressViewModel mMainViewModel;
    ActivityProductShippingBinding mBinder;
    AddressAdapter mAdapter;

    boolean isEditMode = false;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ShippingAddressActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMainViewModel);
        mBinder.setNavigator(this);
        mMainViewModel.setNavigator(this);
        setUpRecyclerViewList(mBinder.rvAddressList);
        isEditMode = getIntent().getBooleanExtra("edit_mode", false);
    }

    @Override
    public void openNewShippingAddress() {
        Intent intent = NewAddressActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void gotoNextStep() {
        if (isEditMode) {
            finish();
        } else
            startActivity(PaymentActivity.getStartIntent(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainViewModel.getAllShippingAddress();
    }

    @Override
    public void showAddressList(RealmResults<Address> addresses) {
        mAdapter = new AddressAdapter(addresses, mMainViewModel);
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
