package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.content.DialogInterface;
import android.view.View;

public interface ProductCartItemNavigator {
    void showContent(ProductCartAdapter adapter, View view, int idProduct);
    void askBeforeRemoved(DialogInterface.OnClickListener listener);
    void backToHomeScreen();
}
