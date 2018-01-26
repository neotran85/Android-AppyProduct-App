package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONException;
import org.json.JSONObject;

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
}
