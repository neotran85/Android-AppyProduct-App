package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestEditDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestType;

import javax.inject.Inject;

public class EditDetailActivity extends BaseActivity<ActivityRequestEditDetailBinding, EditDetailViewModel> implements EditDetailNavigator {

    @Inject
    EditDetailViewModel mEditDetailViewModel;
    ActivityRequestEditDetailBinding mBinder;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, EditDetailActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mEditDetailViewModel);
        setTitle("Confirm Completed");
        activeBackButton();
        Intent intent = getIntent();
        if (intent.hasExtra("detail")) {
            String data = intent.getStringExtra("detail");
            int type = intent.getIntExtra("type", RequestType.TYPE_REQUEST);
            mEditDetailViewModel.processData(data, type);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public EditDetailViewModel getViewModel() {
        return mEditDetailViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_edit_detail;
    }

}
