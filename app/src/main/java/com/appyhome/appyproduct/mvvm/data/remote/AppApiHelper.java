package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.data.model.api.BannersResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.OpenSourceResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddToCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddWishListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ApiResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.DeleteCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.DeleteWishListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.EditCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.EditCartVariantRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.GetSellerResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.GetShippingAddressResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.GetShippingRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.GetShippingResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductVariantResponse;
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
    public Single<LoginResponse> doUserLogin(LoginRequest request) {
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
    public Single<AppointmentCreateResponse> createAppointment(AppointmentCreateRequest dataRequest) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_APPOINTMENT_CREATE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addUrlEncodeFormBodyParameter(dataRequest)
                .build()
                .getObjectSingle(AppointmentCreateResponse.class);
    }

    @Override
    public Single<JSONObject> fetchAppointment(AppointmentGetRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_APPOINTMENT_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> fetchAppointmentAll() {
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
    public Single<JSONObject> fetchOrder(OrderGetRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_ORDER_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> fetchOrderAll() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_ORDER_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> fetchReceiptAll() {
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
    public Single<JSONObject> fetchUserProfile() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_USER_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getJSONObjectSingle();
    }

    @Override
    public Single<JSONObject> fetchReceipt(ReceiptGetRequest request) {
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
    public Single<JSONObject> fetchProducts(ProductListRequest request) {
        JSONObject jsonObject = new JSONObject();
        String url = (request.terms == null || request.terms.length() <= 0) ?
                ApiUrlConfig.API_PRODUCT_SUB_CATEGORY_GET : ApiUrlConfig.API_PRODUCT_SEARCH;
        try {
            jsonObject.put("page", request.page);
            jsonObject.put("category_id", request.categoryId);
            jsonObject.put("type", request.type);
            jsonObject.put("sortBy", request.type);
            jsonObject.put("terms", request.terms);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Rx2AndroidNetworking.post(url)
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

    @Override
    public Single<ProductVariantResponse> fetchProductVariant(int productId) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_VARIANT_GET)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("product_id", productId + "")
                .build()
                .getObjectSingle(ProductVariantResponse.class);
    }

    /********** PRODUCT CART API INTEGRATION **********/

    @Override
    public Single<ApiResponse> addProductToCart(AddToCartRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_CART_ADD)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<ApiResponse> deleteProductToCart(DeleteCartRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_CART_DELETE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<ApiResponse> editProductCartVariant(EditCartVariantRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_CART_VARIANT_EDIT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<ApiResponse> editProductToCart(EditCartRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_CART_EDIT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<ApiResponse> fetchCartsServer() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_CART_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<ApiResponse> emptyUserCarts() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_CART_EMPTY)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    /********** SHIPPING ADDRESS API ************/
    @Override
    public Single<GetShippingAddressResponse> fetchUserShippingAddress() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_SHIPPING_ADDRESS_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(GetShippingAddressResponse.class);
    }

    /********** PRODUCT WISH LIST API INTEGRATION **********/

    @Override
    public Single<ApiResponse> emptyUserWishList() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_WISH_LIST_EMPTY)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<ApiResponse> fetchUserWishList() {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_WISH_LIST_GET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<ApiResponse> deleteUserWishList(DeleteWishListRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_WISH_LIST_DELETE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<ApiResponse> addUserWishList(AddWishListRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_WISH_LIST_ADD)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(ApiResponse.class);
    }

    @Override
    public Single<GetShippingResponse> fetchShippingFee(GetShippingRequest request) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_SHIPPING_GET)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(GetShippingResponse.class);
    }

    @Override
    public Single<GetSellerResponse> fetchSellerInformation(int sellerId) {
        return Rx2AndroidNetworking.post(ApiUrlConfig.API_PRODUCT_SELLER_GET)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("seller_id", sellerId + "")
                .build()
                .getObjectSingle(GetSellerResponse.class);
    }

}