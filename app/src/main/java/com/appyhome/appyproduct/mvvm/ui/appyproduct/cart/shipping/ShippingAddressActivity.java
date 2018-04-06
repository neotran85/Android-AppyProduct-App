package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductShippingBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress.NewAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ShippingAddressActivity extends BaseActivity<ActivityProductShippingBinding, ShippingAddressViewModel> implements ShippingAddressNavigator, AddressItemNavigator {

    @Inject
    ShippingAddressViewModel mMainViewModel;

    @Inject
    AddressAdapter mAdapter;

    ActivityProductShippingBinding mBinder;

    boolean isEditMode = false;

    boolean isEmptyAddress = true;

    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ShippingAddressActivity.class);
        return intent;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mMainViewModel);
        mBinder.setNavigator(this);

        mMainViewModel.setNavigator(this);
        ViewUtils.setUpRecyclerViewList(mBinder.rvAddressList, true);
        isEditMode = getIntent().getBooleanExtra("edit_mode", false);
        mMainViewModel.isEditMode.set(isEditMode);
    }

    @Override
    public void openNewShippingAddress() {
        Intent intent = NewAddressActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void gotoNextStep() {
        if (isEmptyAddress) {
            showAlert(getString(R.string.please_add_address));
            return;
        }
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
        isEmptyAddress = addresses.size() <= 0;
        mAdapter.addItems(addresses, this);
        mBinder.rvAddressList.setAdapter(mAdapter);
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
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    public void updateDatabaseCompleted() {}

    @Override
    public void onItemChecked(AddressItemViewModel item) {
        mAdapter.selectAddress(item);
    }
}
