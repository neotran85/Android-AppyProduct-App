package com.appyhome.appyproduct.mvvm.ui.account.login;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductCartResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductFavoriteResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.util.ArrayList;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    private String mPhoneNumber = "";
    private String mPassword = "";
    private int mTryCounter = 0;

    public LoginViewModel(DataManager dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void login(String phone, String password) {
        mPhoneNumber = phone;
        mPassword = password;
        mTryCounter = 0;
        getNavigator().showLoading();
        getCompositeDisposable().add(getDataManager()
                .doUserLogin(new LoginRequest(phone, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    handleLoginResponse(response);
                }, throwable -> {
                    getNavigator().closeLoading();
                    getNavigator().handleErrorService(throwable);
                }));
    }

    private void updateUserInfo(String phoneNumber, String token) {
        getCompositeDisposable().add(getDataManager().updateUserInfo(phoneNumber, token)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(user -> {
                    //UPDATE SUCCESS
                }, this::moveOnWhenFinishOrEvenError));
    }

    private void handleLoginResponse(LoginResponse response) {
        if (response != null && !response.isEmpty()) {
            String statusCode = response.getStatusCode();
            String message = response.getMessage();
            if (statusCode.equals(ApiCode.OK_200)) {
                if (message != null && message.length() > 0) {
                    setAccessToken(message);
                    setPhoneNumber(mPhoneNumber);
                    getDataManager().updateApiHeader(message);
                    getNavigator().showSuccessLogin();
                    getNavigator().doAfterLoginSucceeded();
                    updateUserInfo(mPhoneNumber, message);
                    return;
                }
            } else {
                // Failed
                if (mTryCounter <= 3) {
                    login("+" + mPhoneNumber, mPassword);
                    mTryCounter++;
                    return;
                } else {
                    // Account is exist
                    if (message != null) {
                        if (message.equals(ApiMessage.INVALID_PHONE_NUMBER)) {
                            // Account is not exist
                            getNavigator().showSignUpDialog();
                            return;
                        }
                    }
                }
            }
        }
        moveOnWhenFinishOrEvenError();
        getNavigator().showErrorServer();
    }

    public void setAccessToken(String token) {
        getDataManager().setAccessToken(token);
    }

    public void setPhoneNumber(String phoneNumber) {
        getDataManager().setCurrentPhoneNumber(phoneNumber);
    }

    public void fetchUserProfile() {
        getCompositeDisposable().add(getDataManager().getUserProfile()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(userGetResponse -> {
                    if (userGetResponse != null) {
                        if (userGetResponse.getString(ApiCode.KEY_CODE).equals(ApiCode.OK_200)) {
                            try {
                                if (userGetResponse.has(ApiMessage.KEY_CODE)) {
                                    JSONObject message = userGetResponse.getJSONObject(ApiMessage.KEY_CODE);
                                    if (message != null) {
                                        String lastNameStr = message.getString("last_name");
                                        getDataManager().setUserLastName(lastNameStr);

                                        String firstNameStr = message.getString("first_name");
                                        getDataManager().setUserFirstName(firstNameStr);

                                        String emailStr = message.getString("email");
                                        getDataManager().setCurrentUserEmail(emailStr);

                                        String phoneNumberStr = message.getString("phone_number");

                                        getDataManager().setCurrentPhoneNumber(phoneNumberStr);

                                        getDataManager().setCurrentUserId(phoneNumberStr);

                                        getDataManager().setCurrentUsername(phoneNumberStr);

                                        fetchCartsServer();

                                        return;
                                    }
                                }
                            } catch (Exception e) {
                                Crashlytics.logException(e);
                            }
                        }
                    }
                    moveOnWhenFinishOrEvenError();
                }, this::moveOnWhenFinishOrEvenError));

    }


    private void updateAllProductCarts(ArrayList<ProductCartResponse> arrayList) {
        getCompositeDisposable().add(getDataManager().updateAllProductCarts(getUserId(), arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // UPDATE CART SUCCESSFUL
                    // THEN FETCH WISH LIST
                    fetchUserWishList();
                }, this::moveOnWhenFinishOrEvenError));
    }


    private void fetchCartsServer() {
        getCompositeDisposable().add(getDataManager().fetchCartsServer()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        ArrayList<ProductCartResponse> arrayList = new ArrayList<>();
                        try {
                            LinkedTreeMap<String, ArrayList> linkedTreeMap = (LinkedTreeMap<String, ArrayList>) data.message;
                            Gson gson = new Gson();
                            for (String key : linkedTreeMap.keySet()) {
                                ArrayList array = linkedTreeMap.get(key);
                                for (int i = 0; i < array.size(); i++) {
                                    JSONObject object = DataUtils.convertToJsonObject((LinkedTreeMap<String, String>) array.get(i));
                                    ProductCartResponse item = gson.fromJson(object.toString(), ProductCartResponse.class);
                                    arrayList.add(item);
                                }
                            }
                            if (arrayList.size() > 0) {
                                updateAllProductCarts(arrayList);
                                return;
                            }
                        } catch (Exception e) {
                            Crashlytics.logException(e);
                        }
                    }
                    // IF NOT SUCCESS, CONTINUE TO FETCH WISH LIST
                    fetchUserWishList();
                }, this::moveOnWhenFinishOrEvenError));
    }

    public void fetchUserWishList() {
        getCompositeDisposable().add(getDataManager().getUserWishList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        ArrayList<ProductFavoriteResponse> arrayList = new ArrayList<>();
                        try {
                            LinkedTreeMap<String, ArrayList> linkedTreeMap = (LinkedTreeMap<String, ArrayList>) data.message;
                            Gson gson = new Gson();
                            for (String key : linkedTreeMap.keySet()) {
                                ArrayList array = linkedTreeMap.get(key);
                                for (int i = 0; i < array.size(); i++) {
                                    JSONObject object = DataUtils.convertToJsonObject((LinkedTreeMap<String, String>) array.get(i));
                                    ProductFavoriteResponse item = gson.fromJson(object.toString(), ProductFavoriteResponse.class);
                                    arrayList.add(item);
                                }
                            }
                            if (arrayList.size() > 0) {
                                updateWishList(arrayList);
                                return;
                            }
                        } catch (Exception e) {
                            Crashlytics.logException(e);
                        }
                    }
                    moveOnWhenFinishOrEvenError();
                }, this::moveOnWhenFinishOrEvenError));
    }

    private void moveOnWhenFinishOrEvenError() {
        moveOnWhenFinishOrEvenError(null);
    }
    private void moveOnWhenFinishOrEvenError(Throwable throwable) {
        getNavigator().closeLoading();
        getNavigator().doAfterFetchUserInfoCompleted();
        if (throwable != null)
            Crashlytics.logException(throwable);
    }

    private void updateWishList(ArrayList<ProductFavoriteResponse> arrayList) {
        getCompositeDisposable().add(getDataManager().updateAllProductFavorite(getUserId(), arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // UPDATE WISH LIST SUCCESSFUL
                    moveOnWhenFinishOrEvenError();
                }, this::moveOnWhenFinishOrEvenError));
    }

}
