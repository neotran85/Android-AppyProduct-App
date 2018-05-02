package com.appyhome.appyproduct.mvvm.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.di.PreferenceInfo;

import javax.inject.Inject;


public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREF_KEY_LOCAL_DATABASE_UPDATED = "LOCAL_DATABASE_UPDATED_1";
    private static final String PREF_KEY_CURRENT_USER_FIRST_NAME = "PREF_KEY_CURRENT_USER_FIRST_NAME";
    private static final String PREF_KEY_CURRENT_USER_LAST_NAME = "PREF_KEY_CURRENT_USER_LAST_NAME";
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_PHONE_NUMBER = "PREF_KEY_CURRENT_PHONE_NUMBER";
    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_PAYMENT_METHOD = "PREF_KEY_PAYMENT_METHOD";
    private static final String PREF_PRODUCT_SORT_CURRENT = "PREF_PRODUCT_SORT_CURRENT";
    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL
            = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_SERVICE_ADDRESS = "PREF_KEY_SERVICE_ADDRESS";
    private static final String PREF_KEY_CACHED_RESPONSE = "PREF_KEY_CACHED_RESPONSE";
    private static final String PREF_KEY_SHIPPING_LOCATION = "PREF_KEY_SHIPPING_LOCATION";
    private static final String PREF_KEY_SHIPPING_POST_CODE = "PREF_KEY_SHIPPING_POST_CODE";

    private final SharedPreferences mPrefs;
    private final SharedPreferences mCachedResponse;

    @Inject
    public AppPreferencesHelper(Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        mCachedResponse = context.getSharedPreferences(prefFileName + "_response_cached", Context.MODE_PRIVATE);
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
    public ServiceAddress loadServiceAddress() {
        ServiceAddress address = new ServiceAddress();
        String json = mPrefs.getString(PREF_KEY_SERVICE_ADDRESS, "");
        address.fromJSONString(json);
        return address;
    }

    @Override
    public void saveServiceAddress(ServiceAddress serviceAddress) {
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

    @Override
    public boolean isLocalDatabaseUpdated() {
        boolean value = mPrefs.getBoolean(PREF_KEY_LOCAL_DATABASE_UPDATED, false);
        return value;
    }

    @Override
    public void setLocalDatabaseUpdated(boolean isDone) {
        mPrefs.edit().putBoolean(PREF_KEY_LOCAL_DATABASE_UPDATED, isDone).apply();
    }

    @Override
    public String getUserLastName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_LAST_NAME, "");
    }

    @Override
    public void setUserLastName(String lastName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_LAST_NAME, lastName).apply();
    }

    @Override
    public String getUserFirstName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_FIRST_NAME, "");
    }

    @Override
    public void setUserFirstName(String firstName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_FIRST_NAME, firstName).apply();
    }

    @Override
    public void setDefaultPaymentMethod(String userId, String methodName) {
        mPrefs.edit().putString(PREF_KEY_PAYMENT_METHOD + ":" + userId, methodName).apply();
    }

    @Override
    public void setDefaultShippingLocation(String location) {
        mPrefs.edit().putString(PREF_KEY_SHIPPING_LOCATION, location).apply();
    }

    @Override
    public void setDefaultShippingPostCode(String postCode) {
        mPrefs.edit().putString(PREF_KEY_SHIPPING_POST_CODE, postCode).apply();
    }

    @Override
    public void setProductsSortCurrent(String userId, String sort) {
        mPrefs.edit().putString(PREF_PRODUCT_SORT_CURRENT + ":" + userId, sort).apply();
    }

    @Override
    public void setCachedResponse(String command, String key, String response) {
        mCachedResponse.edit().putString(PREF_KEY_CACHED_RESPONSE + ":" + command + ":" + key, response).apply();
    }

    @Override
    public String getCachedResponse(String command, String key) {
        return mCachedResponse.getString(PREF_KEY_CACHED_RESPONSE + ":" + command + ":" + key, "");
    }

    @Override
    public String getDefaultShippingPostCode() {
        return mPrefs.getString(PREF_KEY_SHIPPING_POST_CODE, "");
    }

    @Override
    public String getDefaultShippingLocation() {
        return mPrefs.getString(PREF_KEY_SHIPPING_LOCATION, "");
    }

    @Override
    public String getProductsSortCurrent(String userId) {
        return mPrefs.getString(PREF_PRODUCT_SORT_CURRENT + ":" + userId, "");
    }

    @Override
    public String getDefaultPaymentMethod(String userId) {
        return mPrefs.getString(PREF_KEY_PAYMENT_METHOD + ":" + userId, "");
    }

    @Override
    public void clearCachedResponse() {
        mCachedResponse.edit().clear().apply();
    }
}
