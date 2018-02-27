package com.appyhome.appyproduct.mvvm.data;

import android.content.Context;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.data.local.db.DbHelper;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;
import com.appyhome.appyproduct.mvvm.data.local.prefs.PreferencesHelper;
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
import java.util.concurrent.Callable;

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
    public Single<JSONObject> verifyTrue() {
        return mApiHelper.verifyTrue();
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
            return data;
        });
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
    public Flowable<RealmResults<ProductCategory>> getProductCategoryByTopic(int idTopic) {
        return mDbHelper.getProductCategoryByTopic(idTopic);
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
    public Single<ProductListResponse> getProductsByIdCategory(ProductListRequest request) {
        return mApiHelper.getProductsByIdCategory(request);
    }
}