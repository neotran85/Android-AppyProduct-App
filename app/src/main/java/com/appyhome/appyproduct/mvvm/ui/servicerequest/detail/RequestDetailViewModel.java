package com.appyhome.appyproduct.mvvm.ui.servicerequest.detail;

import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestDetailViewModel extends BaseViewModel<RequestDetailNavigator> {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> timeCreated = new ObservableField<>();
    public ObservableField<String> dateTime1 = new ObservableField<>();
    public ObservableField<String> dateTime2 = new ObservableField<>();
    public ObservableField<String> dateTime3 = new ObservableField<>();

    public RequestDetailViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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
        return address.get();
    }

    public String getTimeSlot1() {
        return dateTime1.get();
    }
    public String getTimeSlot2() {
        return dateTime2.get();
    }
    public String getTimeSlot3() {
        return dateTime3.get();
    }

    public String getTotalCost() {
        return price.get();
    }

    public String getDateCreated() {
        return timeCreated.get();
    }

    public String getNameService() {
        return title.get();
    }

    public void proceedData(String data) {
        try {
            JSONObject dataJSON = new JSONObject(data);
            JSONObject temp = new JSONObject(dataJSON.get("services").toString());
            String tempStr = temp.getString("service1");
            String[]result = tempStr.split("::");
            this.title.set(result[0]);
            this.price.set(result[1]);
            this.address.set(dataJSON.get("address").toString());
            this.timeCreated.set(dataJSON.get("time").toString());

            JSONObject datetime = new JSONObject(dataJSON.get("datetime").toString());
            if(datetime != null) {
                if(datetime.has("datetime1")) {
                    dateTime1.set(datetime.getString("datetime1"));
                }
                if(datetime.has("datetime2")) {
                    dateTime1.set(datetime.getString("datetime2"));
                }
                if(datetime.has("datetime3")) {
                    dateTime1.set(datetime.getString("datetime3"));
                }
            }

            JSONObject additional = new JSONObject(dataJSON.get("additional").toString());
            if(additional != null) {
                // Do something
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
