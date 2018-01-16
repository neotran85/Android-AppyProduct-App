package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.model.api.RequestResponse;

public class RequestItemViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();

    private RequestResponse.Request mRequest;

    public RequestItemViewModel(RequestResponse.Request request) {
        mRequest = request;
        this.title = new ObservableField<>(request.getTitle());
        this.content = new ObservableField<>(request.getDescription());
        this.date = new ObservableField<>(request.getDate());
    }

}
