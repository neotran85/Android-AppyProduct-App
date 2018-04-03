package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONObject;

public class SortViewModel extends BaseViewModel<SortNavigator> {
    public SortOption[] sortOptions = {SortOption.POPULAR, SortOption.PRICE_HIGHEST, SortOption.PRICE_LOWEST, SortOption.LATEST, SortOption.RATING};
    public SortViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        String sortJson = getDataManager().getProductsSortCurrent(getUserId());
        SortOption.UNKNOWN.fromJson(sortJson);
        for (SortOption item : sortOptions) {
            if (item.getValue().equals(SortOption.UNKNOWN.getValue()))
                item.checked.set(true);
            else item.checked.set(false);
        }
    }
}
