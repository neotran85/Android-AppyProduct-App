package com.appyhome.appyproduct.mvvm.ui.account.register.verify;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import org.json.JSONObject;

public class VerifyViewModel extends BaseViewModel<VerifyNavigator> {

    private boolean isVerified = false;

    public VerifyViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void verifyTrue(String code) {
        getNavigator().showLoading();
        getCompositeDisposable().add(getDataManager()
                .verifyTrue(code)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    handleVerifyResponse(response);
                    getNavigator().closeLoading();
                }, throwable -> {
                    getNavigator().closeLoading();
                    getNavigator().handleErrorService(throwable);
                }));
    }

    public void doVerifyUser() {
        getNavigator().showLoading();
        getCompositeDisposable().add(getDataManager()
                .verifyUser()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().closeLoading();
                    getNavigator().showCodeSentMessage();
                    // Show message to user.
                }, throwable -> {
                    getNavigator().closeLoading();
                    getNavigator().handleErrorService(throwable);
                }));
    }

    private void handleVerifyResponse(JSONObject response) {
        if (response == null) {
            getNavigator().showErrorServer();
            return;
        }
        try {
            String statusCode = response.getString(ApiCode.KEY_CODE);
            String message = response.getString(ApiMessage.KEY_CODE);
            boolean success = statusCode.equals(ApiCode.OK_200) && DataUtils.isEqualAndNotNull(message, ApiMessage.PHONE_NUMBER_ACTIVATED);
            success = success || (statusCode.equals(ApiCode.BAD_REQUEST_400) && DataUtils.isEqualAndNotNull(message, ApiMessage.PHONE_NUMBER_ALREADY_CONFIRMED));
            if (success) {
                isVerified = true;
                getNavigator().doAfterRegisterSucceeded();
                return;
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        getNavigator().showErrorOthers();
    }

    public boolean isVerified() {
        return isVerified;
    }
}
