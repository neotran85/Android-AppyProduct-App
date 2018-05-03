package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.shipping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductShippingChooseBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress.NewAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ChooseShippingAddressActivity extends BaseActivity<ActivityProductShippingChooseBinding, ShippingAddressViewModel> implements ShippingAddressNavigator, AddressItemNavigator {

    public static final int REQUEST_NEW_ADDRESS = 12;

    protected ActivityProductShippingChooseBinding mBinder;

    @Inject
    protected ShippingAddressViewModel mViewModel;

    @Inject
    protected AddressAdapter mAdapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ChooseShippingAddressActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_shipping_choose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(true);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        mViewModel.setNavigator(this);
        ViewUtils.setUpRecyclerViewListVertical(mBinder.rvAddressList, true);
        mViewModel.isEditMode.set(false);
        mBinder.rvAddressList.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getWindow() != null)
            getWindow().setLayout(AppConstants.SCREEN_WIDTH * 95 / 100, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    public void updateDatabaseCompleted() {
        // DO NOTHING HERE
    }

    @Override
    public void onItemChecked(AddressItemViewModel item) {
        mAdapter.selectAddress(item);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void showAddressList(RealmResults<AppyAddress> addresses) {
        if (addresses != null) {
            mAdapter.addItems(addresses, this);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void gotoNextStep() {
        // DO NOTHING HERE
    }

    @Override
    public void openNewShippingAddress() {
        Intent intent = NewAddressActivity.getStartIntent(this);
        startActivityForResult(intent, REQUEST_NEW_ADDRESS);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getAllShippingAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEW_ADDRESS && resultCode == RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
