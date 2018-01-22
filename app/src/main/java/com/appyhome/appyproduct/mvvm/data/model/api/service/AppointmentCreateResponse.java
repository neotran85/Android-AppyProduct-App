package com.appyhome.appyproduct.mvvm.data.model.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppointmentCreateResponse {

    @Expose
    @SerializedName("code")
    private String statusCode;

    @Expose
    @SerializedName("appointment_id")
    private String appointmentId;

    @Expose
    @SerializedName("message")
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AppointmentCreateResponse that = (AppointmentCreateResponse) object;

        if (statusCode != null ? !statusCode.equals(that.statusCode) : that.statusCode != null)
            return false;

        if (appointmentId != null ? !appointmentId.equals(that.appointmentId) : that.appointmentId != null)
            return false;

        return (message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (appointmentId != null ? appointmentId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "status_code = " + getStatusCode() + " | " + "message = " + getMessage()
                + " | " + "appointment id = " + appointmentId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
