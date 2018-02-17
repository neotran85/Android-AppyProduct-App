package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import android.content.Intent;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderEditRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestType;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

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
        OrderEditRequest request = new OrderEditRequest();
        request.setIdNumber(getIdNumber());
        request.setEditCode(getEditCode());
        JSONObject obj = new JSONObject();
        try {
            String value = additional + "_" + txn_ID + "::" + amount;
            obj.put("service1", value);
            request.setAdditional(obj.toString());
            editOrder(request);
        } catch (Exception e) {

        }
    }

    private void editOrder(OrderEditRequest request) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .editOrder(request)
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
}
