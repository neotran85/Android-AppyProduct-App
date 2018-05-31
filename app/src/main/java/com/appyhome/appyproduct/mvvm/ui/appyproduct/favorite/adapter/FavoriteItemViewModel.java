package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class FavoriteItemViewModel extends ProductItemViewModel {

    public FavoriteItemViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void inputValue(Product product) {
        title.set(product.product_name);
        sellerName.set(product.seller_name);
        imageURL.set(product.avatar_name);
        productId = product.id;
        lowestPrice.set(product.lowest_price);
        rate.set(product.rate);
        rateCount.set(product.rate_count + "");
        isDiscount.set(false);
        favoriteCount.set(product.favorite_count + "");
        isProductFavorite.set(true);
    }
}
