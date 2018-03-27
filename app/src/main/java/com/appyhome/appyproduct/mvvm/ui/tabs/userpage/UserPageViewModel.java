package com.appyhome.appyproduct.mvvm.ui.tabs.userpage;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;

public class

UserPageViewModel extends BaseViewModel<UserPageNavigator> {

    public ObservableField<String> fullName = new ObservableField<>("");

    public UserPageViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public void logout() {
        getDataManager().logout();
        if (getNavigator() != null)
            getNavigator().backToHomeScreen();
    }


    public void fetchUserProfile() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getUserProfile()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(userGetResponse -> {
                    if (userGetResponse != null) {
                        if (userGetResponse.getString(ApiCode.KEY_CODE).equals(ApiCode.OK_200)) {
                            try {
                                if (userGetResponse.has(ApiMessage.KEY_CODE)) {
                                    JSONObject message = userGetResponse.getJSONObject(ApiMessage.KEY_CODE);
                                    if (message != null) {
                                        String lastNameStr = message.getString("last_name");
                                        getDataManager().setUserLastName(lastNameStr);

                                        String firstNameStr = message.getString("first_name");
                                        getDataManager().setUserFirstName(firstNameStr);

                                        String emailStr = message.getString("email");
                                        getDataManager().setCurrentUserEmail(emailStr);

                                        String phoneNumberStr = message.getString("phone_number");
                                        getDataManager().setCurrentPhoneNumber(phoneNumberStr);

                                        String fullNameStr = firstNameStr+ " " + lastNameStr;
                                        fullName.set("Hi, " + fullNameStr);

                                        return;
                                    }
                                }
                            } catch (Exception e) {
                                Crashlytics.logException(e);
                            }
                        }
                    }
                }, throwable -> {}));
    }
}
