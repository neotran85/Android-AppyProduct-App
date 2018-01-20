package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.data.model.api.AppointmentCreateResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.OpenSourceResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.SignUpResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<LoginResponse> doUserLogin(LoginRequest.ServerLoginRequest
                                                     request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.USER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<SignUpResponse> doUserSignUp(SignUpRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.USER_SIGN_UP)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(SignUpResponse.class);
    }


    @Override
    public Single<LogoutResponse> doUserLogout() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.USER_LOGOUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(LogoutResponse.class);
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return Rx2AndroidNetworking.get(ApiUrlConfig.ENDPOINT_BLOG)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(BlogResponse.class);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return Rx2AndroidNetworking.get(ApiUrlConfig.ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(OpenSourceResponse.class);
    }

    @Override
    public Single<AppointmentCreateResponse> createAppointment(JSONObject dataRequest) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.APPOINTMENT_CREATE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addJSONObjectBody(dataRequest)
                .build()
                .getObjectSingle(AppointmentCreateResponse.class);
    }
}
