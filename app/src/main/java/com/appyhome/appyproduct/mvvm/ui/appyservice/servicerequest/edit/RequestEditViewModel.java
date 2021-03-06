package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.edit;

import android.content.Intent;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.ServiceOrderEditRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestType;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.molpay.molpayxdk.MOLPayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.functions.Consumer;

public class RequestEditViewModel extends RequestItemViewModel {
    public RequestEditViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setUpData(Intent intent) {
        if (intent.hasExtra("id") && intent.hasExtra("type")
                && intent.hasExtra("edit_code")) {
            String idNumber = intent.getStringExtra("id");
            int type = intent.getIntExtra("type", RequestType.TYPE_REQUEST);
            String editCode = intent.getStringExtra("edit_code");
            setIdNumber(idNumber);
            setType(type);
            setEditCode(editCode);
        }
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

    public void editOrder(String additional, String amount, String txn_ID) {
        ServiceOrderEditRequest request = new ServiceOrderEditRequest();
        request.setIdNumber(getIdNumber());
        request.setEditCode(getEditCode());
        JSONObject obj = new JSONObject();
        try {
            String value = additional + "_" + txn_ID + "::" + amount;
            obj.put("service1", value);
            request.setAdditional(obj.toString());
            editOrder(request);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    private void editOrder(ServiceOrderEditRequest request) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .editServiceOrder(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject response) throws Exception {
                        setIsLoading(false);
                        if (response != null && response.has(ApiCode.KEY_CODE)) {
                            if (response.getString(ApiCode.KEY_CODE).equals(ApiCode.OK_200)) {
                                // EDIT COMPLETED SUCCESSFULLY
                                setIsLoading(true);
                                getNavigator().doAfterDataUpdated();
                                return;
                            }
                        }
                        getNavigator().handleErrorService(null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                    }
                }));
    }

    public String getTXN_IDWhenPaymentReturned(Intent data) {
        try {
            JSONObject result = new JSONObject(data.getStringExtra(MOLPayActivity.MOLPayTransactionResult));
            if (result.getString("status_code").equals("00")) {
                // PAYMENT SUCCESS
                String txn_ID = result.getString("txn_ID");
                return txn_ID;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        return null;
    }
}
