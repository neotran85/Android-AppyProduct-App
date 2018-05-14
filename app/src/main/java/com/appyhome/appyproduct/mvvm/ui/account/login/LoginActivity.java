package com.appyhome.appyproduct.mvvm.ui.account.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.remote.ApiUrlConfig;
import com.appyhome.appyproduct.mvvm.databinding.ActivityLoginBinding;
import com.appyhome.appyproduct.mvvm.ui.account.register.RegisterActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.NetworkUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.ValidationUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    public static final int REQUEST_SIGN_UP = 2222;

    @Inject
    LoginViewModel mLoginViewModel;

    ActivityLoginBinding mBinder;

    @Inject
    int mLayoutId;

    private boolean mIsError = false;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mLoginViewModel);
        mBinder.setNavigator(this);
        mLoginViewModel.setNavigator(this);

        TextInputEditText[] arrayTextInputs = {mBinder.etPhoneNumber, mBinder.etPassword};
        for (TextInputEditText edt : arrayTextInputs) {
            edt.addTextChangedListener(new LoginTextWatcher(edt));
        }
        mBinder.etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                login();
            }
            return false;
        });
        showError(DataUtils.getStringSafely(getIntent(), "message"));
    }

    private void clearTextInputError(TextInputEditText editText) {
        editText.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.medium_dark_gray));
        editText.setHintTextColor(ContextCompat.getColor(LoginActivity.this, R.color.hint_text));
        showError("");
    }

    @Override
    public void openForgetPassword() {
        if (isNetworkConnected()) {
            AlertManager.getInstance(this).openInformationBrowser("Forget Password",
                    ApiUrlConfig.URL_FORGET_PASSWORD);
        }
    }

    @Override
    public void openSignUpActivity() {
        if (isNetworkConnected()) {
            Intent intent = RegisterActivity.getStartIntent(LoginActivity.this);
            intent.putExtra("phone", mBinder.etPhoneNumber.getText().toString());
            startActivityForResult(intent, REQUEST_SIGN_UP);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SIGN_UP:
                if (resultCode == RESULT_OK) {
                    getViewModel().fetchUserData();
                }
                break;
        }
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showErrorToast(getString(R.string.error_network_general));
    }

    @Override
    public void login() {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
        }
        if (!NetworkUtils.isNetworkConnected(this)) {
            return;
        }
        String phoneNumber = DataUtils.getStringNotNull(mBinder.etPhoneNumber.getText().toString());
        String password = DataUtils.getStringNotNull(mBinder.etPassword.getText().toString());
        if (phoneNumber.length() == 0) {
            showError(getString(R.string.login_error_missing_phone));
            showTextInputError(mBinder.etPhoneNumber);
            return;
        } else {
            phoneNumber = ValidationUtils.correctNumberPhone(phoneNumber, "60");
            if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
                showError(getString(R.string.login_error_invalid_phone));
                showTextInputError(mBinder.etPhoneNumber);
                return;
            }
        }

        if (password.length() == 0) {
            showError(getString(R.string.login_error_missing_phone));
            showTextInputError(mBinder.etPassword);
            return;
        } else if (!ValidationUtils.isPasswordValid(password)) {
            showError(getString(R.string.login_error_invalid_password));
            showTextInputError(mBinder.etPassword);
            return;
        }
        hideKeyboard();
        getViewModel().login(phoneNumber, password);
    }

    private void showTextInputError(TextInputEditText edt) {
        edt.requestFocus();
        if (edt.getText().length() > 0)
            edt.setTextColor(ContextCompat.getColor(this, R.color.red_dark));
        else
            edt.setHintTextColor(ContextCompat.getColor(this, R.color.red_dark2));
    }

    @Override
    public void showErrorServer() {
        showError(getString(R.string.login_error_internal_server));
    }

    @Override
    public void showErrorLogin() {
        showError(getString(R.string.login_error));
    }

    private void showError(String text) {
        mIsError = text != null && text.length() > 0;
        mBinder.txtError.setText(text);
        mBinder.txtError.setVisibility(text.length() > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public LoginViewModel getViewModel() {
        return mLoginViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    private void showSignUpDialog() {
        AlertManager.getInstance(this).showConfirmationDialog(getString(R.string.title_login_error),
                getString(R.string.suggestion_sign_up),
                (dialog, which) -> openSignUpActivity());
    }

    private void finishActivityResult() {
        closeLoading();
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        AlertManager.getInstance(this).showLongToast(getString(R.string.login_success), R.style.AppyToast_Account);
        finish();
    }

    @Override
    public void onFetchUserInfo_Done() {
        finishActivityResult();
        Log.v("onFetchUserInfo_Done", "SUCCESS");
    }

    @Override
    public void onFetchUserInfo_Failed() {
        finishActivityResult();
        Log.v("onFetchUserInfo_Failed", "FAILED");
    }

    private class LoginTextWatcher implements TextWatcher {
        private TextInputEditText editText;

        public LoginTextWatcher(TextInputEditText e) {
            editText = e;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mIsError)
                clearTextInputError(editText);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
