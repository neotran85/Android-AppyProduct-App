package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step3;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep3Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step4.ServicesStep4Activity;
import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.MapManager;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class ServicesStep3Activity extends BaseActivity<ActivityServicesBookingStep3Binding, ServicesStep3ViewModel> implements ServicesStep3Navigator, View.OnClickListener {

    public final static int REQUEST_LOGIN_FOR_BOOKING = 1113;
    @Inject
    ServicesStep3ViewModel mServicesStep3ViewModel;
    ActivityServicesBookingStep3Binding mBinder;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep3Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mServicesStep3ViewModel);
        ViewUtils.setOnClickListener(this, mBinder.btnNext, mBinder.llSearchLocationNearby);
        setTitle(getString(R.string.set_location));
        activeBackButton();
        getViewModel().setNavigator(this);
        getViewModel().updateAddress();
    }


    public void saveAddress(boolean isSaved) {
        if (isSaved) {
            getViewModel().saveServiceAddress(new ServiceAddress(
                    mBinder.etUnitNumberHouse.getText().toString(),
                    mBinder.etStreet.getText().toString(),
                    mBinder.etAreaLine1.getText().toString(),
                    mBinder.etAreaLine2.getText().toString(),
                    mBinder.etCityTown.getText().toString(),
                    mBinder.etPostCode.getText().toString()));
        } else {
            getViewModel().saveServiceAddress(new ServiceAddress());
        }
    }

    private void setAddress() {
        String postcode = mBinder.etPostCode.getText().toString();
        postcode = postcode.length() > 0 ? "(Postal code " + postcode + ")" : "";

        String address = mBinder.etUnitNumberHouse.getText().toString() + ", "
                + mBinder.etStreet.getText().toString() + ", "
                + mBinder.etAreaLine1.getText().toString() + ", "
                + mBinder.etAreaLine2.getText().toString() + ", "
                + mBinder.etCityTown.getText().toString() + " "
                + postcode;
        getOrderUserInput().setAddress(address);
    }

    private ServiceOrderUserInput getOrderUserInput() {
        return getViewModel().getDataManager().getServiceOrderUserInput();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                clickNextButton();
                break;
            case R.id.llSearchLocationNearby:
                MapManager.openMapForPlaceSelection(this);
                break;
        }
    }

    private boolean checkIfLocationInputted() {
        return mBinder.etUnitNumberHouse.getText().length() > 0 ||
                mBinder.etStreet.getText().length() > 0 ||
                mBinder.etAreaLine1.getText().length() > 0 ||
                mBinder.etAreaLine2.getText().length() > 0 ||
                mBinder.etCityTown.getText().length() > 0 ||
                mBinder.etPostCode.getText().length() > 0;
    }

    private void clickNextButton() {
        if (checkIfLocationInputted()) {
            setAddress();
            saveAddress(mBinder.cbSaveAddress.isChecked());
            goToStep4();
        } else {
            AlertManager.getInstance(this).showLongToast(getString(R.string.step3_warning_location));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MapManager.PLACE_PICKER_REQUEST:
                    processDataAfterLocationSelected(data);
                    break;
                case REQUEST_LOGIN_FOR_BOOKING:
                    goToStep4();
                    break;
            }
        }
    }

    private void processDataAfterLocationSelected(Intent data) {
        if (data != null) {
            Place place = PlacePicker.getPlace(this, data);
            if (place != null) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String city = addresses.get(0).getLocality();
                    mBinder.etCityTown.setText(state + ", " + country);
                    mBinder.etPostCode.setText(addresses.get(0).getPostalCode());
                    mBinder.etUnitNumberHouse.setText(place.getName());
                    mBinder.etStreet.setText(place.getAddress());
                    mBinder.etAreaLine1.setText(city);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ServicesStep3ViewModel getViewModel() {
        return mServicesStep3ViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_booking_step3;
    }

    @Override
    public void openLoginActivity(String message, int requestCode) {
        Intent intent = LoginActivity.getStartIntent(this);
        intent.putExtra("message", message);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void goToStep4() {
        if (getViewModel().isUserLoggedIn()) {
            startActivity(ServicesStep4Activity.getStartIntent(this));
        } else {
            openLoginActivity(getString(R.string.login_required_message)
                            + " " + getString(R.string.login_to_book_service),
                    REQUEST_LOGIN_FOR_BOOKING);
        }
    }
}
