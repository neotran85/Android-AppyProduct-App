package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRequestEditDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestType;

import javax.inject.Inject;

public class EditDetailActivity extends BaseActivity<ActivityRequestEditDetailBinding, EditDetailViewModel> implements EditDetailNavigator, View.OnClickListener {

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
        mEditDetailViewModel.setNavigator(this);

        setTitle("Confirm Completed");
        activeBackButton();
        Intent intent = getIntent();
        if (intent.hasExtra("detail")) {
            String data = intent.getStringExtra("detail");
            int type = intent.getIntExtra("type", RequestType.TYPE_REQUEST);
            mEditDetailViewModel.processData(data, type);
        }

        mBinder.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                String rating = mBinder.ratingBar.getRating() + "";
                mEditDetailViewModel.markOrderCompleted(mBinder.etAdditionalInfo.getText().toString(), rating);
                break;
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

    @Override
    public void handleErrorService(Throwable a) {

    }

    @Override
    public void goBackAfterSubmitting(String data) {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("result", data);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
