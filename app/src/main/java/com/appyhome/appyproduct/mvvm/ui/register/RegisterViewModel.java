package com.appyhome.appyproduct.mvvm.ui.register;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.SignUpResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.ValidationUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {

    public RegisterViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerRegisterClick() {
        getNavigator().register();
    }

    public void register(String firstName, String lastName, String email, String phoneNumber, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doUserSignUp(new SignUpRequest(firstName, lastName, email, phoneNumber, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SignUpResponse>() {
                    @Override
                    public void accept(SignUpResponse response) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleSignUpResponse(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }
                }));
    }

    public Object checkData(String phoneNumber, String password) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }
        return true;
    }

}
