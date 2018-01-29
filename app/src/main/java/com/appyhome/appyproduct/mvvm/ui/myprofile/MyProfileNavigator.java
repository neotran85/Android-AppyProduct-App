package com.appyhome.appyproduct.mvvm.ui.myprofile;


import android.view.View;

public interface MyProfileNavigator {

    void goBack();

    void handleErrorService(Throwable throwable);

    void onFieldClick(View view);

    void backToHomeScreen();
}
