package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ListProductOrderViewModel extends BaseViewModel<ListProductOrderNavigator> {
    public ListProductOrderViewModel(DataManager dataManager,
                                     SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private ArrayList<ProductOrder> getOrders(JSONObject jsonObject) throws JSONException {
        JSONArray array = new JSONArray(jsonObject.getString("message"));
        ArrayList<ProductOrder> orders = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            ProductOrder order = new ProductOrder();
            order.input((JSONObject) array.get(i));
            order.customer_id = getUserId();
            orders.add(order);
        }
        return orders;
    }

    public void syncAllProductOrders() {
        getCompositeDisposable().add(getDataManager().fetchUserProductOrders()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonObject -> {
                    // GET SUCCEEDED
                    if (jsonObject != null && jsonObject.getString("code").equals(ApiCode.OK_200)) {
                        getCompositeDisposable().add(getDataManager().saveProductOrders(getOrders(jsonObject), getUserId())
                                .take(1)
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(results -> {
                                    getNavigator().done_syncAllProductOrders(results);
                                }, Crashlytics::logException));
                    }
                }, Crashlytics::logException));
    }
}
