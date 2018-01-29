package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestEditBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestType;

import javax.inject.Inject;

public class RequestEditActivity extends BaseActivity<ActivityRequestEditBinding, RequestEditViewModel> implements RequestItemNavigator, View.OnClickListener {

    @Inject
    RequestEditViewModel mRequestEditViewModel;
    ActivityRequestEditBinding mBinder;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RequestEditActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestEditViewModel);
        mRequestEditViewModel.setNavigator(this);

        setTitle("Adding Services");
        activeBackButton();

        Intent intent = getIntent();
        if (intent.hasExtra("id") && intent.hasExtra("type")) {
            String idNumber = intent.getStringExtra("id");
            int type = intent.getIntExtra("type", RequestType.TYPE_REQUEST);
            mRequestEditViewModel.setIdNumber(idNumber);
            mRequestEditViewModel.setType(type);
        }

        mBinder.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                mRequestEditViewModel.updateDetailChange(mBinder.etAdditionalInfo.getText().toString(),
                        mBinder.etAdditionalAmount.getText().toString());
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public RequestEditViewModel getViewModel() {
        return mRequestEditViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_edit;
    }

    @Override
    public void handleErrorService(Throwable a) {

    }

    @Override
    public void doAfterDataUpdated() {
        finish();
    }
}
