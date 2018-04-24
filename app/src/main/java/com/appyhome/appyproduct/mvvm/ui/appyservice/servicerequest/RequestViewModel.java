package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import io.reactivex.Single;

public class RequestViewModel extends BaseViewModel<RequestNavigator> {

    public RequestViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchAllData() {
        setIsLoading(true);
        fetchDataByType(getDataManager().fetchAppointmentAll(), RequestType.TYPE_REQUEST);
        fetchDataByType(getDataManager().fetchOrderAll(), RequestType.TYPE_ORDER);
        fetchDataByType(getDataManager().fetchReceiptAll(), RequestType.TYPE_CLOSED);
    }

    private void fetchDataByType(Single<JSONObject> object, final int type) {
        getCompositeDisposable().add(object
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    handleServiceResult(response, type);
                }, throwable -> {
                    setIsLoading(false);
                    showEmptyList(type);
                }));
    }

    private JSONObject getItemInside(JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
            // get some_name_i_wont_know in str_Name
            String str_Name = keys.next();
            // get the value i care about
            JSONObject result = jsonObject.getJSONObject(str_Name);
            result.put("id", str_Name);
            return result;
        } catch (JSONException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
        return null;
    }

    private void handleServiceResult(JSONObject response, int type) {
        try {
            if (response != null && response.getString(ApiCode.KEY_CODE).equals(ApiCode.OK_200)) {
                if (response.getString(ApiMessage.KEY_CODE).equals(ApiMessage.LIST_EMPTY)) {
                    showEmptyList(type);
                    return;
                } else {
                    JSONArray arrayResult = new JSONArray(response.getString(ApiMessage.KEY_CODE));
                    if (arrayResult.length() > 0) {
                        ArrayList<RequestItemViewModel> arrayItems = new ArrayList<>();
                        for (int i = 0; i < arrayResult.length(); i++) {
                            JSONObject valueItem = getItemInside(arrayResult.getJSONObject(i));
                            if (valueItem != null) {
                                RequestItemViewModel item = new RequestItemViewModel(valueItem, type);
                                arrayItems.add(item);
                            }
                        }
                        getNavigator().showResultList(arrayItems, type);
                        return;
                    }
                }
            }
        } catch (JSONException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
        // Some error???
        // EMPTY LIST
        showEmptyList(type);
    }

    private void showEmptyList(int type) {
        getNavigator().showResultList(new ArrayList<RequestItemViewModel>(), type);
    }
}
