package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ServiceOrderEditRequest {
    @Expose
    @SerializedName("id_number")
    private String idNumber;

    @Expose
    @SerializedName("edit_code")
    private String editCode;

    @Expose
    @SerializedName("additional")
    private String additional;

    public ServiceOrderEditRequest() {

    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEditCode() {
        return editCode;
    }

    public void setEditCode(String editCode) {
        this.editCode = editCode;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
}
