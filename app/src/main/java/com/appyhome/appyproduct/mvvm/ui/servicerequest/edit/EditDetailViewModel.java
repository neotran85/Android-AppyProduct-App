package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderCompletedRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderCompletedResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderGetRequest;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import io.reactivex.functions.Consumer;

public class EditDetailViewModel extends BaseViewModel<EditDetailNavigator> {

    private RequestItemViewModel mDataModel;
    private int mType = 0;
    public EditDetailViewModel(DataManager dataManager,
                               SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mDataModel = new RequestItemViewModel();
    }

    public void processData(String data, int type) {
        mType = type;
        try {
            JSONObject dataJSON = new JSONObject(data);
            mDataModel.processData(dataJSON, type);
            AppLogger.d("EditDetailViewModel: " + mDataModel.getIdNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderInformation() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getOrder(new OrderGetRequest(mDataModel.getIdNumber()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject response) throws Exception {
                        if(response != null) {
                            if(response.has("code")) {
                                if(response.getString("code").equals("200")) {
                                    if(response.has("message")) {
                                        String result = response.getString("message");
                                        JSONObject object = new JSONObject(result);
                                        JSONObject resultJSON = getItemInside(object);
                                        getNavigator().goBackAfterSubmitting(resultJSON.toString());
                                    }
                                }
                            }
                        }
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                    }
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
            e.printStackTrace();
        }
        return null;
    }

    public void markOrderCompleted(String comments, String rating) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .markOrderCompleted(new OrderCompletedRequest(mDataModel.getIdNumber(), comments, rating))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<OrderCompletedResponse>() {
                    @Override
                    public void accept(OrderCompletedResponse response) throws Exception {
                        if(response != null && response.getStatusCode().equals("200")) {
                            // MARK COMPLETED SUCCESS
                            setIsLoading(true);
                            updateOrderInformation();
                        }
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
