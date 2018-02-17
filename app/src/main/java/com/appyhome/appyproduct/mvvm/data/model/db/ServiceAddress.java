package com.appyhome.appyproduct.mvvm.data.model.db;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceAddress {
    public String number;
    public String street;
    public String area1;
    public String area2;
    public String city;
    public String code;

    public ServiceAddress() {
        number = "";
        street = "";
        area1 = "";
        area2 = "";
        city = "";
        code = "";
    }

    public ServiceAddress(String numberValue, String streetValue, String area1Value, String area2Value, String cityValue, String codeValue) {
        number = numberValue;
        street = streetValue;
        area1 = area1Value;
        area2 = area2Value;
        city = cityValue;
        code = codeValue;
    }

    public String toJSONString() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("number", number);
            jsonObject.put("street", street);
            jsonObject.put("area1", area1);
            jsonObject.put("area2", area2);
            jsonObject.put("city", city);
            jsonObject.put("code", code);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean fromJSONString(String json) {
        try {
            JSONObject object = new JSONObject(json);
            number = object.getString("number");
            street = object.getString("street");
            area1 = object.getString("area1");
            area2 = object.getString("area2");
            city = object.getString("city");
            code = object.getString("code");
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public String toString() {
        return "number: " + number + " | " +
                "street: " + street + " | " +
                "area1: " + area1 + " | " +
                "area2: " + area2 + " | " +
                "city: " + city + " | " +
                "code: " + code;
    }

}
