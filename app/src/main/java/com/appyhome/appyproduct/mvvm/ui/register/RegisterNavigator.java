package com.appyhome.appyproduct.mvvm.ui.register;

import com.appyhome.appyproduct.mvvm.data.model.api.SignUpResponse;

public interface RegisterNavigator {

    void openMainActivity();

    void handleError(Throwable throwable);
    void handleSignUpResponse(SignUpResponse response);

    void register();

}
