package com.appyhome.appyproduct.mvvm.ui.custom.detail;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class TextDetailViewModel extends BaseViewModel<TextDetailActivity> {
    public final ObservableField<String> title = new ObservableField<>("");
    public final ObservableField<String> detail = new ObservableField<>("");

    public TextDetailViewModel(DataManager dataManager,
                               SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setUp(Intent intent) {
        String titleStr = "";
        String detailStr = "";
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey("detail")) {
                detailStr = bundle.getString("detail");
            }
            if (bundle.containsKey("title")) {
                titleStr = bundle.getString("title");
            }
            getNavigator().setTitle(titleStr);
        }
        title.set(titleStr);
        detail.set(detailStr);
    }
}
