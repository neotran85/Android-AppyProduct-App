package com.appyhome.appyproduct.mvvm.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.di.PreferenceInfo;

import javax.inject.Inject;


public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_PHONE_NUMBER = "PREF_KEY_CURRENT_PHONE_NUMBER";
    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL
            = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_SERVICE_ADDRESS_NUMBER = "PREF_KEY_SERVICE_ADDRESS_NUMBER";
    private static final String PREF_KEY_SERVICE_ADDRESS_STREET = "PREF_KEY_SERVICE_ADDRESS_STREET";
    private static final String PREF_KEY_SERVICE_ADDRESS_AREA_LINE1 = "PREF_KEY_SERVICE_ADDRESS_AREA_LINE1";
    private static final String PREF_KEY_SERVICE_ADDRESS_AREA_LINE2 = "PREF_KEY_SERVICE_ADDRESS_AREA_LINE2";
    private static final String PREF_KEY_SERVICE_ADDRESS_CITY = "PREF_KEY_SERVICE_ADDRESS_CITY";
    private static final String PREF_KEY_SERVICE_ADDRESS_CODE = "PREF_KEY_SERVICE_ADDRESS_CODE";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getCurrentPhoneNumber() {
        return mPrefs.getString(PREF_KEY_CURRENT_PHONE_NUMBER, null);
    }

    @Override
    public void setCurrentPhoneNumber(String phoneNumber) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_PHONE_NUMBER, phoneNumber).apply();
    }

    @Override
    public String getCurrentUsername() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUsername(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }

    @Override
    public String getCurrentUserId() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_ID, null);
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String token) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, token).apply();
    }

    @Override
    public ServiceAddress getServiceAddress() {
        ServiceAddress address = new ServiceAddress();
        address.number = mPrefs.getString(PREF_KEY_SERVICE_ADDRESS_NUMBER, "");
        address.street = mPrefs.getString(PREF_KEY_SERVICE_ADDRESS_STREET, "");
        address.area1 = mPrefs.getString(PREF_KEY_SERVICE_ADDRESS_AREA_LINE1, "");
        address.area2 = mPrefs.getString(PREF_KEY_SERVICE_ADDRESS_AREA_LINE2, "");
        address.city = mPrefs.getString(PREF_KEY_SERVICE_ADDRESS_CITY, "");
        address.code = mPrefs.getString(PREF_KEY_SERVICE_ADDRESS_CODE, "");
        return address;
    }

    @Override
    public void setServiceAddress(ServiceAddress serviceAddress) {
        mPrefs.edit().putString(PREF_KEY_SERVICE_ADDRESS_NUMBER, serviceAddress.number).apply();
        mPrefs.edit().putString(PREF_KEY_SERVICE_ADDRESS_STREET, serviceAddress.street).apply();
        mPrefs.edit().putString(PREF_KEY_SERVICE_ADDRESS_AREA_LINE1, serviceAddress.area1).apply();
        mPrefs.edit().putString(PREF_KEY_SERVICE_ADDRESS_AREA_LINE2, serviceAddress.area2).apply();
        mPrefs.edit().putString(PREF_KEY_SERVICE_ADDRESS_CITY, serviceAddress.city).apply();
        mPrefs.edit().putString(PREF_KEY_SERVICE_ADDRESS_CODE, serviceAddress.code).apply();
    }
}
