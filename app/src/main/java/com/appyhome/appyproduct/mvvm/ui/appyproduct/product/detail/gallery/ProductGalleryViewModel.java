package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductGalleryViewModel extends BaseViewModel<ProductGalleryNavigator> {

    public ProductGalleryViewModel(DataManager dataManager,
                                   SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getProductVariantById(String id) {
        getCompositeDisposable().add(getDataManager().getProductVariantById(id)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(variant -> {
                    getNavigator().showGallery(variant);
                }, Crashlytics::logException));
    }
}
