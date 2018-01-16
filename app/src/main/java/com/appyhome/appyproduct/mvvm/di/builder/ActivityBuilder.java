package com.appyhome.appyproduct.mvvm.di.builder;

import com.appyhome.appyproduct.mvvm.ui.about.AboutFragmentProvider;
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

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class,
            AboutFragmentProvider.class, TextInputDialogProvider.class, HomeFragmentProvider.class,
            MyProfileFragmentProvider.class, UserPageFragmentProvider.class, MyWishListFragmentProvider.class,
            NotificationFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {FeedActivityModule.class,
            BlogFragmentProvider.class, OpenSourceFragmentProvider.class})
    abstract FeedActivity bindFeedActivity();

}
