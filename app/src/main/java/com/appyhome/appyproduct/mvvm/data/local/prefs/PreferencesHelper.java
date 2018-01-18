package com.appyhome.appyproduct.mvvm.data.local.prefs;

import com.appyhome.appyproduct.mvvm.data.DataManager;


public interface PreferencesHelper {

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentUsername();

    void setCurrentUsername(String userName);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    void setCurrentPhoneNumber(String phoneNumber);

    String getCurrentPhoneNumber();

    String getCurrentUserProfilePicUrl();

    void setCurrentUserProfilePicUrl(String profilePicUrl);

    String getAccessToken();

    void setAccessToken(String token);
}
