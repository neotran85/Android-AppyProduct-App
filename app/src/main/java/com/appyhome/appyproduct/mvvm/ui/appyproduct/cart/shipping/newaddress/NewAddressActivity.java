package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductShippingNewBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.MapManager;

import javax.inject.Inject;

public class NewAddressActivity extends BaseActivity<ActivityProductShippingNewBinding, NewAddressViewModel> implements NewAddressNavigator {

    @Inject
    public NewAddressViewModel mViewModel;

    ActivityProductShippingNewBinding mBinder;

    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, NewAddressActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void openMapForPlaceSelection() {
        MapManager.openMapForPlaceSelection(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MapManager.PLACE_PICKER_REQUEST:
                    getViewModel().updateAddressFromGooglePlaceData(this, data);
                    break;
            }
        }
    }

    @Override
    public void openStatesSelection() {
        String currentState = getViewModel().state.get();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int checkedItem = -1;
        for (int i = 0; i < States.malaysiaStates.length; i++) {
            if (currentState.equals(States.malaysiaStates[i]))
                checkedItem = i;
        }
        builder.setTitle("Select a state");
        builder.setSingleChoiceItems(States.malaysiaStates, checkedItem, (dialog, item) -> {
            getViewModel().state.set(States.malaysiaStates[item]);
            if (dialog != null) dialog.dismiss();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void saveAddress() {
        if (!getViewModel().checkIfContactInputted()) {
            showAlert(getString(R.string.please_input_contact));
            return;
        }
        if (!getViewModel().isPostCodeValid()) {
            showAlert(getString(R.string.please_input_valid_post_code));
            return;
        }
        if (!getViewModel().checkIfLocationInputted()) {
            showAlert(getString(R.string.please_input_shipping_address));
            return;
        }
        getViewModel().saveShippingAddress(getAddressId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mViewModel);
        mViewModel.setNavigator(this);
        if (getAddressId() >= 0) {
            getViewModel().titleScreen.set(getString(R.string.title_edit_shipping_address));
            getViewModel().titleButton.set(getString(R.string.save));
            getViewModel().getAddressById(getAddressId());
        } else {
            getViewModel().titleButton.set(getString(R.string.create));
            getViewModel().titleScreen.set(getString(R.string.title_add_new_shipping_address));
        }
    }

    private int getAddressId() {
        return getIntent().getIntExtra("address_id", -1);
    }

    @Override
    public void onAddressSaved() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public NewAddressViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showAlert(String message) {
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(message);
    }
}
