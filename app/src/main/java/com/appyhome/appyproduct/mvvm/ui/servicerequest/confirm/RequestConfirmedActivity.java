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

    public static final int MODE_REFUND = 0;
    public static final int MODE_CONFIRM = 1;

    @Inject
    RequestConfirmedViewModel mRequestConfirmedViewModel;
    ActivityRequestConfirmCompletedBinding mBinder;
    private int mode = MODE_CONFIRM;

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

        activeBackButton();
        Intent intent = getIntent();
        if (intent.hasExtra("id") && intent.hasExtra("type")) {
            String idNumber = intent.getStringExtra("id");
            int type = intent.getIntExtra("type", RequestType.TYPE_REQUEST);
            mRequestConfirmedViewModel.setIdNumber(idNumber);
            mRequestConfirmedViewModel.setType(type);
        }
        if (intent.hasExtra("mode")) {
            mode = intent.getIntExtra("mode", MODE_CONFIRM);
            if (mode == MODE_REFUND) {
                setTitle("Request A Refund");
                mBinder.btnSubmit.setText("Request A Refund");
                mBinder.tvComments.setText("Please comment your reasons of requesting a refund.");
            }
            if (mode == MODE_CONFIRM) {
                setTitle("Confirm Completed");
                mBinder.btnSubmit.setText("Confirm Completed");
                mBinder.tvComments.setText("Please add your comments on the service.");
            }
        }

        mBinder.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                String rating = mBinder.ratingBar.getRating() + "";
                String comments = mBinder.etAdditionalInfo.getText().toString();
                if (mode == MODE_REFUND) {
                    comments = "Request a refund: " + comments;
                }
                mRequestConfirmedViewModel.markOrderCompleted(comments, rating);
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
