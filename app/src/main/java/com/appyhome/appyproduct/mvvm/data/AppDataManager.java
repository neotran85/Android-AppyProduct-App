package com.appyhome.appyproduct.mvvm.data;

import android.content.Context;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.data.local.db.DbHelper;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Seller;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;
import com.appyhome.appyproduct.mvvm.data.local.prefs.PreferencesHelper;
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
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductCartResponse;
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
import com.appyhome.appyproduct.mvvm.data.model.db.AppyService;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyServiceCategory;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHeader;
import com.appyhome.appyproduct.mvvm.data.remote.ApiHelper;
import com.appyhome.appyproduct.mvvm.data.remote.ApiUrlConfig;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.RealmList;
import io.realm.RealmResults;

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Flowable<User> updateUserInfo(String phoneNumber, String token) {
        return mDbHelper.updateUserInfo(phoneNumber, token);
    }

    @Override
    public Flowable<User> createNewUser() {
        return mDbHelper.createNewUser();
    }

    @Override
    public Single<LoginResponse> doUserLogin(LoginRequest request) {
        return mApiHelper.doUserLogin(request);
    }

    @Override
    public Single<SignUpResponse> doUserSignUp(SignUpRequest request) {
        return mApiHelper.doUserSignUp(request);
    }

    @Override
    public Single<LogoutResponse> doUserLogout() {
        return mApiHelper.doUserLogout();
    }

    @Override
    public String getCurrentUsername() {
        return mPreferencesHelper.getCurrentUsername();
    }

    @Override
    public void setCurrentUsername(String userName) {
        mPreferencesHelper.setCurrentUsername(userName);
    }

    @Override
    public String getCurrentPhoneNumber() {
        return mPreferencesHelper.getCurrentPhoneNumber();
    }

    @Override
    public void setCurrentPhoneNumber(String phoneNumber) {
        mPreferencesHelper.setCurrentPhoneNumber(phoneNumber);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void updateApiHeader(String token) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setAuthorization(token);
    }

    @Override
    public void updateUserInfo(
            String userId,
            LoggedInMode loggedInMode,
            String username,
            String phoneNumber,
            String email,
            String profilePicPath, String token) {
        setCurrentUsername(username);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);
        setCurrentUserId(userId);
        updateApiHeader(token);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                LoggedInMode.LOGGED_OUT,
                null,
                null,
                null,
                null,
                null);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String token) {
        mPreferencesHelper.setAccessToken(token);
    }

    @Override
    public ServiceAddress loadServiceAddress() {
        return mPreferencesHelper.loadServiceAddress();
    }

    @Override
    public void saveServiceAddress(ServiceAddress address) {
        mPreferencesHelper.saveServiceAddress(address);
    }

    @Override
    public Single<AppointmentCreateResponse> createAppointment(AppointmentCreateRequest dataRequest) {
        return mApiHelper.createAppointment(dataRequest);
    }

    @Override
    public Single<JSONObject> fetchAppointmentAll() {
        return mApiHelper.fetchAppointmentAll();
    }

    @Override
    public Single<JSONObject> fetchAppointment(AppointmentGetRequest request) {
        return mApiHelper.fetchAppointment(request);
    }

    @Override
    public Single<AppointmentDeleteResponse> deleteAppointment(AppointmentDeleteRequest request) {
        return mApiHelper.deleteAppointment(request);
    }

    @Override
    public Single<JSONObject> fetchOrder(OrderGetRequest request) {
        return mApiHelper.fetchOrder(request);
    }

    @Override
    public Single<JSONObject> fetchOrderAll() {
        return mApiHelper.fetchOrderAll();
    }

    @Override
    public Single<JSONObject> fetchReceipt(ReceiptGetRequest request) {
        return mApiHelper.fetchReceipt(request);
    }

    @Override
    public Single<JSONObject> fetchReceiptAll() {
        return mApiHelper.fetchReceiptAll();
    }

    @Override
    public Single<OrderCompletedResponse> markOrderCompleted(OrderCompletedRequest request) {
        return mApiHelper.markOrderCompleted(request);
    }

    @Override
    public Single<JSONObject> fetchUserProfile() {
        return mApiHelper.fetchUserProfile();
    }

    @Override
    public void logout() {
        mPreferencesHelper.logout();
    }

    @Override
    public boolean isUserLoggedIn() {
        return mPreferencesHelper.isUserLoggedIn();
    }

    @Override
    public Single<JSONObject> editOrder(OrderEditRequest request) {
        return mApiHelper.editOrder(request);
    }

    @Override
    public String getUserLastName() {
        return mPreferencesHelper.getUserLastName();
    }

    @Override
    public void setUserLastName(String lastName) {
        mPreferencesHelper.setUserLastName(lastName);
    }

    @Override
    public String getUserFirstName() {
        return mPreferencesHelper.getUserFirstName();
    }

    @Override
    public void setUserFirstName(String firstName) {
        mPreferencesHelper.setUserFirstName(firstName);
    }

    @Override
    public Observable<ArrayList<AppyServiceCategory>> seedDatabaseCategories() {
        return Observable.fromCallable(() -> {
            Type type = new TypeToken<ArrayList<AppyServiceCategory>>() {
            }.getType();
            GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            final Gson gson = builder.create();
            ArrayList<AppyServiceCategory> data = gson.fromJson(
                    DataUtils.loadJSONFromAsset(mContext,
                            ApiUrlConfig.SEED_DATABASE_SERVICE_CATEGORIES), type);
            return data;
        });
    }

    @Override
    public Observable<ArrayList<AppyService>> seedDatabaseServices() {
        return Observable.fromCallable(() -> {
            Type type = new TypeToken<ArrayList<AppyService>>() {
            }.getType();
            GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            Gson gson = builder.create();
            ArrayList<AppyService> data = gson.fromJson(
                    DataUtils.loadJSONFromAsset(mContext,
                            ApiUrlConfig.SEED_DATABASE_SERVICES), type);
            return data;
        });
    }

    @Override
    public ServiceOrderUserInput getServiceOrderUserInput() {
        return ServiceOrderUserInput.getInstance();
    }

    @Override
    public Single<JSONObject> verifyUser() {
        return mApiHelper.verifyUser();
    }

    @Override
    public Single<JSONObject> verifyTrue(String code) {
        return mApiHelper.verifyTrue(code);
    }


    @Override
    public Observable<ArrayList<ProductCategory>> seedDatabaseProductCategories() {
        return Observable.fromCallable(() -> {
            Type type = new TypeToken<ArrayList<ProductCategory>>() {
            }.getType();
            GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            final Gson gson = builder.create();
            ArrayList<ProductCategory> data = gson.fromJson(
                    DataUtils.loadJSONFromAsset(mContext,
                            ApiUrlConfig.SEED_DATABASE_PRODUCT_CATEGORIES), type);
            return data;
        });
    }

    @Override
    public Flowable<Boolean> addProductCategories(ArrayList<ProductCategory> categories) {
        return mDbHelper.addProductCategories(categories);
    }

    @Override
    public Flowable<Boolean> addProductTopics(ArrayList<ProductTopic> topics) {
        return mDbHelper.addProductTopics(topics);
    }

    @Override
    public Flowable<Boolean> addProductSubs(ArrayList<ProductSub> topics) {
        return mDbHelper.addProductSubs(topics);
    }

    @Override
    public Observable<ArrayList<ProductSub>> seedDatabaseProductSubs() {
        return Observable.fromCallable(() -> {
            Type type = new TypeToken<ArrayList<ProductSub>>() {
            }.getType();
            GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            final Gson gson = builder.create();
            ArrayList<ProductSub> data = gson.fromJson(
                    DataUtils.loadJSONFromAsset(mContext,
                            ApiUrlConfig.SEED_DATABASE_PRODUCT_SUBS), type);
            data = updateThumbnailsOfSub(data);
            return data;
        });
    }

    private ArrayList<ProductTopic> updateThumbnailsOfTopic(ArrayList<ProductTopic> data) {
        for (ProductTopic item : data) {
            item.thumbnail = "images/product/topic/" + item.id + ".png";
        }
        return data;
    }

    private ArrayList<ProductSub> updateThumbnailsOfSub(ArrayList<ProductSub> data) {
        for (ProductSub item : data) {
            item.thumbnail = "images/product/sub/" + item.id + ".jpg";
        }
        return data;
    }

    @Override
    public Observable<ArrayList<ProductTopic>> seedDatabaseProductTopics() {
        return Observable.fromCallable(() -> {
            Type type = new TypeToken<ArrayList<ProductTopic>>() {
            }.getType();
            GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            final Gson gson = builder.create();
            ArrayList<ProductTopic> data = gson.fromJson(
                    DataUtils.loadJSONFromAsset(mContext,
                            ApiUrlConfig.SEED_DATABASE_PRODUCT_TOPICS), type);
            data = updateThumbnailsOfTopic(data);
            return data;
        });
    }

    @Override
    public Flowable<RealmResults<ProductCategory>> getProductCategoriesByTopic(int idTopic) {
        return mDbHelper.getProductCategoriesByTopic(idTopic);
    }

    @Override
    public Flowable<RealmResults<ProductTopic>> getAllProductTopics() {
        return mDbHelper.getAllProductTopics();
    }

    @Override
    public Flowable<ProductTopic> getProductTopicById(int idTopic) {
        return mDbHelper.getProductTopicById(idTopic);
    }

    @Override
    public Flowable<RealmResults<ProductSub>> getSubProductCategoryByCategory(int idCategory) {
        return mDbHelper.getSubProductCategoryByCategory(idCategory);
    }

    @Override
    public Single<JSONObject> fetchProducts(ProductListRequest request) {
        return mApiHelper.fetchProducts(request);
    }

    @Override
    public Flowable<Boolean> addProducts(String userId, RealmList<Product> list) {
        return mDbHelper.addProducts(userId, list);
    }

    @Override
    public Flowable<RealmResults<Product>> getProductsBySubCategory(int idSub) {
        return mDbHelper.getProductsBySubCategory(idSub);
    }

    @Override
    public Flowable<Product> getProductById(int idProduct) {
        return mDbHelper.getProductById(idProduct);
    }

    @Override
    public Flowable<RealmResults<ProductCart>> getAllProductCarts(String userId) {
        return mDbHelper.getAllProductCarts(userId);
    }

    @Override
    public Flowable<ProductCart> updateProductCartItem(long idProductCart, boolean checked, int amount, String variantModelId) {
        return mDbHelper.updateProductCartItem(idProductCart, checked, amount, variantModelId);
    }

    @Override
    public Flowable<Boolean> removeProductCartItem(long idProductCart) {
        return mDbHelper.removeProductCartItem(idProductCart);
    }

    @Override
    public Flowable<Boolean> emptyProductCarts(String userId) {
        return mDbHelper.emptyProductCarts(userId);
    }

    @Override
    public Flowable<RealmResults<AppyAddress>> getAllShippingAddress(String userId) {
        return mDbHelper.getAllShippingAddress(userId);
    }

    @Override
    public Flowable<Boolean> setDefaultShippingAddress(String userId, long id) {
        return mDbHelper.setDefaultShippingAddress(userId, id);
    }

    @Override
    public Flowable<AppyAddress> getShippingAddress(String userId, long id) {
        return mDbHelper.getShippingAddress(userId, id);
    }

    @Override
    public Flowable<Boolean> removeShippingAddress(String userId, long id) {
        return mDbHelper.removeShippingAddress(userId, id);
    }

    @Override
    public Flowable<AppyAddress> getDefaultShippingAddress(String userId) {
        return mDbHelper.getDefaultShippingAddress(userId);
    }

    @Override
    public void setDefaultPaymentMethod(String userId, String methodName) {
        mPreferencesHelper.setDefaultPaymentMethod(userId, methodName);
    }

    @Override
    public String getDefaultPaymentMethod(String userId) {
        return mPreferencesHelper.getDefaultPaymentMethod(userId);
    }

    @Override
    public Flowable<RealmResults<ProductCart>> getAllCheckedProductCarts(String userId) {
        return mDbHelper.getAllCheckedProductCarts(userId);
    }

    @Override
    public Flowable<ProductOrder> addOrder(RealmResults<ProductCart> items,
                                           String paymentMethod, AppyAddress shippingAddress,
                                           String customerId, String customerName,
                                           float totalCost, float discount, long orderId) {
        return mDbHelper.addOrder(items, paymentMethod, shippingAddress, customerId,
                customerName, totalCost, discount, orderId);
    }

    @Override
    public Flowable<Integer> getTotalCountProductCarts(String userId) {
        return mDbHelper.getTotalCountProductCarts(userId);
    }

    @Override
    public Flowable<Boolean> addOrRemoveFavorite(long productId, String userId) {
        return mDbHelper.addOrRemoveFavorite(productId, userId);
    }

    @Override
    public Flowable<RealmResults<Product>> getAllProductFavorites(String userId) {
        return mDbHelper.getAllProductFavorites(userId);
    }

    @Override
    public Flowable<Boolean> isProductFavorite(String userId, long productId) {
        return mDbHelper.isProductFavorite(userId, productId);
    }

    @Override
    public Single<BannersResponse> fetchBanners() {
        return mApiHelper.fetchBanners();
    }

    @Override
    public Flowable<ProductFilter> saveProductFilter(String userId, String shippingFrom, String discount, float rating, String priceMin, String priceMax) {
        return mDbHelper.saveProductFilter(userId, shippingFrom, discount, rating, priceMin, priceMax);
    }

    @Override
    public Flowable<ProductFilter> getCurrentFilter(String userId) {
        return mDbHelper.getCurrentFilter(userId);
    }

    @Override
    public Flowable<RealmResults<Product>> getAllProductsFilter(String userId) {
        return mDbHelper.getAllProductsFilter(userId);
    }

    @Override
    public Flowable<Boolean> clearProductsLoaded() {
        return mDbHelper.clearProductsLoaded();
    }

    @Override
    public void setProductsSortCurrent(String userId, String sort) {
        mPreferencesHelper.setProductsSortCurrent(userId, sort);
    }

    @Override
    public String getProductsSortCurrent(String userId) {
        return mPreferencesHelper.getProductsSortCurrent(userId);
    }

    @Override
    public Flowable<Product> getProductCachedById(long idProduct) {
        return mDbHelper.getProductCachedById(idProduct);
    }

    @Override
    public Flowable<ProductCart> getProductCart(String userId, long productId, long variantId) {
        return mDbHelper.getProductCart(userId, productId, variantId);
    }

    @Override
    public Flowable<ProductOrder> getOrderById(String userId, long orderId) {
        return mDbHelper.getOrderById(userId, orderId);
    }

    @Override
    public Flowable<RealmResults<SearchItem>> getSearchHistory(String userId) {
        return mDbHelper.getSearchHistory(userId);
    }

    @Override
    public Flowable<Boolean> addSearchItems(ArrayList<SearchItem> items) {
        return mDbHelper.addSearchItems(items);
    }

    @Override
    public Flowable<Boolean> clearSearchHistory(String userId) {
        return mDbHelper.clearSearchHistory(userId);
    }

    @Override
    public Flowable<Boolean> addSearchItem(SearchItem item) {
        return mDbHelper.addSearchItem(item);
    }

    @Override
    public Flowable<RealmResults<SearchItem>> getSearchSuggestions() {
        return mDbHelper.getSearchSuggestions();
    }

    @Override
    public Single<ProductVariantResponse> fetchProductVariant(int productId) {
        return mApiHelper.fetchProductVariant(productId);
    }

    @Override
    public Flowable<RealmResults<ProductVariant>> getProductVariants(int productId) {
        return mDbHelper.getProductVariants(productId);
    }

    @Override
    public Flowable<RealmResults<ProductSub>> getProductCategoryIdsByTopic(int idTopic) {
        return mDbHelper.getProductCategoryIdsByTopic(idTopic);
    }

    @Override
    public Single<ApiResponse> addProductToCart(AddToCartRequest request) {
        return mApiHelper.addProductToCart(request);
    }

    @Override
    public Single<ApiResponse> fetchCartsServer() {
        return mApiHelper.fetchCartsServer();
    }

    @Override
    public Single<ApiResponse> emptyUserCarts() {
        return mApiHelper.emptyUserCarts();
    }

    @Override
    public Flowable<Boolean> syncAllProductCarts(String userId, ArrayList<ProductCartResponse> array) {
        return mDbHelper.syncAllProductCarts(userId, array);
    }

    @Override
    public Flowable<Boolean> syncAllShippingAddresses(String userId, RealmList<AppyAddress> addresses) {
        return mDbHelper.syncAllShippingAddresses(userId, addresses);
    }

    @Override
    public Flowable<Boolean> syncAllProductFavorite(String userId, ArrayList<Product> array) {
        return mDbHelper.syncAllProductFavorite(userId, array);
    }

    @Override
    public Single<ApiResponse> deleteProductToCart(DeleteCartRequest request) {
        return mApiHelper.deleteProductToCart(request);
    }

    @Override
    public Single<ApiResponse> editProductToCart(EditCartRequest request) {
        return mApiHelper.editProductToCart(request);
    }

    @Override
    public Single<ApiResponse> editProductCartVariant(EditCartVariantRequest request) {
        return mApiHelper.editProductCartVariant(request);
    }

    @Override
    public Flowable<Boolean> addProductVariants(RealmList<ProductVariant> variants) {
        return mDbHelper.addProductVariants(variants);
    }

    @Override
    public Single<ApiResponse> addUserWishList(AddWishListRequest request) {
        return mApiHelper.addUserWishList(request);
    }

    @Override
    public Single<ApiResponse> deleteUserWishList(DeleteWishListRequest request) {
        return mApiHelper.deleteUserWishList(request);
    }

    @Override
    public Single<ApiResponse> emptyUserWishList() {
        return mApiHelper.emptyUserWishList();
    }

    @Override
    public Flowable<Boolean> emptyFavorites(String userId) {
        return mDbHelper.emptyFavorites(userId);
    }

    @Override
    public Single<ApiResponse> fetchUserWishList() {
        return mApiHelper.fetchUserWishList();
    }

    @Override
    public boolean isLocalDatabaseUpdated() {
        return mPreferencesHelper.isLocalDatabaseUpdated();
    }

    @Override
    public void setLocalDatabaseUpdated(boolean isDone) {
        mPreferencesHelper.setLocalDatabaseUpdated(isDone);
    }

    @Override
    public void setCachedResponse(String command, String key, String response) {
        mPreferencesHelper.setCachedResponse(command, key, response);
    }

    @Override
    public String getCachedResponse(String command, String key) {
        return mPreferencesHelper.getCachedResponse(command, key);
    }

    @Override
    public Flowable<ProductVariant> getProductVariantById(String variantModelId) {
        return mDbHelper.getProductVariantById(variantModelId);
    }

    @Override
    public Single<GetSellerResponse> fetchSellerInformation(long sellerId) {
        return mApiHelper.fetchSellerInformation(sellerId);
    }

    @Override
    public Flowable<Boolean> addSeller(Seller seller) {
        return mDbHelper.addSeller(seller);
    }

    @Override
    public Flowable<Boolean> updateProductCartShippingFee(String userId, Bundle dataFees) {
        return mDbHelper.updateProductCartShippingFee(userId, dataFees);
    }

    @Override
    public void clearCachedResponse() {
        mPreferencesHelper.clearCachedResponse();
    }

    @Override
    public void setDefaultShippingLocation(String location) {
        mPreferencesHelper.setDefaultShippingLocation(location);
    }

    @Override
    public void setDefaultShippingPostCode(String postCode) {
        mPreferencesHelper.setDefaultShippingPostCode(postCode);
    }

    @Override
    public String getDefaultShippingPostCode() {
        return mPreferencesHelper.getDefaultShippingPostCode();
    }

    @Override
    public String getDefaultShippingLocation() {
        return mPreferencesHelper.getDefaultShippingLocation();
    }

    @Override
    public Single<GetShippingAddressResponse> fetchUserShippingAddress() {
        return mApiHelper.fetchUserShippingAddress();
    }

    @Override
    public Single<AddShippingAddressResponse> addUserShippingAddress(AddShippingAddressRequest request) {
        return mApiHelper.addUserShippingAddress(request);
    }

    @Override
    public Single<ApiResponse> setUserDefaultShippingAddress(int idAddress) {
        return mApiHelper.setUserDefaultShippingAddress(idAddress);
    }

    @Override
    public Single<ApiResponse> removeUserShippingAddress(int idAddress) {
        return mApiHelper.removeUserShippingAddress(idAddress);
    }

    @Override
    public Single<AddShippingAddressResponse> editUserShippingAddress(AddShippingAddressRequest request) {
        return mApiHelper.editUserShippingAddress(request);
    }

    @Override
    public Single<ApiResponse> checkoutProductOrder(CheckoutOrderRequest request) {
        return mApiHelper.checkoutProductOrder(request);
    }

    @Override
    public Single<VerifyOrderResponse> verifyProductOrder(VerifyOrderRequest request) {
        return mApiHelper.verifyProductOrder(request);
    }

    @Override
    public Single<GetShippingResponse> fetchShippingFee(GetShippingRequest request) {
        return mApiHelper.fetchShippingFee(request);
    }
}