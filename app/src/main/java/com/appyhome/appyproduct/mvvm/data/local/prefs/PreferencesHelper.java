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

    ServiceAddress getServiceAddress();

    void setServiceAddress(ServiceAddress serviceAddress);

    void logout();

    boolean isUserLoggedIn();

    String getUserLastName();

    void setUserLastName(String lastName);

    String getUserFirstName();

    void setUserFirstName(String firstName);
}
