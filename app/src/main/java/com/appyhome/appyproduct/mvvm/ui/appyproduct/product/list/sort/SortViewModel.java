package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class SortViewModel extends BaseViewModel<SortNavigator> {
    public SortOption[] sortOptions = {SortOption.POPULAR, SortOption.PRICE_HIGHEST, SortOption.PRICE_LOWEST, SortOption.LATEST, SortOption.RATING};

    public SortViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public SortOption getCurrentSortOption() {
        for (SortOption item : sortOptions) {
            if (item.checked.get())
                return item;
        }
        sortOptions[0].checked.set(true);
        return sortOptions[0];
    }
}
