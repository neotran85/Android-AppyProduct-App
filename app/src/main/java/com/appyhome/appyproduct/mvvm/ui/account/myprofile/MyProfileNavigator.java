package com.appyhome.appyproduct.mvvm.ui.account.myprofile;


import android.view.View;

public interface MyProfileNavigator {

    void handleErrorService(Throwable throwable);

    void onFieldClick(View view);

    void backToHomeScreen();
}
