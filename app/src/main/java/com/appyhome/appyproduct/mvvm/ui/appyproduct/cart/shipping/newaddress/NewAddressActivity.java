package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductShippingNewBinding;
import com.appyhome.appyproduct.mvvm.databinding.ActivitySampleBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.MapManager;

import javax.inject.Inject;

public class NewAddressActivity extends BaseActivity<ActivityProductShippingNewBinding, NewAddressViewModel> implements NewAddressNavigator, View.OnClickListener {

    ActivityProductShippingNewBinding mBinder;

    @Inject
    public NewAddressViewModel mMainViewModel;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, NewAddressActivity.class);
        return intent;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llSearchLocation:
                MapManager.openMapForPlaceSelection(this);
                break;
            case R.id.btSave:
                if(!mMainViewModel.checkIfContactInputted()) {
                    showAlert(getString(R.string.please_input_contact));
                    return;
                }
                if(!mMainViewModel.isPhoneNumberValid()) {
                    showAlert(getString(R.string.please_input_valid_phone));
                    return;
                }
                if(!mMainViewModel.checkIfLocationInputted()) {
                    showAlert(getString(R.string.please_input_shipping_address));
                    return;
                }
                mMainViewModel.saveShippingAddress();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MapManager.PLACE_PICKER_REQUEST:
                    mMainViewModel.updateAddressFromGooglePlaceData(this,data);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        mBinder.btSave.setOnClickListener(this);
        mBinder.llSearchLocation.setOnClickListener(this);
    }

    @Override
    public void done() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public NewAddressViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_shipping_new;
    }


    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }
}
