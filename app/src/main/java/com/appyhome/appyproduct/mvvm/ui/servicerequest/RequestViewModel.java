package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import io.reactivex.functions.Consumer;

public class RequestViewModel extends BaseViewModel<RequestNavigator> {

    public RequestViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllAppointments() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAppointmentAll()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject response) throws Exception {
                        setIsLoading(false);
                        AppLogger.d("handleServiceResult");
                        handleServiceResult(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    private JSONObject getItemInside(JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
            // get some_name_i_wont_know in str_Name
            String str_Name = keys.next();
            // get the value i care about
            return jsonObject.getJSONObject(str_Name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void handleServiceResult(JSONObject response) {
        try {
            if (response != null && response.getString("code").equals("200")) {
                try {
                    JSONArray arrayResult = new JSONArray(response.getString("message"));
                    if (arrayResult != null && arrayResult.length() > 0) {
                        ArrayList<RequestItemViewModel> arrayItems = new ArrayList<>();
                        try {
                            for (int i = 0; i < arrayResult.length(); i++) {
                                JSONObject valueItem = getItemInside(arrayResult.getJSONObject(i));
                                if (valueItem != null) {
                                    RequestItemViewModel item = new RequestItemViewModel(valueItem);
                                    arrayItems.add(item);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getNavigator().showResultList(arrayItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
