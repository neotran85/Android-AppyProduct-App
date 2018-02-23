package com.appyhome.appyproduct.mvvm.ui.account.register.verify;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;

public class VerifyViewModel extends BaseViewModel<VerifyNavigator> {

    public VerifyViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void verifyTrue() {
        getCompositeDisposable().add(getDataManager()
                .verifyTrue()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject response) throws Exception {
                        handleVerifyResponse(response);
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                    }
                }));
    }

    public void doVerifyUser() {
        getCompositeDisposable().add(getDataManager()
                .verifyUser()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject response) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCodeSentMessage();
                        // Show message to user.
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                    }
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
            if (statusCode.equals(ApiCode.OK_200)) {
                if (DataUtils.isEqualAndNotNull(message, ApiMessage.SUCCESS)) {
                    getNavigator().showSuccessLogin();
                    getNavigator().doAfterRegisterSucceeded();
                    return;
                }
            }
        } catch (Exception e) {

        }
        getNavigator().showErrorOthers();
    }
}
