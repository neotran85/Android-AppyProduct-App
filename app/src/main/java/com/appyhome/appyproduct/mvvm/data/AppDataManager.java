package com.appyhome.appyproduct.mvvm.data;

import android.content.Context;

import com.appyhome.appyproduct.mvvm.data.local.db.DbHelper;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;
import com.appyhome.appyproduct.mvvm.data.local.prefs.PreferencesHelper;
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
    public Single<LoginResponse> doUserLogin(LoginRequest.ServerLoginRequest
                                                     request) {
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
    public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
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
    public Single<JSONObject> getAppointmentAll() {
        return mApiHelper.getAppointmentAll();
    }

    @Override
    public Single<JSONObject> getAppointment(AppointmentGetRequest request) {
        return mApiHelper.getAppointment(request);
    }

    @Override
    public Single<AppointmentDeleteResponse> deleteAppointment(AppointmentDeleteRequest request) {
        return mApiHelper.deleteAppointment(request);
    }

    @Override
    public Single<JSONObject> getOrder(OrderGetRequest request) {
        return mApiHelper.getOrder(request);
    }

    @Override
    public Single<JSONObject> getOrderAll() {
        return mApiHelper.getOrderAll();
    }

    @Override
    public Single<JSONObject> getReceipt(ReceiptGetRequest request) {
        return mApiHelper.getReceipt(request);
    }

    @Override
    public Single<JSONObject> getReceiptAll() {
        return mApiHelper.getReceiptAll();
    }

    @Override
    public Single<OrderCompletedResponse> markOrderCompleted(OrderCompletedRequest request) {
        return mApiHelper.markOrderCompleted(request);
    }

    @Override
    public Single<JSONObject> getUserProfile() {
        return mApiHelper.getUserProfile();
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
    public void closeDatabase() {
        mDbHelper.closeDatabase();
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
            data = updateThumbnailsOfCategories(data);
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

    private ArrayList<ProductSub> updateThumbnailsOfSub(ArrayList<ProductSub> data) {
        for (ProductSub item : data) {
            item.thumbnail = "images/product/sub/" + item.id + ".jpg";
            //item.thumbnail = "images/product/sub/temp.png";
        }
        return data;
    }

    private ArrayList<ProductCategory> updateThumbnailsOfCategories(ArrayList<ProductCategory> data) {
        for (ProductCategory item : data) {
            item.thumbnail = "images/product/category/" + item.id + ".png";
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
    public Single<ProductListResponse> fetchProductsByIdCategory(ProductListRequest request) {
        return mApiHelper.fetchProductsByIdCategory(request);
    }

    @Override
    public Flowable<Boolean> addProducts(Product[] list) {
        return mDbHelper.addProducts(list);
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
    public Flowable<ProductCart> addProductToCart(String userId, int productId, int amountAdded) {
        return mDbHelper.addProductToCart(userId, productId, amountAdded);
    }

    @Override
    public Flowable<RealmResults<ProductCart>> getAllProductCarts(String userId) {
        return mDbHelper.getAllProductCarts(userId);
    }

    @Override
    public Flowable<Boolean> updateProductCartItem(long idProductCart, boolean checked, int amount) {
        return mDbHelper.updateProductCartItem(idProductCart, checked, amount);
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
    public Flowable<RealmResults<Address>> getAllShippingAddress(String userId) {
        return mDbHelper.getAllShippingAddress(userId);
    }

    @Override
    public Flowable<Boolean> addShippingAddress(String userId, String placeId, String name, String phoneNumber, String addressStr, boolean isDefault) {
        return mDbHelper.addShippingAddress(userId, placeId, name, phoneNumber, addressStr, isDefault);
    }

    @Override
    public Flowable<Boolean> setDefaultShippingAddress(String userId, long id) {
        return mDbHelper.setDefaultShippingAddress(userId, id);
    }

    @Override
    public Flowable<Address> getDefaultShippingAddress(String userId) {
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
    public Flowable<Boolean> addOrder(RealmResults<ProductCart> items,
                                      String paymentMethod, Address shippingAddress,
                                      String customerId, String customerName,
                                      float totalCost, float discount) {
        return mDbHelper.addOrder(items, paymentMethod, shippingAddress, customerId, customerName, totalCost, discount);
    }

    @Override
    public Flowable<Integer> getTotalCountProductCarts(String userId) {
        return mDbHelper.getTotalCountProductCarts(userId);
    }

    @Override
    public Flowable<Boolean> addOrRemoveFavorite(int productId, String userId) {
        return mDbHelper.addOrRemoveFavorite(productId, userId);
    }

    @Override
    public Flowable<RealmResults<ProductFavorite>> getAllProductFavorites(String userId) {
        return mDbHelper.getAllProductFavorites(userId);
    }

    @Override
    public Flowable<Boolean> isFavorite(int productId, String userId) {
        return mDbHelper.isFavorite(productId, userId);
    }

    @Override
    public Flowable<RealmResults<Product>> getAllProductsFavorited(ArrayList<Integer> ids) {
        return mDbHelper.getAllProductsFavorited(ids);
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
    public Flowable<RealmResults<Product>> getAllProductsFilter(String userId, int idSubCategory) {
        return mDbHelper.getAllProductsFilter(userId, idSubCategory);
    }

    @Override
    public Flowable<Boolean> clearProductCached() {
        return mDbHelper.clearProductCached();
    }
}