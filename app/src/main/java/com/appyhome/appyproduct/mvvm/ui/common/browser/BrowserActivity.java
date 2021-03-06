package com.appyhome.appyproduct.mvvm.ui.common.browser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.ActivityBrowserBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;

public class BrowserActivity extends BaseActivity<ActivityBrowserBinding, BrowserViewModel> {

    @Inject
    BrowserViewModel mBrowserViewModel;

    ActivityBrowserBinding mBinder;

    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, BrowserActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mBrowserViewModel);
        mBrowserViewModel.setNavigator(this);
        mBrowserViewModel.setUp(getIntent());
        activeBackButton();
    }

    public void openLink(String url) {
        mBinder.webView.getSettings().setJavaScriptEnabled(true);
        mBinder.webView.loadUrl(url);
        mBinder.webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (mBrowserViewModel != null)
                    mBrowserViewModel.setIsLoading(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public BrowserViewModel getViewModel() {
        return mBrowserViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

}
