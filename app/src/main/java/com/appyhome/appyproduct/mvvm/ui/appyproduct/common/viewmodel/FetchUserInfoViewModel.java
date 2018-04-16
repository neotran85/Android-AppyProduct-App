package com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel;


import com.appyhome.appyproduct.mvvm.data.DataManager;
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

public class FetchUserInfoViewModel extends BaseViewModel<FetchUserInfoNavigator> {

    private boolean mIsFetchCartsStarted = false;

    private boolean mIsFetchProfileStarted = false;

    private boolean mIsFetchWishListStarted = false;

    public FetchUserInfoViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private void fetchCartsServer() {
        mIsFetchCartsStarted = true;
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
                            updateAllProductCarts(arrayList);
                        } catch (Exception e) {
                            onFetchFailed(e);
                        }
                    } else {
                        onFetchFailed(null);
                    }

                }, this::onFetchFailed));
    }

    private void updateAllProductCarts(ArrayList<ProductCartResponse> arrayList) {
        getCompositeDisposable().add(getDataManager().updateAllProductCarts(getUserId(), arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // UPDATE CART SUCCESSFUL
                    // THEN FETCH WISH LIST
                    fetchUserWishList();
                }, Crashlytics::logException));
    }

    private void fetchUserWishList() {
        mIsFetchWishListStarted = true;
        getCompositeDisposable().add(getDataManager().getUserWishList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        ArrayList<ProductFavoriteResponse> arrayList = new ArrayList<>();
                        if (!data.message.equals(ApiMessage.WISHLIST_EMPTY)) {
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
                            } catch (Exception e) {
                                Crashlytics.logException(e);
                            }
                        }
                        updateWishList(arrayList);
                    } else {
                        onFetchFailed(null);
                    }
                }, this::onFetchFailed));
    }

    private void updateWishList(ArrayList<ProductFavoriteResponse> arrayList) {
        getCompositeDisposable().add(getDataManager().syncAllProductFavorite(getUserId(), arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // UPDATE WISH LIST SUCCESSFUL
                    getNavigator().onFetchUserInfo_Done();
                }, Crashlytics::logException));
    }

    public void fetchUserData() {
        // START FROM FETCH USER PROFILE
        fetchUserProfile();
    }

    private void onFetchFailed(Throwable throwable) {
        if (throwable != null) {
            Crashlytics.logException(throwable);
        }
        if (!mIsFetchProfileStarted) fetchUserProfile();
        else if (!mIsFetchCartsStarted) fetchCartsServer();
        else if (!mIsFetchWishListStarted) fetchUserWishList();
        else getNavigator().onFetchUserInfo_Failed();
    }

    private void fetchUserProfile() {
        mIsFetchProfileStarted = true;
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

                                        // AFTER UPDATING USER INFO, FETCH PRODUCT CARTS
                                        fetchCartsServer();
                                        return;
                                    }
                                }
                            } catch (Exception e) {
                                onFetchFailed(e);
                            }
                        }
                    }
                    onFetchFailed(null);
                }, Crashlytics::logException));

    }
}