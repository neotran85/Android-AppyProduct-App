package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.crashlytics.android.Crashlytics;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;


public class UserGetResponse {

    @Expose
    @SerializedName("code")
    private String statusCode;

    @Expose
    @SerializedName("message")
    private String result;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void parseInfo() {
        if (result != null && result.length() > 0) {
            try {
                JSONObject json = new JSONObject(result);
                if (json.has("first_name")) {
                    firstName = json.getString("first_name");
                }
                if (json.has("last_name")) {
                    lastName = json.getString("last_name");
                }
                if (json.has("email")) {
                    email = json.getString("email");
                }
                if (json.has("phone_number")) {
                    phoneNumber = json.getString("phone_number");
                }
            } catch (Exception e) {
                Crashlytics.logException(e);
                return;
            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        String str = "first_name: " + firstName + " | ";
        str = str + "last_name: " + lastName + " | ";
        str = str + "email: " + email + " | ";
        str = str + "phone_number: " + phoneNumber + " | ";
        return str;
    }
}
