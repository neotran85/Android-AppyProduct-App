package com.appyhome.appyproduct.mvvm.ui.servicerequest.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestType;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.edit.EditDetailActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;

import javax.inject.Inject;

public class RequestDetailActivity extends BaseActivity<ActivityRequestDetailBinding, RequestDetailViewModel> implements RequestDetailNavigator, View.OnClickListener {

    @Inject
    RequestDetailViewModel mRequestDetailViewModel;

    ActivityRequestDetailBinding mBinder;

    private int mType = 0;

    private static final int CODE_REQUEST_BACK = 111;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RequestDetailActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestDetailViewModel);
        mRequestDetailViewModel.setNavigator(this);
        Intent intent = getIntent();
        setTitle("SUMMARY");
        if (intent.hasExtra("detail")) {
            String data = intent.getStringExtra("detail");
            mType = intent.getIntExtra("type", RequestType.TYPE_REQUEST);
            mRequestDetailViewModel.processData(data, mType);
            switch (mType) {
                case RequestType.TYPE_CLOSED:
                    setTitle("RECEIPT SUMMARY");
                    break;
                case RequestType.TYPE_ORDER:
                    setTitle("ORDER SUMMARY");
                    break;
                case RequestType.TYPE_REQUEST:
                    setTitle("REQUEST SUMMARY");
                    break;
            }
        }
        activeBackButton();
        mBinder.llAddServices.setOnClickListener(this);
        mBinder.llConfirmation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llConfirmation:
                openEditDetailActivity();
                break;
            case R.id.llAddServices:
                break;
        }
    }

    private void openEditDetailActivity() {
        Intent intent = getIntent();
        intent.setClass(this, EditDetailActivity.class);
        startActivityForResult(intent, CODE_REQUEST_BACK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public RequestDetailViewModel getViewModel() {
        return mRequestDetailViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLogger.d("onActivityResult");
        if(requestCode == CODE_REQUEST_BACK) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                AppLogger.d("onActivityResult: " + result);
                mRequestDetailViewModel.processData(result, mType);
            }
        }
    }
}
