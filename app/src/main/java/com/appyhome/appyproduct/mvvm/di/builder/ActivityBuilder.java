package com.appyhome.appyproduct.mvvm.di.builder;

import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivityModule;
import com.appyhome.appyproduct.mvvm.ui.account.myprofile.MyProfileFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.account.myprofile.textinput.TextInputDialogProvider;
import com.appyhome.appyproduct.mvvm.ui.account.register.RegisterActivity;
import com.appyhome.appyproduct.mvvm.ui.account.register.RegisterActivityModule;
import com.appyhome.appyproduct.mvvm.ui.account.register.verify.VerifyActivity;
import com.appyhome.appyproduct.mvvm.ui.account.register.verify.VerifyActivityModule;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.CategoryActivityModule;
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
    abstract VerifyActivity bindCategoryActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class, TextInputDialogProvider.class, HomeFragmentProvider.class,
            MyProfileFragmentProvider.class, UserPageFragmentProvider.class, MyWishListFragmentProvider.class,
            NotificationFragmentProvider.class, RequestFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {FeedActivityModule.class,
            BlogFragmentProvider.class, OpenSourceFragmentProvider.class})
    abstract FeedActivity bindFeedActivity();

}
