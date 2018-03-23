package com.appyhome.appyproduct.mvvm.data.local.prefs;

import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;


public interface PreferencesHelper {

    String getCurrentUsername();

    void setCurrentUsername(String userName);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    String getCurrentPhoneNumber();

    void setCurrentPhoneNumber(String phoneNumber);

    String getCurrentUserProfilePicUrl();

    void setCurrentUserProfilePicUrl(String profilePicUrl);

    String getAccessToken();

    void setAccessToken(String token);

    ServiceAddress loadServiceAddress();

    void saveServiceAddress(ServiceAddress serviceAddress);

    void logout();

    boolean isUserLoggedIn();

    String getUserLastName();

    void setUserLastName(String lastName);

    String getUserFirstName();

    void setUserFirstName(String firstName);

    void setDefaultPaymentMethod(String userId, String methodName);

    String getDefaultPaymentMethod(String userId);

    String getProductsSortCurrent(String userId);

    void setProductsSortCurrent(String userId, String sort);
}
