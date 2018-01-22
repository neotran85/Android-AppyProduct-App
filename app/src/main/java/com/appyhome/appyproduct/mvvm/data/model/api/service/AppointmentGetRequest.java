package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppointmentGetRequest {
    @Expose
    @SerializedName("id_number")
    private String idNumber;

    public AppointmentGetRequest(String idNumber) {
        idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
