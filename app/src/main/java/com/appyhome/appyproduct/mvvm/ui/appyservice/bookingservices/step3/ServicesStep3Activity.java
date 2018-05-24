package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep3Binding;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step4.ServicesStep4Activity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.MapManager;

import javax.inject.Inject;

public class ServicesStep3Activity extends BaseActivity<ActivityServicesBookingStep3Binding, ServicesStep3ViewModel> implements ServicesStep3Navigator, View.OnClickListener {

    @Inject
    ServicesStep3ViewModel mViewModel;
    ActivityServicesBookingStep3Binding mBinder;

    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep3Activity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        ViewUtils.setOnClickListener(this, mBinder.btnNext, mBinder.llSearchLocationNearby);
        setTitle(getString(R.string.set_location));
        activeBackButton();
        getViewModel().setNavigator(this);
        getViewModel().updateAddress();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                mViewModel.executeData(this, mBinder.cbSaveAddress.isChecked());
                break;
            case R.id.llSearchLocationNearby:
                MapManager.openMapForPlaceSelection(this);
                break;
        }
    }

    @Override
    public void showAlert(String message) {
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MapManager.PLACE_PICKER_REQUEST:
                    mViewModel.updateAddressFromGooglePlaceData(this, data);
                    break;
                case REQUEST_LOGIN:
                    goToStep4();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ServicesStep3ViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void goToStep4() {
        if (!askForLogin(getString(R.string.login_required_message)
                + " " + getString(R.string.login_to_book_service))) {
            startActivity(ServicesStep4Activity.getStartIntent(this));
        }
    }
}
