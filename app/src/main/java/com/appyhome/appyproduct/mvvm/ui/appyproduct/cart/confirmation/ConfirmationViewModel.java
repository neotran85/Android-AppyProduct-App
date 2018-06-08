package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ApiResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.CheckoutOrderRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.VerifyOrderRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class ConfirmationViewModel extends BaseViewModel<ConfirmationNavigator> {

    public ObservableField<Double> totalAllCost = new ObservableField<>(0.0);
    public ObservableField<Double> totalShippingCost = new ObservableField<>(0.0);
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> discount = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> address = new ObservableField<>("");
    public ObservableField<Double> productCost = new ObservableField<>(0.0);
    public ObservableField<Boolean> isVisa = new ObservableField<>(false);
    public ObservableField<Boolean> isMolpay = new ObservableField<>(false);
    private String mPaymentMethod = "";
    private int addressId = 0;
    private ArrayList<String> strCartIds;
    private double shippingCostAll = 0.0;
    private double totalCost = 0.0;
    private double totalCostAfterDiscount = 0.0;

    private FetchUserInfoViewModel mFetchUserInfoViewModel;
    private VerifyOrderViewModel mVerifyOrderViewModel;

    public ConfirmationViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mFetchUserInfoViewModel = new FetchUserInfoViewModel(dataManager, schedulerProvider);
        mVerifyOrderViewModel = new VerifyOrderViewModel(dataManager, schedulerProvider);
    }

    public void setNavigator(ConfirmationNavigator navigator) {
        super.setNavigator(navigator);
        mFetchUserInfoViewModel.setNavigator(navigator);
        mVerifyOrderViewModel.setNavigator(navigator);
    }

    public void getDefaultShippingAddress() {
        getCompositeDisposable().add(getDataManager().getDefaultShippingAddress(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(addressResult -> {
                    // GET SUCCEEDED
                    addressId = addressResult.id;
                    name.set(addressResult.recipient_name);
                    phoneNumber.set(addressResult.recipient_phone_number);
                    address.set(addressResult.getAddressText());
                    getAllCheckedProductCarts();
                }, Crashlytics::logException));
    }

    public void fetchPaymentMethods() {
        mPaymentMethod = getDataManager().getDefaultPaymentMethod(getUserId());
        isVisa.set(mPaymentMethod.equals(PaymentViewModel.PAYMENT_VISA));
        isMolpay.set(mPaymentMethod.equals(PaymentViewModel.PAYMENT_MOLPAY));
    }

    public void update() {
        fetchPaymentMethods();
        getDefaultShippingAddress();
    }

    private void getAllCheckedProductCarts() {
        getCompositeDisposable().add(getDataManager().getAllCheckedProductCarts(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(items -> {
                    // GET SUCCEEDED
                    getNavigator().showCheckedItems(items, addressId);
                    if (items != null && items.size() > 0) {
                        totalCost = 0.0;
                        strCartIds = new ArrayList<>();
                        for (ProductCart item : items) {
                            totalCost = totalCost + (item.price * item.amount);
                            strCartIds.add(item.cart_id + "");
                        }
                        productCost.set(DataUtils.roundNumber(totalCost, 2));
                        doVerifyOrderFirst(addressId, TextUtils.join(",", strCartIds));
                    }
                }, Crashlytics::logException));
    }

    private void doVerifyOrderFirst(int idAddress, String cartItemIds) {
        getCompositeDisposable().add(getDataManager()
                .verifyProductOrder(new VerifyOrderRequest(cartItemIds, idAddress))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result != null && result.code.equals(ApiCode.OK_200)) {
                        LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) result.message;
                        updatePricesAfterDiscount(productCost.get(), linkedTreeMap);
                    }
                }, Crashlytics::logException));
    }

    private void updatePricesAfterDiscount(Double totalBeforeDiscount, LinkedTreeMap<String, Object> linkedTreeMap) {
        totalCostAfterDiscount = totalBeforeDiscount;
        discount.set("RM " + DataUtils.roundPrice(totalCostAfterDiscount) + "+ Shipping: RM " + totalShippingCost.get());
        if (linkedTreeMap.get("promo") instanceof ArrayList) {
            ArrayList<LinkedTreeMap<String, Double>> arrayList = (ArrayList<LinkedTreeMap<String, Double>>) linkedTreeMap.get("promo");
            if (arrayList != null && arrayList.size() > 0) {
                for (LinkedTreeMap<String, Double> object : arrayList) {
                    if (object != null) {
                        if (object.containsKey("10PERCENT")) {
                            totalCostAfterDiscount = totalBeforeDiscount * 0.9;
                        }
                    }
                }
            }
        }
        updateAllCost();
    }

    public void updateAllCost() {
        totalAllCost.set(totalCostAfterDiscount + totalShippingCost.get());
        discount.set("RM " + DataUtils.roundPrice(totalCostAfterDiscount)
                + " (10% discounted)" + " + Shipping: RM " + totalShippingCost.get());
    }

    public String getNameOfUser() {
        String firstName = getDataManager().getUserFirstName();
        String lastName = getDataManager().getUserLastName();
        if (firstName != null && firstName.length() > 0)
            return firstName + " " + lastName;
        return "";
    }

    public String getEmailOfUser() {
        return getDataManager().getCurrentUserEmail();
    }

    public String getPhoneNumberOfUser() {
        return getDataManager().getCurrentPhoneNumber();
    }

    public void addOrder(String statusPayment, String promoCodeUsed, String transactionId) {
        CheckoutOrderRequest request = new CheckoutOrderRequest();
        request.address_id = addressId;
        request.cart_id = TextUtils.join(",", strCartIds);
        request.shipping = shippingCostAll;
        request.price = totalCost;
        request.promocode_used = promoCodeUsed;
        request.status = statusPayment;
        request.transaction_id = transactionId;

        getCompositeDisposable().add(getDataManager()
                .checkoutProductOrder(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result != null && result.isValid()) {
                        long idOrder = getOrderId(result);
                        addOrdersToDatabase(idOrder);
                    } else {
                        getNavigator().addOrderFailed(result != null ? result.message.toString() : "");
                    }
                }, this::addOrderFailed));
    }

    private void addOrderFailed(Throwable throwable) {
        Crashlytics.logException(throwable);
        getNavigator().addOrderFailed("");
    }

    private long getOrderId(ApiResponse result) {
        if (result.message instanceof String) {
            String message = (String) result.message;
            String[] resultStr = message.split(":");
            if (resultStr != null && resultStr.length == 2) {
                if (DataUtils.isNumeric(resultStr[1])) {
                    return Long.valueOf(resultStr[1]);
                }
            }
        }
        return -1;
    }

    private void addOrdersToDatabase(long idOrder) {
        getCompositeDisposable().add(getDataManager().fetchUserProductOrders()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(orderGetResponse -> {
                    // GET SUCCEEDED
                    if (orderGetResponse != null && orderGetResponse.isValid()) {
                        for (ProductOrder order : orderGetResponse.message) {
                            order.customer_id = getUserId();
                            if (order.id == idOrder) {
                                getNavigator().addOrderOk(order);
                            }
                        }
                        getCompositeDisposable().add(getDataManager().saveProductOrders(orderGetResponse.message, getUserId())
                                .take(1)
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(results -> {
                                }, Crashlytics::logException));
                    }
                }, Crashlytics::logException));
    }

    public void updateUserCartAgain() {
        mFetchUserInfoViewModel.fetchUserData();
    }

    public void doVerifyOrderBeforePayment() {
        mVerifyOrderViewModel.doVerifyOrder();
    }
}
