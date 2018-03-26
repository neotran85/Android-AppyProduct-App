package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.data.model.api.BannersResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.OpenSourceResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentDeleteRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentDeleteResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderCompletedRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderCompletedResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderEditRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.ReceiptGetRequest;
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
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_USER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<SignUpResponse> doUserSignUp(SignUpRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_USER_SIGN_UP)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(SignUpResponse.class);
    }


    @Override
    public Single<LogoutResponse> doUserLogout() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_USER_LOGOUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(LogoutResponse.class);
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return Rx2AndroidNetworking.get(ApiUrlConfig.API_ENDPOINT_BLOG)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(BlogResponse.class);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return Rx2AndroidNetworking.get(ApiUrlConfig.API_ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(OpenSourceResponse.class);
    }

    @Override
    public Single<AppointmentCreateResponse> createAppointment(AppointmentCreateRequest dataRequest) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_APPOINTMENT_CREATE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addUrlEncodeFormBodyParameter(dataRequest)
                .build()
                .getObjectSingle(AppointmentCreateResponse.class);
    }

    @Override
    public Single<JSONObject> getAppointment(AppointmentGetRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_APPOINTMENT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> getAppointmentAll() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_APPOINTMENT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<AppointmentDeleteResponse> deleteAppointment(AppointmentDeleteRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_APPOINTMENT_DELETE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addUrlEncodeFormBodyParameter(request)
                .build()
                .getObjectSingle(AppointmentDeleteResponse.class);
    }

    @Override
    public Single<JSONObject> getOrder(OrderGetRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_ORDER_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> getOrderAll() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_ORDER_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> getReceiptAll() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_RECEIPT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<OrderCompletedResponse> markOrderCompleted(OrderCompletedRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_ORDER_COMPLETED)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addUrlEncodeFormBodyParameter(request)
                .build()
                .getObjectSingle(OrderCompletedResponse.class);
    }

    @Override
    public Single<JSONObject> getUserProfile() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_USER_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> getReceipt(ReceiptGetRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_RECEIPT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> editOrder(OrderEditRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_ORDER_EDIT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> verifyUser() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_VERIFY_USER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> verifyTrue(String code) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_VERIFY_TRUE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("code", code)
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> fetchProductsByIdCategory(ProductListRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", request.page);
            jsonObject.put("category_id", request.categoryId);
            jsonObject.put("type", request.type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_PER_CATEGORY_GET)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addJSONObjectBody(jsonObject)
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<BannersResponse> fetchBanners() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_BANNERS_GET)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .build()
                .getObjectSingle(BannersResponse.class);
    }
}