package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.databinding.ObservableField;

import org.json.JSONObject;

public class RequestItemViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> timeCreated = new ObservableField<>();
    public ObservableField<String> dateTime1 = new ObservableField<>();
    public ObservableField<String> dateTime2 = new ObservableField<>();
    public ObservableField<String> dateTime3 = new ObservableField<>();

    private JSONObject mItem;

    public RequestItemViewModel(JSONObject item) {
        mItem = item;
        try {
            JSONObject temp = new JSONObject(mItem.get("services").toString());
            String tempStr = temp.getString("service1");
            String[]result = tempStr.split("::");
            this.title.set(result[0]);
            this.price.set(result[1]);
            this.address.set(mItem.get("address").toString());
            this.timeCreated.set(mItem.get("time").toString());

            JSONObject datetime = new JSONObject(mItem.get("datetime").toString());
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

            JSONObject additional = new JSONObject(mItem.get("additional").toString());
            if(additional != null) {
                // Do something
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getData() {
        return mItem.toString();
    }
}
