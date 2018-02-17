package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderGetRequest {
    @Expose
    @SerializedName("id_number")
    private String idNumber;

    public OrderGetRequest(String number) {
        idNumber = number;
    }

    public OrderGetRequest() {

    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
