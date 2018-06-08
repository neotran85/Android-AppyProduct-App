package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ListProductOrderViewModel extends BaseViewModel<ListProductOrderNavigator> {
    private static final int STATUS_ACTIVE = 0;
    private static final int STATUS_HISTORY = 1;
    private static final int STATUS_CANCELED = 2;

    public ObservableField<ListProductOrderAdapter> activeAdapter = new ObservableField<>(new ListProductOrderAdapter());

    public ObservableField<ListProductOrderAdapter> historyAdapter = new ObservableField<>(new ListProductOrderAdapter());

    public ObservableField<ListProductOrderAdapter> canceledAdapter = new ObservableField<>(new ListProductOrderAdapter());

    public ListProductOrderViewModel(DataManager dataManager,
                                     SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void syncAllProductOrders() {
        getCompositeDisposable().add(getDataManager().fetchUserProductOrders()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(orderGetResponse -> {
                    // GET SUCCEEDED
                    if (orderGetResponse != null && orderGetResponse.isValid()) {
                        getCompositeDisposable().add(getDataManager().saveProductOrders(orderGetResponse.message, getUserId())
                                .take(1)
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(results -> {
                                    ListProductOrderAdapter vActiveAdapter = new ListProductOrderAdapter();
                                    ListProductOrderAdapter vHistoryAdapter = new ListProductOrderAdapter();
                                    ListProductOrderAdapter vCanceledAdapter = new ListProductOrderAdapter();
                                    for (ProductOrder order : results) {
                                        switch (order.status) {
                                            case STATUS_ACTIVE:
                                                vActiveAdapter.addItem(order, getNavigator());
                                                break;
                                            case STATUS_HISTORY:
                                                vHistoryAdapter.addItem(order, getNavigator());
                                                break;
                                            case STATUS_CANCELED:
                                                vCanceledAdapter.addItem(order, getNavigator());
                                                break;
                                        }
                                    }
                                    activeAdapter.set(vActiveAdapter);
                                    historyAdapter.set(vHistoryAdapter);
                                    canceledAdapter.set(vCanceledAdapter);
                                }, Crashlytics::logException));
                    }
                }, Crashlytics::logException));
    }

}
