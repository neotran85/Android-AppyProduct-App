package com.appyhome.appyproduct.mvvm.ui.servicerequest.confirm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestConfirmCompletedBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestType;

import javax.inject.Inject;

public class RequestConfirmedActivity extends BaseActivity<ActivityRequestConfirmCompletedBinding, RequestConfirmedViewModel> implements RequestItemNavigator, View.OnClickListener {

    @Inject
    RequestConfirmedViewModel mRequestConfirmedViewModel;
    ActivityRequestConfirmCompletedBinding mBinder;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RequestConfirmedActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRequestConfirmedViewModel);
        mRequestConfirmedViewModel.setNavigator(this);

        setTitle("Confirm Completed");
        activeBackButton();
        Intent intent = getIntent();
        if (intent.hasExtra("id") && intent.hasExtra("type")) {
            String idNumber = intent.getStringExtra("id");
            int type = intent.getIntExtra("type", RequestType.TYPE_REQUEST);
            mRequestConfirmedViewModel.setIdNumber(idNumber);
            mRequestConfirmedViewModel.setType(type);
        }

        mBinder.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                String rating = mBinder.ratingBar.getRating() + "";
                mRequestConfirmedViewModel.markOrderCompleted(mBinder.etAdditionalInfo.getText().toString(), rating);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public RequestConfirmedViewModel getViewModel() {
        return mRequestConfirmedViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_confirm_completed;
    }

    @Override
    public void handleErrorService(Throwable a) {

    }

    @Override
    public void doAfterDataUpdated() {
        finish();
    }
}
