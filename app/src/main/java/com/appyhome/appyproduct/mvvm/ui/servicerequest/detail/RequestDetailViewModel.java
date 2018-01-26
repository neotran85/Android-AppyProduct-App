package com.appyhome.appyproduct.mvvm.ui.servicerequest.detail;

import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestStatus;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestType;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestDetailViewModel extends BaseViewModel<RequestDetailNavigator> {

    private int mType = 0;
    private RequestItemViewModel mDataModel;
    
    public RequestDetailViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mDataModel = new RequestItemViewModel();
    }

    public int isAdditionalServiceViewVisible() {
        return View.GONE;
    }

    public String getAdditionalServices() {
        return "";
    }

    public String getAdditionalDetail() {
        return "";
    }

    public int isAdditionalDetailViewVisible() {
        return View.GONE;
    }

    public String getAddressString() {
        return mDataModel.address.get();
    }

    public String getTimeSlot1() {
        return mDataModel.dateTime1.get();
    }

    public String getTimeSlot2() {
        return mDataModel.dateTime2.get();
    }

    public String getTimeSlot3() {
        return mDataModel.dateTime3.get();
    }

    public String getTotalCost() {
        return mDataModel.price.get();
    }

    public String getDateCreated() {
        return "Date created: " + mDataModel.timeCreated.get();
    }

    public String getNameService() {
        if(mDataModel == null) return "";
        return mDataModel.title.get();
    }

    public void processData(String data, int type) {
        mType = type;
        try {
            JSONObject dataJSON = new JSONObject(data);
            mDataModel.processData(dataJSON, type);
            AppLogger.d("RequestDetailViewModel: " + mDataModel.getIdNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int isSafetyCodeVisible() {
        return mType == RequestType.TYPE_ORDER ? View.VISIBLE : View.GONE;
    }

    public String getSafetyCode() {
        return "Safety Code: " + mDataModel.safetyCode.get();
    }

    public int isAddServiceVisible() {
        return mType == RequestType.TYPE_ORDER
                && mDataModel.getStatusOfOrder().equals(RequestStatus.PENDING_EXECUTION)
                ? View.VISIBLE : View.GONE;
    }

    public int isConfirmationVisible() {
        return mType == RequestType.TYPE_ORDER
                && mDataModel.getStatusOfOrder().equals(RequestStatus.PENDING_EXECUTION)
                ? View.VISIBLE : View.GONE;
    }

    public int isAdminReViewingVisible() {
        return mType == RequestType.TYPE_ORDER
                && mDataModel.getStatusOfOrder().equals(RequestStatus.ADMIN_REVIEW)
                ? View.VISIBLE : View.GONE;
    }

    public float getRating() {
        return mDataModel.getRating();
    }

    public int isTimeSlot2Visible() {
        return mDataModel.dateTime2.get() != null && mDataModel.dateTime2.get().length() > 0 ? View.VISIBLE : View.GONE;
    }
    public int isTimeSlot3Visible() {
        return mDataModel.dateTime3.get() != null && mDataModel.dateTime3.get().length() > 0 ? View.VISIBLE : View.GONE;
    }
    public int isTimeSlot1Visible() {
        return mDataModel.dateTime1.get() != null && mDataModel.dateTime1.get().length() > 0 ? View.VISIBLE : View.GONE;
    }
}
