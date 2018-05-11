package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
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

import io.realm.RealmResults;

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

    private float mTotalCost = 0;
    private RealmResults<ProductCart> mCarts;
    private String mPaymentMethod = "";
    private AppyAddress mShippingAddress;
    private String customerName = "";
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
                    mShippingAddress = addressResult;
                    name.set(addressResult.recipient_name);
                    phoneNumber.set(addressResult.recipient_phone_number);
                    address.set(addressResult.getAddressText());
                    customerName = addressResult.recipient_name;
                    getAllCheckedProductCarts();
                }, Crashlytics::logException));
    }

    public void fetchPaymentMethods() {
        mPaymentMethod = getDataManager().getDefaultPaymentMethod(getUserId());
        isVisa.set(mPaymentMethod.equals(PaymentViewModel.PAYMENT_VISA));
        isMolpay.set(mPaymentMethod.equals(PaymentViewModel.PAYMENT_MOLPAY));
    }

    public void addOrder(String statusPayment, String promoCode, long orderId) {
        createOrderToServer(statusPayment, promoCode);
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
                    mCarts = items;
                    getNavigator().showCheckedItems(items, addressId);
                    if (items != null && items.size() > 0) {
                        totalCost = 0.0;
                        strCartIds = new ArrayList<>();
                        for (ProductCart item : items) {
                            totalCost = totalCost + (item.price * item.amount);
                            strCartIds.add(item.card_id + "");
                        }
                        productCost.set(DataUtils.roundNumber(totalCost, 2));
                        updateTotalShippingCost(addressId, TextUtils.join(",", strCartIds));
                    }
                }, Crashlytics::logException));
    }

    private Double getShippingCost(Object object) {
        if (object instanceof Double) {
            return (Double) object;
        } else if (object instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) object;
            if (arrayList.size() > 0) {
                try {
                    Double total = 0.0;
                    for (int i = 0; i < arrayList.size(); i++) {
                        total = total + Double.valueOf(arrayList.get(i).toString());
                    }
                    return total;
                } catch (Exception e) {
                    return 0.0;
                }
            }
        }
        return 0.0;
    }

    private void updateTotalShippingCost(int idAddress, String cartItemIds) {
        getCompositeDisposable().add(getDataManager()
                .verifyProductOrder(new VerifyOrderRequest(cartItemIds, idAddress))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result != null && result.code.equals(ApiCode.OK_200)) {
                        LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) result.message;
                        LinkedTreeMap<String, Object> shippingTreeMap = (LinkedTreeMap<String, Object>) linkedTreeMap.get("shipping");
                        shippingCostAll = 0.0;
                        for (String key : shippingTreeMap.keySet()) {
                            shippingCostAll = shippingCostAll + getShippingCost(shippingTreeMap.get(key));
                        }
                        totalShippingCost.set(shippingCostAll);
                        updatePricesAfterDiscount(productCost.get(), linkedTreeMap);
                    }
                }, Crashlytics::logException));
    }

    private void updatePricesAfterDiscount(Double totalBeforeDiscount, LinkedTreeMap<String, Object> linkedTreeMap) {
        double totalAfterDiscount = totalBeforeDiscount;
        discount.set("RM " + DataUtils.roundPrice(totalAfterDiscount) + "+ Shipping: RM " + totalShippingCost.get());
        if (linkedTreeMap.get("promo") instanceof ArrayList) {
            ArrayList<LinkedTreeMap<String, Double>> arrayList = (ArrayList<LinkedTreeMap<String, Double>>) linkedTreeMap.get("promo");
            if (arrayList != null && arrayList.size() > 0) {
                for (LinkedTreeMap<String, Double> object : arrayList) {
                    if (object != null) {
                        if (object.containsKey("10PERCENT")) {
                            totalAfterDiscount = totalBeforeDiscount * 0.9;
                            discount.set("RM " + DataUtils.roundPrice(totalAfterDiscount)
                                    + " (10% discounted)" + " + Shipping: RM " + totalShippingCost.get());
                        }
                    }
                }
            }
        }
        totalAllCost.set(totalAfterDiscount + totalShippingCost.get());
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

    public void createOrderToServer(String statusPayment, String promoCodeUsed) {
        CheckoutOrderRequest request = new CheckoutOrderRequest();
        request.address_id = addressId;
        request.card_id = TextUtils.join(",", strCartIds);
        request.shipping = shippingCostAll;
        request.price = totalCost;
        request.promocode_used = promoCodeUsed;
        request.status = statusPayment;

        getCompositeDisposable().add(getDataManager()
                .checkoutProductOrder(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result != null && result.isValid()) {
                        if (result.message instanceof String) {
                            String message = (String) result.message;
                            String[] resultStr = message.split(":");
                            if (resultStr != null && resultStr.length == 2) {
                                if (DataUtils.isNumeric(resultStr[1])) {
                                    long orderId = Long.valueOf(resultStr[1]);
                                    addOrderToDatabase(orderId);
                                }
                            }
                        }
                    }
                }, Crashlytics::logException));
    }

    private void addOrderToDatabase(long orderId) {
        getCompositeDisposable().add(getDataManager().addOrder(mCarts, mPaymentMethod,
                mShippingAddress, getUserId(), customerName, mTotalCost, 0, orderId)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(order -> {
                    // GET SUCCEEDED
                    if (order != null && order.isValid()) {
                        getNavigator().addOrderOk(order);
                    } else {
                        getNavigator().handleErrors(null);
                    }
                }, throwable -> {
                    getNavigator().handleErrors(throwable);
                    Crashlytics.logException(throwable);
                }));
    }

    public void updateUserCartAgain() {
        mFetchUserInfoViewModel.fetchUserData();
    }

    public void doVerifyOrderBeforePayment() {
        mVerifyOrderViewModel.doVerifyOrder();
    }
}
