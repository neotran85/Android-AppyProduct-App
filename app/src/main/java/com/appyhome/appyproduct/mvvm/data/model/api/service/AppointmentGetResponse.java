package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppointmentGetResponse {

    @Expose
    @SerializedName("code")
    private String statusCode;

    @Expose
    @SerializedName("message")
    private String result;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AppointmentGetResponse that = (AppointmentGetResponse) object;
        if (statusCode != null ? !statusCode.equals(that.getStatusCode()) : that.statusCode != null)
            return false;
        return (result != null ? !result.equals(that.result) : that.result != null);
    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (this.result != null ? this.result.hashCode() : 0);
        return result;
    }

}
