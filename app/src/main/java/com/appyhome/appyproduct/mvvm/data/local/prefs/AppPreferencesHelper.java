package com.appyhome.appyproduct.mvvm.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.di.PreferenceInfo;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;

import javax.inject.Inject;


public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_PHONE_NUMBER = "PREF_KEY_CURRENT_PHONE_NUMBER";
    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL
            = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_SERVICE_ADDRESS = "PREF_KEY_SERVICE_ADDRESS";

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
        String json = mPrefs.getString(PREF_KEY_SERVICE_ADDRESS, "");
        address.fromJSONString(json);
        return address;
    }

    @Override
    public void setServiceAddress(ServiceAddress serviceAddress) {
        AppLogger.d("setServiceAddress: " + serviceAddress.toJSONString());
        AppLogger.d("setServiceAddress: " + serviceAddress.toString());
        mPrefs.edit().putString(PREF_KEY_SERVICE_ADDRESS, serviceAddress.toJSONString()).apply();
    }

    @Override
    public void logout() {
        setAccessToken("");
    }

    @Override
    public boolean isUserLoggedIn() {
        String accessToken = getAccessToken();
        return accessToken != null && accessToken.length() > 0;
    }
}
