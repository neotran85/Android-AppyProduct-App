package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step3;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.location.Address;
import android.location.Geocoder;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;
import java.util.Locale;

public class ServicesStep3ViewModel extends BaseViewModel<ServicesStep3Navigator> {
    public ObservableField<String> number = new ObservableField<>("");
    public ObservableField<String> street = new ObservableField<>("");
    public ObservableField<String> area1 = new ObservableField<>("");
    public ObservableField<String> area2 = new ObservableField<>("");
    public ObservableField<String> city = new ObservableField<>("");
    public ObservableField<String> code = new ObservableField<>("");

    public ServicesStep3ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ServiceAddress getServiceAddress() {
        return getDataManager().loadServiceAddress();
    }

    private void saveServiceAddress(ServiceAddress serviceAddress) {
        getDataManager().saveServiceAddress(serviceAddress);
    }

    public void saveAddress(boolean isSaved) {
        if (isSaved) {
            saveServiceAddress(new ServiceAddress(
                    number.get(), street.get(), area1.get(),
                    area2.get(), city.get(), code.get()));
            setAddressToRequest();
        } else {
            saveServiceAddress(new ServiceAddress());
        }
    }

    public void executeData(Context context, boolean isSavedChecked) {
        if (checkIfLocationInputted()) {
            saveAddress(isSavedChecked);
            getNavigator().goToStep4();
        } else {
            getNavigator().showAlert(context.getString(R.string.step3_warning_location));
        }
    }

    public void updateAddressFromGooglePlaceData(Context context, Intent data) {
        if (data != null) {
            Place place = PlacePicker.getPlace(context, data);
            if (place != null) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    String stateStr = addresses.get(0).getAdminArea();
                    String countryStr = addresses.get(0).getCountryName();
                    String cityStr = addresses.get(0).getLocality();
                    setValueNotNull(city, stateStr + ", " + countryStr);
                    setValueNotNull(code, addresses.get(0).getPostalCode());
                    setValueNotNull(number, place.getName().toString());
                    setValueNotNull(street, place.getAddress().toString());
                    setValueNotNull(area1, cityStr);
                } catch (Exception e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
            }
        }
    }

    private void setValueNotNull(ObservableField<String> target, String value) {
        target.set(value != null ? value : "");
    }

    private void setAddressToRequest() {
        String postcode = code.get();
        postcode = (postcode != null && postcode.length() > 0) ? "(Postal code " + postcode + ")" : "";
        String address = number.get() + ", "
                + street.get() + ", "
                + area1.get() + ", "
                + area2.get() + ", "
                + city.get() + " "
                + postcode;
        getDataManager().getServiceOrderUserInput().setAddress(address);
    }

    public boolean checkIfLocationInputted() {
        return number.get().length() > 0 ||
                street.get().length() > 0 ||
                area1.get().length() > 0 ||
                area2.get().length() > 0 ||
                city.get().length() > 0 ||
                code.get().length() > 0;
    }

    public void updateAddress() {
        ServiceAddress address = getServiceAddress();
        number.set(address.number);
        street.set(address.street);
        area1.set(address.area1);
        area2.set(address.area2);
        city.set(address.city);
        code.set(address.code);
    }
}
