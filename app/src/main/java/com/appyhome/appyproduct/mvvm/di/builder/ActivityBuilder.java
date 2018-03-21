package com.appyhome.appyproduct.mvvm.di.builder;

import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivityModule;
import com.appyhome.appyproduct.mvvm.ui.account.myprofile.MyProfileFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.account.myprofile.textinput.TextInputDialogProvider;
import com.appyhome.appyproduct.mvvm.ui.account.register.RegisterActivity;
import com.appyhome.appyproduct.mvvm.ui.account.register.RegisterActivityModule;
import com.appyhome.appyproduct.mvvm.ui.account.register.verify.VerifyActivity;
import com.appyhome.appyproduct.mvvm.ui.account.register.verify.VerifyActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed.OrderCompleteActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed.OrderCompleteActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.ConfirmationActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.ConfirmationActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress.NewAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress.NewAddressActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.CategoryActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.CategoryActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.FavoriteFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.ProductGalleryActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.ProductGalleryActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter.FilterFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.SearchActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.SearchActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.topic.ProductTopicFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.ProductTrackingActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.ProductTrackingActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1.ServicesStep1Activity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1.ServicesStep1ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step2.ServicesStep2Activity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step2.ServicesStep2ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step3.ServicesStep3Activity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step3.ServicesStep3ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step4.ServicesStep4Activity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step4.ServicesStep4ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step5.ServicesStep5Activity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step5.ServicesStep5ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.confirm.RequestConfirmedActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.confirm.RequestConfirmedActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.detail.RequestDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.detail.RequestDetailActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.edit.RequestEditActivity;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.edit.RequestEditActivityModule;
import com.appyhome.appyproduct.mvvm.ui.common.browser.BrowserActivity;
import com.appyhome.appyproduct.mvvm.ui.common.browser.BrowserActivityModule;
import com.appyhome.appyproduct.mvvm.ui.common.custom.detail.TextDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.common.custom.detail.TextDetailActivityModule;
import com.appyhome.appyproduct.mvvm.ui.feed.FeedActivity;
import com.appyhome.appyproduct.mvvm.ui.feed.FeedActivityModule;
import com.appyhome.appyproduct.mvvm.ui.feed.blogs.BlogFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivityModule;
import com.appyhome.appyproduct.mvvm.ui.splash.SplashActivity;
import com.appyhome.appyproduct.mvvm.ui.splash.SplashActivityModule;
import com.appyhome.appyproduct.mvvm.ui.tabs.home.HomeFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.tabs.mywishlist.MyWishListFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.tabs.notification.NotificationFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.tabs.userpage.UserPageFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = TextDetailActivityModule.class)
    abstract TextDetailActivity bindTextDetailActivity();

    @ContributesAndroidInjector(modules = RequestEditActivityModule.class)
    abstract RequestEditActivity bindRequestEditActivity();

    @ContributesAndroidInjector(modules = ServicesStep1ActivityModule.class)
    abstract ServicesStep1Activity bindBookingServicesActivity();

    @ContributesAndroidInjector(modules = ServicesStep2ActivityModule.class)
    abstract ServicesStep2Activity bindServicesStep2Activity();

    @ContributesAndroidInjector(modules = ServicesStep3ActivityModule.class)
    abstract ServicesStep3Activity bindServicesStep3Activity();

    @ContributesAndroidInjector(modules = ServicesStep4ActivityModule.class)
    abstract ServicesStep4Activity bindServicesStep4Activity();

    @ContributesAndroidInjector(modules = ServicesStep5ActivityModule.class)
    abstract ServicesStep5Activity bindServicesStep5Activity();

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector(modules = BrowserActivityModule.class)
    abstract BrowserActivity bindBrowserActivity();

    @ContributesAndroidInjector(modules = RequestConfirmedActivityModule.class)
    abstract RequestConfirmedActivity bindEditDetailActivity();

    @ContributesAndroidInjector(modules = RequestDetailActivityModule.class)
    abstract RequestDetailActivity bindRequestDetailActivity();

    @ContributesAndroidInjector(modules = VerifyActivityModule.class)
    abstract VerifyActivity bindVerifyActivity();

    @ContributesAndroidInjector(modules = CategoryActivityModule.class)
    abstract CategoryActivity bindCategoryActivity();

    @ContributesAndroidInjector(modules = {ProductListActivityModule.class, SortFragmentProvider.class, FilterFragmentProvider.class})
    abstract ProductListActivity bindProductListActivity();

    @ContributesAndroidInjector(modules = ProductCartListActivityModule.class)
    abstract ProductCartListActivity bindProductCartListActivity();

    @ContributesAndroidInjector(modules = ShippingAddressActivityModule.class)
    abstract ShippingAddressActivity bindShippingAddressActivity();

    @ContributesAndroidInjector(modules = NewAddressActivityModule.class)
    abstract NewAddressActivity bindNewAddressActivity();

    @ContributesAndroidInjector(modules = PaymentActivityModule.class)
    abstract PaymentActivity bindPaymentActivity();

    @ContributesAndroidInjector(modules = ConfirmationActivityModule.class)
    abstract ConfirmationActivity bindConfirmationActivity();

    @ContributesAndroidInjector(modules = OrderCompleteActivityModule.class)
    abstract OrderCompleteActivity bindOrderCompleteActivity();

    @ContributesAndroidInjector(modules = ProductGalleryActivityModule.class)
    abstract ProductGalleryActivity bindProductGalleryActivity();

    @ContributesAndroidInjector(modules = SearchActivityModule.class)
    abstract SearchActivity bindSearchActivity();

    @ContributesAndroidInjector(modules = ProductDetailActivityModule.class)
    abstract ProductDetailActivity bindProductDetailActivity();

    @ContributesAndroidInjector(modules = ProductTrackingActivityModule.class)
    abstract ProductTrackingActivity bindProductTrackingActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class, TextInputDialogProvider.class, HomeFragmentProvider.class,
            MyProfileFragmentProvider.class, UserPageFragmentProvider.class, MyWishListFragmentProvider.class, FavoriteFragmentProvider.class,
            NotificationFragmentProvider.class, RequestFragmentProvider.class, ProductTopicFragmentProvider.class, SortFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {FeedActivityModule.class,
            BlogFragmentProvider.class, OpenSourceFragmentProvider.class})
    abstract FeedActivity bindFeedActivity();

}
