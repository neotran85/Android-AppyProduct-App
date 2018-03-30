package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {
    public ObservableField<Boolean> isHistoryVisible = new ObservableField<>(true);
    public ObservableField<Boolean> isSuggestionsVisible = new ObservableField<>(false);
    public SearchViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
