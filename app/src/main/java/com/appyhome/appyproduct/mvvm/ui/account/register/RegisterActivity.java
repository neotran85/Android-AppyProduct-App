package com.appyhome.appyproduct.mvvm.ui.account.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.remote.ApiUrlConfig;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRegisterBinding;
import com.appyhome.appyproduct.mvvm.ui.account.login.LoginActivity;
import com.appyhome.appyproduct.mvvm.ui.account.register.verify.VerifyActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.ValidationUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements RegisterNavigator {

    @Inject
    RegisterViewModel mRegisterViewModel;

    ActivityRegisterBinding mBinder;

    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
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
        mBinder.setViewModel(mRegisterViewModel);
        mRegisterViewModel.setNavigator(this);

        TextInputEditText[] arrayTextInputs = {mBinder.etFirstName, mBinder.etLastName,
                mBinder.etEmailAddress, mBinder.etNumberPhone,
                mBinder.etAccountPassword, mBinder.etRetypedPassword};

        for (TextInputEditText edt : arrayTextInputs) {
            edt.addTextChangedListener(new InputTextWatcher(edt));
        }

        if (getIntent().hasExtra("phone")) {
            mBinder.etNumberPhone.setText(getIntent().getStringExtra("phone"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showErrorToast(getString(R.string.error_network_general));
    }

    @Override
    public void register() {
        if (!isNetworkConnected()) {
            return;
        }

        View view = getCurrentFocus();
        if (view != null)
            view.clearFocus();

        String firstName = mBinder.etFirstName.getText().toString();
        if (firstName.length() == 0) {
            showError(getString(R.string.register_error_missing_first_name));
            showTextInputError(mBinder.etFirstName);
            return;
        }
        String lastName = mBinder.etLastName.getText().toString();
        if (lastName.length() == 0) {
            showError(getString(R.string.register_error_missing_last_name));
            showTextInputError(mBinder.etLastName);
            return;
        }
        String email = mBinder.etEmailAddress.getText().toString();
        if (email.length() == 0) {
            showError(getString(R.string.register_error_missing_email_address));
            showTextInputError(mBinder.etEmailAddress);
            return;
        } else if (!ValidationUtils.isEmailValid(email)) {
            showTextInputError(mBinder.etEmailAddress);
            showError(getString(R.string.register_error_invalid_email));
            return;
        }

        String phoneNumber = DataUtils.getStringNotNull(mBinder.etNumberPhone.getText().toString());
        phoneNumber = ValidationUtils.correctNumberPhone(phoneNumber, "60");
        if (phoneNumber.length() == 0) {
            showError(getString(R.string.register_error_missing_phone));
            showTextInputError(mBinder.etNumberPhone);
            return;
        } else if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            showError(getString(R.string.register_error_invalid_phone));
            showTextInputError(mBinder.etNumberPhone);
            return;
        }

        String password = mBinder.etAccountPassword.getText().toString();
        if (password.length() == 0) {
            showError(getString(R.string.register_error_missing_password));
            showTextInputError(mBinder.etAccountPassword);
            return;
        } else if (!ValidationUtils.isPasswordValid(password)) {
            showError(getString(R.string.register_error_invalid_password));
            showTextInputError(mBinder.etAccountPassword);
            return;
        }

        String retypedPassword = mBinder.etRetypedPassword.getText().toString();
        if (retypedPassword.length() == 0) {
            showError(getString(R.string.register_error_missing_retyped_password));
            showTextInputError(mBinder.etRetypedPassword);
            return;
        }

        if (!password.equals(retypedPassword)) {
            mBinder.etRetypedPassword.requestFocus();
            showError(getString(R.string.register_error_password_not_matched));
            showTextInputError(mBinder.etRetypedPassword);
            return;
        }
        hideKeyboard();
        mRegisterViewModel.register(firstName, lastName, email, phoneNumber, password);
    }

    private void showError(String text) {
        mBinder.txtError.setText(text);
        mBinder.txtError.setVisibility(text.length() > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public RegisterViewModel getViewModel() {
        return mRegisterViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    private void clearTextInputError(TextInputEditText edt) {
        edt.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.white));
        edt.setHintTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.hint_text));
        showError("");
    }

    private void showTextInputError(TextInputEditText edt) {
        edt.requestFocus();
        if (edt.getText().length() > 0)
            edt.setTextColor(ContextCompat.getColor(this, R.color.red_dark));
        else
            edt.setHintTextColor(ContextCompat.getColor(this, R.color.red_dark2));
    }

    @Override
    public void login() {
        if (!isNetworkConnected()) {
            return;
        }
        // Do login after sign up succeeded
        String phoneNumber = mBinder.etNumberPhone.getText().toString();
        phoneNumber = ValidationUtils.correctNumberPhone(phoneNumber, "60");
        String password = mBinder.etAccountPassword.getText().toString();
        mRegisterViewModel.doUserLogin(phoneNumber, password);
    }

    @Override
    public void showErrorServer() {
        showError(getString(R.string.login_error_internal_server));
    }

    @Override
    public void showErrorOthers() {
        showError(getString(R.string.register_error_something));
    }

    @Override
    public void showErrorPhoneDuplicated() {
        showError(getString(R.string.register_error_phone_duplicated));
        showTextInputError(mBinder.etNumberPhone);
    }

    @Override
    public void showErrorEmailDuplicated() {
        showError(getString(R.string.register_error_email_duplicated));
        showTextInputError(mBinder.etEmailAddress);
    }

    @Override
    public void openPrivacyPolicy() {
        AlertManager.getInstance(this).openInformationBrowser(getString(R.string.register_privacy_policy),
                ApiUrlConfig.URL_PRIVACY_POLICY);
    }

    @Override
    public void openTermsOfUsage() {
        AlertManager.getInstance(this).openInformationBrowser(getString(R.string.register_terms),
                ApiUrlConfig.URL_TERMS_CONDITIONS);
    }

    @Override
    public void openPhoneNumberVerification() {
        Intent intent = VerifyActivity.getStartIntent(this);
        startActivityForResult(intent, LoginActivity.REQUEST_SIGN_UP);
    }

    private class InputTextWatcher implements TextWatcher {
        private TextInputEditText edt;

        public InputTextWatcher(TextInputEditText et) {
            edt = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            clearTextInputError(edt);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LoginActivity.REQUEST_SIGN_UP:
                if (resultCode == RESULT_OK) {
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

}
