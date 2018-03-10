package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductGalleryViewModel extends BaseViewModel<ProductGalleryNavigator> {
    private static final ArrayList<String> images = new ArrayList<>(Arrays.asList(
            "https://static1.squarespace.com/static/59a6fac79f8dcef7791c8518/59a86285c534a555b4a3ff71/59ab01f33e00bebf33c19d02/1519863837665/Four-Olive-Oil-Bottles-2048px-4.4.jpg?format=2500w",
            "https://www.photigy.com/wp-content/uploads/2015/04/Glenfiddich.jpg",
            "http://www.westlightphoto.com.my/wp-content/uploads/2016/01/Untitled_00044_4-810x1080.jpg",
            "https://s3-img.pixpa.com/large/faiyazhawa_481249_large.jpeg",
            "http://timstubbings.com/commercial/wp-content/uploads/2017/03/17-10334-pp_gallery/Marketing_and_advertising_photography_Kent-001-1(pp_w500_h333).jpg",
            "https://www.shell.in/business-customers/lubricants-for-business/lubricants-product-range/shell-argina-and-gadina-power-engine-oils/_jcr_content/pagePromo/image.img.800.jpeg/1457361752025/argina-gadina-products.jpeg"
    ));
    public ProductGalleryViewModel(DataManager dataManager,
                                   SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getPhotos() {}
}
