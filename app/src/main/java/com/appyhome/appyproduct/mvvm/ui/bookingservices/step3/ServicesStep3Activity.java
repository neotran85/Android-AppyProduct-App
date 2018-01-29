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
        mBinder.setViewModel(mServicesStep3ViewModel);
        mBinder.btnNext.setOnClickListener(this);
        mBinder.llSearchLocationNearby.setOnClickListener(this);
        setTitle("Set Location");
        activeBackButton();
        mServicesStep3ViewModel.updateAddress();
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
        String postcode = mBinder.etPostCode.getText().toString();
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
        if(checkIfLocationInputted()) {
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
    public void goToStep4() {
        startActivity(ServicesStep4Activity.getStartIntent(this));
    }

}
