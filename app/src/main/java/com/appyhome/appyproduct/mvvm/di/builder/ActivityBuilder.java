package com.appyhome.appyproduct.mvvm.di.builder;

import com.appyhome.appyproduct.mvvm.ui.about.AboutFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step1.ServicesStep1Activity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step1.ServicesStep1ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step2.ServicesStep2Activity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step2.ServicesStep2ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step3.ServicesStep3Activity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step3.ServicesStep3ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step4.ServicesStep4Activity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step4.ServicesStep4ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step5.ServicesStep5Activity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step5.ServicesStep5ActivityModule;
import com.appyhome.appyproduct.mvvm.ui.feed.FeedActivity;
import com.appyhome.appyproduct.mvvm.ui.feed.FeedActivityModule;
import com.appyhome.appyproduct.mvvm.ui.feed.blogs.BlogFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.home.HomeFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.ui.login.LoginActivityModule;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivityModule;
import com.appyhome.appyproduct.mvvm.ui.myprofile.MyProfileFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.myprofile.textinput.TextInputDialogProvider;
import com.appyhome.appyproduct.mvvm.ui.mywishlist.MyWishListFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.notification.NotificationFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.register.RegisterActivity;
import com.appyhome.appyproduct.mvvm.ui.register.RegisterActivityModule;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestFragmentProvider;
import com.appyhome.appyproduct.mvvm.ui.splash.SplashActivity;
import com.appyhome.appyproduct.mvvm.ui.splash.SplashActivityModule;
import com.appyhome.appyproduct.mvvm.ui.userpage.UserPageFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

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

    @ContributesAndroidInjector(modules = {MainActivityModule.class,
            AboutFragmentProvider.class, TextInputDialogProvider.class, HomeFragmentProvider.class,
            MyProfileFragmentProvider.class, UserPageFragmentProvider.class, MyWishListFragmentProvider.class,
            NotificationFragmentProvider.class, RequestFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {FeedActivityModule.class,
            BlogFragmentProvider.class, OpenSourceFragmentProvider.class})
    abstract FeedActivity bindFeedActivity();

}
