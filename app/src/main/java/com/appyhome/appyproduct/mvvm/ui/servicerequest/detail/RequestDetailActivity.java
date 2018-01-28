package com.appyhome.appyproduct.mvvm.ui.servicerequest.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestType;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.edit.EditDetailActivity;

import javax.inject.Inject;

public class RequestDetailActivity extends BaseActivity<ActivityRequestDetailBinding, RequestItemViewModel> implements RequestItemNavigator, View.OnClickListener {

    @Inject
    RequestItemViewModel mRequestItemViewModel;

    ActivityRequestDetailBinding mBinder;

    private int mType = 0;
    private String mIdNumber = "";

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RequestDetailActivity.class);
        return intent;
    }

    @Override
    public void onResume() {
        super.onResume();
        processDetailData(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestItemViewModel);
        mRequestItemViewModel.setNavigator(this);
        setTitle("SUMMARY");
        activeBackButton();
        mBinder.llAddServices.setOnClickListener(this);
        mBinder.llConfirmation.setOnClickListener(this);
    }

    private void processDetailData(Intent data) {
        if (data != null && data.hasExtra("id")) {
            mIdNumber = data.getStringExtra("id");
            mType = data.getIntExtra("type", RequestType.TYPE_REQUEST);
            try {
                mRequestItemViewModel.fetchData(mIdNumber, mType);
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
            } catch (Exception e) {

            }
        }
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
        intent.putExtra("id", mIdNumber);
        intent.putExtra("type", mType);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public RequestItemViewModel getViewModel() {
        return mRequestItemViewModel;
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
    public void doAfterDataUpdated() {

    }

    @Override
    public void handleErrorService(Throwable a) {

    }
}
