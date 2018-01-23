package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.model.api.RequestResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestItemViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> timeCreated = new ObservableField<>();

    private JSONObject mItem;

    public RequestItemViewModel(JSONObject item) {
        mItem = item;
        try {
            JSONObject temp = new JSONObject(mItem.get("services").toString());
            String tempStr = temp.getString("service1");
            String[]result = tempStr.split("::");
            this.title = new ObservableField<>(result[0]);
            this.price = new ObservableField<>(result[1]);
            this.address = new ObservableField<>(mItem.get("address").toString());
            this.timeCreated = new ObservableField<>(mItem.get("time").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
