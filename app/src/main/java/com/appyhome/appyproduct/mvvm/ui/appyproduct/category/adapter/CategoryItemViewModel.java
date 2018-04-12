package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class CategoryItemViewModel extends BaseViewModel<CategoryItemNavigator> {
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> imageURL = new ObservableField<>("");
    public ObservableField<Boolean> isActive = new ObservableField<>(false);

    public boolean isHighLight = false;

    public boolean isSub = false;

    private int idCategory;

    private String subIds;

    public CategoryItemViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public CategoryItemViewModel() {
        super();
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getSubIds() {
        return subIds;
    }

    public void setSubIds(String subIds) {
        this.subIds = subIds;
    }
}
