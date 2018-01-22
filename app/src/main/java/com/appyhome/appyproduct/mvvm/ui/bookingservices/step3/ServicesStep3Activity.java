package com.appyhome.appyproduct.mvvm.ui.bookingservices.step3;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep3Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step4.ServicesStep4Activity;
import com.appyhome.appyproduct.mvvm.ui.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.manager.MapManager;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class ServicesStep3Activity extends BaseActivity<ActivityServicesBookingStep3Binding, ServicesStep3ViewModel> implements ServicesStep3Navigator, View.OnClickListener {

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
        mServicesStep3ViewModel.setNavigator(this);
        mBinder.btnNext.setOnClickListener(this);
        mBinder.llSearchLocationNearby.setOnClickListener(this);
        setTitle("Set Location");
        activeBackButton();
        updateAddress();
    }

    private void updateAddress() {
        ServiceAddress address = mServicesStep3ViewModel.getServiceAddress();
        if (address.number != null || address.number.length() > 0) {
            mBinder.etUnitNumberHouse.setText(address.number);
            mBinder.etStreet.setText(address.street);
            mBinder.etAreaLine1.setText(address.area1);
            mBinder.etAreaLine2.setText(address.area2);
            mBinder.etCityTown.setText(address.city);
            mBinder.etPostCode.setText(address.code);
        }
    }

    public void saveAddress(boolean isSaved) {
        if (isSaved) {
            mServicesStep3ViewModel.setServiceAddress(new ServiceAddress(
                    mBinder.etUnitNumberHouse.getText().toString(),
                    mBinder.etStreet.getText().toString(),
                    mBinder.etAreaLine1.getText().toString(),
                    mBinder.etAreaLine2.getText().toString(),
                    mBinder.etCityTown.getText().toString(),
                    mBinder.etPostCode.getText().toString()));
        } else {
            mServicesStep3ViewModel.setServiceAddress(new ServiceAddress());
        }
    }

    private void setAddress() {
        String postcode =  mBinder.etPostCode.getText().toString();
        postcode = postcode.length() > 0 ? "(Postal code " + postcode + ")" : "";

        String address = mBinder.etUnitNumberHouse.getText().toString() + ", "
                + mBinder.etStreet.getText().toString() + ", "
                + mBinder.etAreaLine1.getText().toString() + ", "
                + mBinder.etAreaLine2.getText().toString() + ", "
                + mBinder.etCityTown.getText().toString() + " "
                + postcode;
        ServiceOrderInfo.getInstance().setAddress(address);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                setAddress();
                setAppointmentId();
                saveAddress(mBinder.cbSaveAddress.isChecked());
                AppLogger.d(ServiceOrderInfo.getInstance().getAppointmentCreateRequest().toString());
                mServicesStep3ViewModel.createAppointment(ServiceOrderInfo.getInstance().getAppointmentCreateRequest());
                break;
            case R.id.llSearchLocationNearby:
                MapManager.openMapForPlaceSelection(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MapManager.PLACE_PICKER_REQUEST:
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
        }
    }


    private void setAppointmentId() {
        ServiceOrderInfo.getInstance().setAppointmentId(System.currentTimeMillis() + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
    }

    @Override
    public void doWhenAppointmentCreated() {
        goToStep4();
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
    public void goToStep4() {
        startActivity(ServicesStep4Activity.getStartIntent(this));
    }

    @Override
    public void openLoginActivity() {
        AlertManager.getInstance(this).showLongToast("Unauthorized. Please log in again.");
        Intent intent = LoginActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
