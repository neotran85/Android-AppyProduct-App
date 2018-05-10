package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.model.api.product.VerifyOrderRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentViewModel;
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
    public ObservableField<Float> productCost = new ObservableField<>(0.0f);
    public ObservableField<Boolean> isVisa = new ObservableField<>(false);
    public ObservableField<Boolean> isMolpay = new ObservableField<>(false);

    private float mTotalCost = 0;
    private RealmResults<ProductCart> mCarts;
    private String mPaymentMethod = "";
    private AppyAddress mShippingAddress;
    private String customerName = "";
    private int addressId = 0;

    public ConfirmationViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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

    public void addOrder(long orderId) {
        getCompositeDisposable().add(getDataManager().addOrder(mCarts, mPaymentMethod,
                mShippingAddress, getUserId(), customerName, mTotalCost, 0, orderId)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(order -> {
                    // GET SUCCEEDED
                    if (order != null && order.isValid()) {
                        //getNavigator().showAlert("Order added");
                        getNavigator().addOrderOk(order);
                    } else {
                        getNavigator().handleErrors(null);
                    }
                }, throwable -> {
                    getNavigator().handleErrors(throwable);
                    Crashlytics.logException(throwable);
                }));
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
                        float totalCost = 0;
                        ArrayList<String> strIds = new ArrayList<>();
                        for (ProductCart item : items) {
                            totalCost = totalCost + (item.price * item.amount);
                            strIds.add(item.card_id + "");
                        }
                        productCost.set(DataUtils.roundNumber(totalCost, 2));
                        updateTotalShippingCost(addressId, TextUtils.join(",", strIds));
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
                        Double shippingCostAll = 0.0;
                        for (String key : shippingTreeMap.keySet()) {
                            shippingCostAll = shippingCostAll + getShippingCost(shippingTreeMap.get(key));
                        }
                        totalShippingCost.set(shippingCostAll);
                        updatePricesAfterDiscount(productCost.get(), linkedTreeMap);
                    }
                }, Crashlytics::logException));
    }

    private void updatePricesAfterDiscount(Float totalBeforeDiscount, LinkedTreeMap<String, Object> linkedTreeMap) {
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
}
