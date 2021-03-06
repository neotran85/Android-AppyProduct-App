package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.location.Address;
import android.location.Geocoder;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddShippingAddressRequest;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.ValidationUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;
import java.util.Locale;

public class NewAddressViewModel extends BaseViewModel<NewAddressNavigator> {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> street = new ObservableField<>("");
    public ObservableField<String> unit = new ObservableField<>("");
    public ObservableField<String> city = new ObservableField<>("");
    public ObservableField<String> state = new ObservableField<>("");
    public ObservableField<String> postCode = new ObservableField<>("");
    public ObservableField<String> companyName = new ObservableField<>("");
    public ObservableField<Boolean> checked = new ObservableField<>(true);
    public ObservableField<String> titleScreen = new ObservableField<String>("");
    public ObservableField<String> titleButton = new ObservableField<String>("");

    private double longitude = 0;

    private double latitude = 0;

    private String placeId = "";

    public NewAddressViewModel(DataManager dataManager,
                               SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void saveShippingAddress(int idAddress) {
        AppyAddress address = new AppyAddress();
        address.address_name = name.get();
        address.recipient_name = name.get();
        address.recipient_phone_number = getPhoneNumber();
        address.post_code = postCode.get();
        address.city = city.get();
        address.state = state.get();
        address.indoor_address = unit.get();
        address.outdoor_address = street.get();
        address.country = "Malaysia";
        address.user_id = getUserId();
        address.is_default = checked.get() ? 1 : 0;
        address.place_id = placeId;
        address.longitude = longitude;
        address.company_name = companyName.get();
        address.latitude = latitude;
        if (idAddress >= 0) { // EDIT TO SERVER DB
            getCompositeDisposable().add(getDataManager().editUserShippingAddress(new AddShippingAddressRequest(address, idAddress))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(data -> {
                        // SUCCESSFULLY EDITED, THEN FETCH ALL ADDRESSES
                        if (!updateDefaultToServer(idAddress))
                            fetchAllShippingAddresses();
                    }, Crashlytics::logException));

        } else {        // ADD TO SERVER DB
            getCompositeDisposable().add(getDataManager().addUserShippingAddress(new AddShippingAddressRequest(address))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(data -> {
                        // SUCCESSFULLY ADDED TO SERVER, THEN FETCH ALL ADDRESSES
                        fetchAllShippingAddresses();
                    }, Crashlytics::logException));
        }
    }

    private void setValueNotNull(ObservableField<String> target, String value) {
        target.set(value != null ? value : "");
    }

    private String getPhoneNumber() {
        return "60" + phoneNumber.get();
    }

    public boolean isPhoneNumberValid() {
        return ValidationUtils.isPhoneNumberValid(getPhoneNumber());
    }

    public boolean isPostCodeValid() {
        return postCode.get().length() > 0;
    }

    public boolean checkIfContactInputted() {
        return name.get().length() > 0
                && ValidationUtils.isPhoneNumberValid(getPhoneNumber());
    }

    public boolean checkIfLocationInputted() {
        return unit.get().length() > 0 ||
                companyName.get().length() > 0 ||
                street.get().length() > 0 ||
                city.get().length() > 0 ||
                state.get().length() > 0 ||
                city.get().length() > 0 ||
                postCode.get().length() > 0;
    }

    public void updateAddressFromGooglePlaceData(Context context, Intent data) {
        if (data != null) {
            Place place = PlacePicker.getPlace(context, data);
            if (place != null) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    placeId = place.getId();
                    longitude = place.getLatLng().longitude;
                    latitude = place.getLatLng().latitude;
                    addresses = geocoder.getFromLocation(latitude, longitude, 10);
                    if (addresses != null && addresses.size() > 0) {
                        String stateStr = addresses.get(0).getAdminArea();
                        String countryStr = addresses.get(0).getCountryName();
                        String cityStr = getLocalCity(addresses);
                        setValueNotNull(city, cityStr);
                        setValueNotNull(postCode, getPostCode(addresses));
                        setValueNotNull(unit, place.getName().toString());
                        setValueNotNull(street, place.getAddress().toString());
                        setValueNotNull(state, stateStr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
            }
        }
    }

    private String getPostCode(List<Address> addresses) {
        for (Address address : addresses) {
            if (address.getPostalCode() != null) {
                return address.getPostalCode();
            }
        }
        return "";
    }

    private String getLocalCity(List<Address> addresses) {
        for (Address address : addresses) {
            if (address.getLocality() != null) {
                return address.getLocality();
            }
        }
        return "";
    }

    public void getAddressById(int addressId) {
        getCompositeDisposable().add(getDataManager().getShippingAddress(getUserId(), addressId)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(address -> {
                    if (address != null) {
                        name.set(address.recipient_name);
                        companyName.set(DataUtils.getStringNotNull(address.company_name));
                        city.set(DataUtils.getStringNotNull(address.city));
                        unit.set(DataUtils.getStringNotNull(address.indoor_address));
                        state.set(DataUtils.getStringNotNull(address.state));
                        postCode.set(DataUtils.getStringNotNull(address.post_code));
                        street.set(DataUtils.getStringNotNull(address.outdoor_address));
                        checked.set(address.is_default == 1);
                        if (address.recipient_phone_number.length() > 0) {
                            String head = address.recipient_phone_number.substring(0, 2);
                            if (head.equals("60")) { // MALAYSIA PHONE NUMBER
                                String rest = address.recipient_phone_number.substring(2, address.recipient_phone_number.length());
                                phoneNumber.set(DataUtils.getStringNotNull(rest));
                            } else
                                phoneNumber.set(DataUtils.getStringNotNull(address.recipient_phone_number));
                        }
                    }

                }, Crashlytics::logException));
    }

    private void fetchAllShippingAddresses() {
        getCompositeDisposable().add(getDataManager().fetchUserShippingAddress()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        getCompositeDisposable().add(getDataManager().syncAllShippingAddresses(getUserId(), data.message)
                                .take(1)
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(success -> {
                                    getNavigator().onAddressSaved();
                                }, Crashlytics::logException));
                    }
                }, Crashlytics::logException));
    }

    private boolean updateDefaultToServer(int idAddress) {
        if (checked.get()) {
            getCompositeDisposable().add(getDataManager().setUserDefaultShippingAddress(idAddress)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(data -> {
                        // SUCCESSFULLY UPDATED
                        fetchAllShippingAddresses();
                    }, Crashlytics::logException));
            return true;
        }
        return false;
    }
}
