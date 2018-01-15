package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.di.ApiInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class ApiHeader {

    private ProtectedApiHeader mProtectedApiHeader;
    private PublicApiHeader mPublicApiHeader;

    @Inject
    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }

    public static final class PublicApiHeader {

        @Expose
        @SerializedName("api_key")
        private String mApiKey;

        @Inject
        public PublicApiHeader(@ApiInfo String apiKey) {
            mApiKey = apiKey;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
        }
    }

    public static final class ProtectedApiHeader {

        @Expose
        @SerializedName("user_id")
        private String mUserId;

        @Expose
        @SerializedName("phone_number")
        private String mPhoneNumber;


        public ProtectedApiHeader(String userId, String phoneNumber) {
            this.mPhoneNumber = phoneNumber;
            this.mUserId = userId;
        }

        public String getPhoneNumber() {
            return mPhoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.mPhoneNumber = phoneNumber;
        }

        public String getUserId() {
            return mUserId;
        }

        public void setUserId(String userId) {
            this.mUserId = userId;
        }
    }
}
