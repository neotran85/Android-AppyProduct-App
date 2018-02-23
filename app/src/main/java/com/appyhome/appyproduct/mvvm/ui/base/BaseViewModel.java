package com.appyhome.appyproduct.mvvm.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseViewModel<N> extends ViewModel {

    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    public ObservableField<Integer> isLoadingVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> isContentVisible = new ObservableField<>(View.GONE);
    private N mNavigator;
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel() {
        mDataManager = null;
        mSchedulerProvider = null;
    }

    public BaseViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean value) {
        isLoading.set(value);
        isLoadingVisible.set(value ? View.VISIBLE : View.GONE);
        isContentVisible.set(value ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public boolean isUserLoggedIn() {
        return getDataManager().isUserLoggedIn();
    }
}
