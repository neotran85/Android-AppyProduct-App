package com.appyhome.appyproduct.mvvm.ui.myprofile;


import android.view.View;

public interface MyProfileNavigator {

    void goBack();

    void handleError(Throwable throwable);

    void onFieldClick(View view);
}
