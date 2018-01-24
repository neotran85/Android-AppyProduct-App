package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.OpenSourceResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentDeleteRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentDeleteResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentGetResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.ReceiptGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.ReceiptGetResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;
    private static final String KEY_ID_NUMBER = "id_number";

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
    public Single<AppointmentCreateResponse> createAppointment(AppointmentCreateRequest dataRequest) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.APPOINTMENT_CREATE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addUrlEncodeFormBodyParameter(dataRequest)
                .build()
                .getObjectSingle(AppointmentCreateResponse.class);
    }

    @Override
    public Single<AppointmentGetResponse> getAppointment(String idNumber) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.APPOINTMENT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(KEY_ID_NUMBER, idNumber)
                .build()
                .getObjectSingle(AppointmentGetResponse.class);
    }

    @Override
    public Single<JSONObject> getAppointmentAll() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.APPOINTMENT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<AppointmentDeleteResponse> deleteAppointment(AppointmentDeleteRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.APPOINTMENT_DELETE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addUrlEncodeFormBodyParameter(request)
                .build()
                .getObjectSingle(AppointmentDeleteResponse.class);
    }

    @Override
    public Single<JSONObject> getOrder(OrderGetRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.ORDER_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> getOrderAll() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.ORDER_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> getReceiptAll() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.RECEIPT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<ReceiptGetResponse> getReceipt(ReceiptGetRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.RECEIPT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addUrlEncodeFormBodyParameter(request)
                .build()
                .getObjectSingle(ReceiptGetResponse.class);
    }
}