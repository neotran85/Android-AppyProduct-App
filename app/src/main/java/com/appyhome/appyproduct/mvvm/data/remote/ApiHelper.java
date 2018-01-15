package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.OpenSourceResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.SignUpResponse;

import io.reactivex.Single;


public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doUserLogin(LoginRequest.ServerLoginRequest request);
    Single<SignUpResponse> doUserSignUp(SignUpRequest request);

    Single<LogoutResponse> doUserLogout();

    Single<BlogResponse> getBlogApiCall();

    Single<OpenSourceResponse> getOpenSourceApiCall();
}
