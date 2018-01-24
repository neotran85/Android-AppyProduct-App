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

import org.json.JSONObject;

import io.reactivex.Single;


public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doUserLogin(LoginRequest.ServerLoginRequest request);

    Single<SignUpResponse> doUserSignUp(SignUpRequest request);

    Single<LogoutResponse> doUserLogout();

    Single<BlogResponse> getBlogApiCall();

    Single<OpenSourceResponse> getOpenSourceApiCall();

    Single<AppointmentCreateResponse> createAppointment(AppointmentCreateRequest dataRequest);

    Single<AppointmentGetResponse> getAppointment(String idNumber);

    Single<JSONObject> getOrder(OrderGetRequest request);

    Single<ReceiptGetResponse> getReceipt(ReceiptGetRequest request);

    Single<JSONObject> getOrderAll();

    Single<JSONObject> getReceiptAll();

    Single<AppointmentDeleteResponse> deleteAppointment(AppointmentDeleteRequest request);

    Single<JSONObject> getAppointmentAll();
}
