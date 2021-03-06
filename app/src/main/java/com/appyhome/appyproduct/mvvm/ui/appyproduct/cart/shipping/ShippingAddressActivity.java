package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductShippingBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress.NewAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ShippingAddressActivity extends BaseActivity<ActivityProductShippingBinding, ShippingAddressViewModel> implements ShippingAddressNavigator {

    @Inject
    protected ShippingAddressViewModel mViewModel;

    @Inject
    protected AddressAdapter mAdapter;

    protected ActivityProductShippingBinding mBinder;

    protected boolean isEditMode = false;

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
        return R.layout.activity_product_shipping;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);

        mViewModel.setNavigator(this);
        ViewUtils.setUpRecyclerViewListVertical(mBinder.rvAddressList, true);
        isEditMode = getIntent().getBooleanExtra("edit_mode", false);
        mViewModel.isEditMode.set(isEditMode);
        mBinder.rvAddressList.setAdapter(mAdapter);
    }

    @Override
    public void openNewShippingAddress() {
        Intent intent = NewAddressActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void gotoNextStep() {
        if (mAdapter == null || mAdapter.getItemSize() == 0) {
            showAlert(getString(R.string.please_add_address));
            return;
        }
        getViewModel().updateShippingFees();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getAllShippingAddress();
    }

    @Override
    public void showAddressList(RealmResults<AppyAddress> addresses) {
        if (addresses != null) {
            mAdapter.addItems(addresses, mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public ShippingAddressViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(message, R.style.AppyToast_Cart);
    }

    @Override
    public void updateShippingFees_DONE() {
        if (isEditMode) {
            finish();
        } else {
            startActivity(PaymentActivity.getStartIntent(this));
        }
    }
}
