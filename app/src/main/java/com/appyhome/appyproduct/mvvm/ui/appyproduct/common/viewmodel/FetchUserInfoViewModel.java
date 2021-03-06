package com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel;


import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ApiResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductCartResponse;
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

import io.realm.RealmList;

public class FetchUserInfoViewModel extends BaseViewModel<FetchUserInfoNavigator> {

    private boolean mIsFetchCartsStarted = false;

    private boolean mIsFetchProfileStarted = false;

    private boolean mIsFetchWishListStarted = false;

    private boolean mIsFetchShippingAddresses = false;

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
                        if (data.isEmpty()) {
                            updateAllProductCarts(arrayList);
                        } else {
                            try {
                                arrayList = processCartData(data);
                                updateAllProductCarts(arrayList);
                            } catch (Exception e) {
                                onFetchFailed(e);
                            }
                        }
                    } else {
                        onFetchFailed(null);
                    }

                }, this::onFetchFailed));
    }

    private ArrayList<Product> processWishListData(ApiResponse data) {
        ArrayList<Product> arrayList = new ArrayList<>();
        if (!data.message.equals(ApiMessage.WISHLIST_EMPTY)) {
            try {
                LinkedTreeMap<String, ArrayList> linkedTreeMap = (LinkedTreeMap<String, ArrayList>) data.message;
                Gson gson = new Gson();
                for (String key : linkedTreeMap.keySet()) {
                    if (key != null && key.equals("content")) {
                        ArrayList array = linkedTreeMap.get(key);
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject object = DataUtils.convertToJsonObject((LinkedTreeMap<String, String>) array.get(i));
                            Product item = gson.fromJson(object.toString(), Product.class);
                            arrayList.add(item);
                        }
                    }
                    if (key != null && key.equals("padding")) {
                        RealmList<Product> relatedArrayList = new RealmList<>();
                        ArrayList array = linkedTreeMap.get(key);
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject object = DataUtils.convertToJsonObject((LinkedTreeMap<String, String>) array.get(i));
                            Product item = gson.fromJson(object.toString(), Product.class);
                            item.related = getUserId() + "_wishlist";
                            relatedArrayList.add(item);
                        }
                        addProductsPadding(relatedArrayList, "wishlist");
                    }
                }
            } catch (Exception e) {
                Crashlytics.logException(e);
            }
        }
        return arrayList;
    }

    private void addProductsPadding(RealmList<Product> products, String tag) {
        getCompositeDisposable().add(getDataManager().addProducts(getUserId(), products, tag)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                }, Crashlytics::logException));
    }

    private ArrayList<ProductCartResponse> processCartData(ApiResponse data) {
        ArrayList<ProductCartResponse> arrayList = new ArrayList<>();
        LinkedTreeMap<String, ArrayList> linkedTreeMap = (LinkedTreeMap<String, ArrayList>) data.message;
        Gson gson = new Gson();
        for (String key : linkedTreeMap.keySet()) {
            if (key != null && !key.equals("padding")) {
                ArrayList array = linkedTreeMap.get(key);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = DataUtils.convertToJsonObject((LinkedTreeMap<String, String>) array.get(i));
                    ProductCartResponse item = gson.fromJson(object.toString(), ProductCartResponse.class);
                    arrayList.add(item);
                }
            }
            if (key != null && key.equals("padding")) {
                RealmList<Product> relatedArrayList = new RealmList<>();
                ArrayList array = linkedTreeMap.get(key);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = DataUtils.convertToJsonObject((LinkedTreeMap<String, String>) array.get(i));
                    Product item = gson.fromJson(object.toString(), Product.class);
                    item.related = getUserId() + "_cart";
                    relatedArrayList.add(item);
                }
                addProductsPadding(relatedArrayList, "cart");
            }
        }
        return arrayList;
    }

    public void fetchShippingAddresses() {
        mIsFetchShippingAddresses = true;
        getCompositeDisposable().add(getDataManager().fetchUserShippingAddress()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        updateAllShippingAddresses(data.message);
                    } else {
                        onFetchFailed(null);
                    }
                }, this::onFetchFailed));
    }

    private void updateAllShippingAddresses(RealmList<AppyAddress> addresses) {
        getCompositeDisposable().add(getDataManager().syncAllShippingAddresses(getUserId(), addresses)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // DONE FETCHING FOR USER DATA
                    if (getNavigator() != null)
                        getNavigator().onFetchUserInfo_Done();
                }, this::onFetchFailed));
    }

    private void updateAllProductCarts(ArrayList<ProductCartResponse> arrayList) {
        getCompositeDisposable().add(getDataManager().syncAllProductCarts(getUserId(), arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // UPDATE CART SUCCESSFUL
                    // THEN FETCH WISH LIST
                    fetchUserWishList();
                }, this::onFetchFailed));
    }

    private void fetchUserWishList() {
        mIsFetchWishListStarted = true;
        getCompositeDisposable().add(getDataManager().fetchUserWishList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        ArrayList<Product> arrayList = processWishListData(data);
                        updateWishList(arrayList);
                    } else {
                        onFetchFailed(null);
                    }
                }, this::onFetchFailed));
    }

    private void updateWishList(ArrayList<Product> arrayList) {
        getCompositeDisposable().add(getDataManager().syncAllProductFavorite(getUserId(), arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // UPDATE WISH LIST SUCCESSFULLY, THEN FETCH SHIPPING ADDRESS
                    fetchShippingAddresses();
                }, this::onFetchFailed));
    }

    public void fetchUserData() {
        // START FROM FETCH USER PROFILE
        fetchUserProfile();
    }

    private void onFetchFailed(Throwable throwable) {
        if (throwable != null) {
            Crashlytics.logException(throwable);
        }
        FetchUserInfoNavigator navigator = getNavigator();
        if (!mIsFetchProfileStarted) fetchUserProfile();
        else if (!mIsFetchCartsStarted) fetchCartsServer();
        else if (!mIsFetchWishListStarted) fetchUserWishList();
        else if (!mIsFetchShippingAddresses) fetchShippingAddresses();
        else if (navigator != null)
            navigator.onFetchUserInfo_Failed();
    }

    private void fetchUserProfile() {
        mIsFetchProfileStarted = true;
        getCompositeDisposable().add(getDataManager().fetchUserProfile()
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
                }, this::onFetchFailed));
    }

    public void fetchAndSyncWishListServer() {
        mIsFetchWishListStarted = true;
        getCompositeDisposable().add(getDataManager().fetchUserWishList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        ArrayList<Product> arrayList = processWishListData(data);
                        updateWishListNow(arrayList);
                    }
                }, Crashlytics::logException));
    }

    public void fetchAndSyncCartsServer() {
        getCompositeDisposable().add(getDataManager().fetchCartsServer()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        ArrayList<ProductCartResponse> arrayList = new ArrayList<>();
                        if (data.isEmpty()) {
                            updateAllProductCartsNow(arrayList);
                        } else {
                            try {
                                arrayList = processCartData(data);
                                updateAllProductCartsNow(arrayList);
                            } catch (Exception e) {
                                Crashlytics.logException(e);
                            }
                        }
                    }
                }, Crashlytics::logException));
    }

    private void updateWishListNow(ArrayList<Product> arrayList) {
        getCompositeDisposable().add(getDataManager().syncAllProductFavorite(getUserId(), arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    if (success)
                        getNavigator().onFetchUserInfo_Done();
                }, throwable -> {
                    getNavigator().onFetchUserInfo_Failed();
                    Crashlytics.logException(throwable);
                }));
    }

    private void updateAllProductCartsNow(ArrayList<ProductCartResponse> arrayList) {
        getCompositeDisposable().add(getDataManager().syncAllProductCarts(getUserId(), arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    if (success)
                        getNavigator().onFetchUserInfo_Done();
                }, throwable -> {
                    getNavigator().onFetchUserInfo_Failed();
                    Crashlytics.logException(throwable);
                }));
    }


}
