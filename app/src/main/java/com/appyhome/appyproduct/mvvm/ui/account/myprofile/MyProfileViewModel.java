package com.appyhome.appyproduct.mvvm.ui.account.myprofile;

import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;

public class MyProfileViewModel extends BaseViewModel<MyProfileNavigator> {

    public ObservableField<String> lastName = new ObservableField<>("");
    public ObservableField<String> firstName = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");

    public MyProfileViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onFieldClick(View view) {
        if (getNavigator() != null)
            getNavigator().onFieldClick(view);
    }

    public void fetchUserProfile() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getUserProfile()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(userGetResponse -> {
                    setIsLoading(false);
                    AppLogger.d(userGetResponse.toString());
                    if (userGetResponse != null) {
                        if (userGetResponse.getString(ApiCode.KEY_CODE).equals(ApiCode.OK_200)) {
                            try {
                                if (userGetResponse.has(ApiMessage.KEY_CODE)) {
                                    JSONObject message = userGetResponse.getJSONObject(ApiMessage.KEY_CODE);
                                    if (message != null) {
                                        String lastNameStr = message.getString("last_name");
                                        lastName.set(lastNameStr);
                                        getDataManager().setUserLastName(lastNameStr);

                                        String firstNameStr = message.getString("first_name");
                                        firstName.set(firstNameStr);
                                        getDataManager().setUserFirstName(firstNameStr);

                                        String emailStr = message.getString("email");
                                        email.set(emailStr);
                                        getDataManager().setCurrentUserEmail(emailStr);

                                        String phoneNumberStr = message.getString("phone_number");
                                        phoneNumber.set(phoneNumberStr);
                                        getDataManager().setCurrentPhoneNumber(phoneNumberStr);
                                        return;
                                    }
                                }
                            } catch (Exception e) {
                                Crashlytics.logException(e);
                            }
                        }
                    }
                    if (getNavigator() != null)
                        getNavigator().handleErrorService(null);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        if (getNavigator() != null)
                            getNavigator().handleErrorService(null);
                    }
                }));
    }

    public void logout() {
        getDataManager().logout();
        if (getNavigator() != null)
            getNavigator().backToHomeScreen();
    }
}
