package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.data.model.api.BannersResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LogoutResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddShippingAddressRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddShippingAddressResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddToCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddWishListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ApiResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.CheckoutOrderRequest;
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
import com.appyhome.appyproduct.mvvm.data.model.api.product.VerifyOrderRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.VerifyOrderResponse;
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

import org.json.JSONObject;

import io.reactivex.Single;


public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doUserLogin(LoginRequest request);

    Single<SignUpResponse> doUserSignUp(SignUpRequest request);

    Single<LogoutResponse> doUserLogout();

    Single<AppointmentCreateResponse> createAppointment(AppointmentCreateRequest dataRequest);

    Single<JSONObject> fetchAppointment(AppointmentGetRequest request);

    Single<JSONObject> fetchOrder(OrderGetRequest request);

    Single<JSONObject> fetchReceipt(ReceiptGetRequest request);

    Single<JSONObject> fetchOrderAll();

    Single<OrderCompletedResponse> markOrderCompleted(OrderCompletedRequest request);

    Single<JSONObject> fetchReceiptAll();

    Single<AppointmentDeleteResponse> deleteAppointment(AppointmentDeleteRequest request);

    Single<JSONObject> fetchAppointmentAll();

    Single<JSONObject> editOrder(OrderEditRequest request);

    Single<JSONObject> fetchUserProfile();

    Single<JSONObject> verifyUser();

    Single<JSONObject> verifyTrue(String code);

    Single<JSONObject> fetchProducts(ProductListRequest request);

    Single<BannersResponse> fetchBanners();

    Single<ProductVariantResponse> fetchProductVariant(int productId);

    Single<ApiResponse> addProductToCart(AddToCartRequest request);

    Single<ApiResponse> emptyUserCarts();

    Single<ApiResponse> fetchCartsServer();

    Single<ApiResponse> deleteProductToCart(DeleteCartRequest request);

    Single<ApiResponse> editProductToCart(EditCartRequest request);

    Single<ApiResponse> editProductCartVariant(EditCartVariantRequest request);

    Single<ApiResponse> addUserWishList(AddWishListRequest request);

    Single<ApiResponse> deleteUserWishList(DeleteWishListRequest request);

    Single<ApiResponse> emptyUserWishList();

    Single<ApiResponse> fetchUserWishList();

    Single<GetShippingResponse> fetchShippingFee(GetShippingRequest request);

    Single<GetSellerResponse> fetchSellerInformation(long sellerId);

    Single<GetShippingAddressResponse> fetchUserShippingAddress();

    Single<AddShippingAddressResponse> addUserShippingAddress(AddShippingAddressRequest request);

    Single<ApiResponse> setUserDefaultShippingAddress(int idAddress);

    Single<ApiResponse> removeUserShippingAddress(int idAddress);

    Single<AddShippingAddressResponse> editUserShippingAddress(AddShippingAddressRequest request);

    Single<ApiResponse> checkoutProductOrder(CheckoutOrderRequest request);

    Single<VerifyOrderResponse> verifyProductOrder(VerifyOrderRequest request);
}
