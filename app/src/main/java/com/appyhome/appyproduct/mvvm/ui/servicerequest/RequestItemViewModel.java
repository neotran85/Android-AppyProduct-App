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
    public ObservableField<String> safetyCode = new ObservableField<>();
    public ObservableField<String> additionalComments = new ObservableField<>();

    private JSONObject mItem;

    private int mType = 0;

    private String idNumber;

    public RequestItemViewModel() {

    }
    public RequestItemViewModel(JSONObject item, int type) {
        processData(item, type);
    }
    public void processData(JSONObject item, int type) {
        mItem = item;
        mType = type;
        try {
            if (mItem.has("id"))
                idNumber = mItem.get("id").toString();

            if (mItem.has("services")) {
                String serviceString = mItem.get("services").toString();
                if (serviceString.contains("{")) {
                    JSONObject temp = new JSONObject(serviceString);
                    String tempStr = temp.getString("service1");
                    String[] result = tempStr.split("::");
                    this.title.set(result[0]);
                    this.price.set(result[1]);
                } else {
                    this.title.set(serviceString);
                    String priceString = mItem.get("price").toString();
                    this.price.set(priceString);
                }
            }
            if (mItem.has("address"))
                this.address.set(mItem.get("address").toString());
            if (mItem.has("time"))
                this.timeCreated.set(mItem.get("time").toString());
            if (mItem.has("datetime")) {
                String dateTimeStr = mItem.get("datetime").toString();
                if(dateTimeStr.contains("{")) {
                    JSONObject datetime = new JSONObject(dateTimeStr);
                    if (datetime != null) {
                        if (datetime.has("datetime1")) {
                            dateTime1.set(datetime.getString("datetime1"));
                        }
                        if (datetime.has("datetime2")) {
                            dateTime2.set(datetime.getString("datetime2"));
                        }
                        if (datetime.has("datetime3")) {
                            dateTime3.set(datetime.getString("datetime3"));
                        }
                    }
                } else {
                    dateTime1.set(dateTimeStr);
                }
            }

            if (mItem.has("safety_code")) {
                safetyCode.set(mItem.getString("safety_code"));
            }

            if (mItem.has("additional")) {
                // Do something
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getType() {
        return mType;
    }

    public String getData() {
        return mItem.toString();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
