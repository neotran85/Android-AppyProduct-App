package com.appyhome.appyproduct.mvvm.ui.register;

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
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.NetworkUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.TextUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.ValidationUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import java.util.ArrayList;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements RegisterNavigator {

    @Inject
    RegisterViewModel mRegisterViewModel;

    ActivityRegisterBinding mBinder;

    private ArrayList<TextInputEditText> mArrayTextInputs;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mRegisterViewModel);
        mRegisterViewModel.setNavigator(this);

        mArrayTextInputs = new ArrayList<>();
        mArrayTextInputs.add(mBinder.etFirstName);
        mArrayTextInputs.add(mBinder.etLastName);
        mArrayTextInputs.add(mBinder.etEmailAddress);
        mArrayTextInputs.add(mBinder.etNumberPhone);
        mArrayTextInputs.add(mBinder.etAccountPassword);
        mArrayTextInputs.add(mBinder.etRetypedPassword);
        for (int i = 0; i < mArrayTextInputs.size(); i++) {
            final TextInputEditText edt = mArrayTextInputs.get(i);
            edt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edt.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.white));
                    edt.setHintTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.hint_text));
                    showError("");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        if(getIntent().hasExtra("phone")) {
            mBinder.etNumberPhone.setText(getIntent().getStringExtra("phone"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mArrayTextInputs.clear();
        mArrayTextInputs = null;
    }

    @Override
    public void doAfterRegisterSucceeded() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
    }

    @Override
    public void register() {
        View view = getCurrentFocus();
        if (view != null)
            view.clearFocus();
        if (!NetworkUtils.isNetworkConnected(this)) {
            return;
        }
        String firstName = mBinder.etFirstName.getText().toString();
        if (firstName == null || firstName.length() == 0) {
            showError(getString(R.string.register_error_missing_first_name));
            showTextInputError(mBinder.etFirstName);
            return;
        }
        String lastName = mBinder.etLastName.getText().toString();
        if (lastName == null || lastName.length() == 0) {
            showError(getString(R.string.register_error_missing_last_name));
            showTextInputError(mBinder.etLastName);
            return;
        }
        String email = mBinder.etEmailAddress.getText().toString();
        if (email == null || email.length() == 0) {
            showError(getString(R.string.register_error_missing_email_address));
            showTextInputError(mBinder.etEmailAddress);
            return;
        } else if (!ValidationUtils.isEmailValid(email)) {
            showTextInputError(mBinder.etEmailAddress);
            showError(getString(R.string.register_error_invalid_email));
            return;
        }

        String phoneNumber = TextUtils.getString(mBinder.etNumberPhone.getText().toString());
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
        if (password == null || password.length() == 0) {
            showError(getString(R.string.register_error_missing_password));
            showTextInputError(mBinder.etAccountPassword);
            return;
        } else if (!ValidationUtils.isPasswordValid(password)) {
            showError(getString(R.string.register_error_invalid_password));
            showTextInputError(mBinder.etAccountPassword);
            return;
        }

        String retypedPassword = mBinder.etRetypedPassword.getText().toString();
        if (retypedPassword == null || retypedPassword.length() == 0) {
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
        // Do login after sign up succeeded
        String phoneNumber = mBinder.etNumberPhone.getText().toString();
        String password = mBinder.etAccountPassword.getText().toString();
        mRegisterViewModel.doUserLogin(phoneNumber, password);
    }

    @Override
    public void showSuccessLogin() {
        AlertManager.getInstance(this).showLongToast(getString(R.string.register_success_logged));
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
        AlertManager.getInstance(this).openInformationBrowser("Privacy Policy",
                ApiUrlConfig.URL_PRIVACY_POLICY);
    }
    @Override
    public void openTermsOfUsage() {
        AlertManager.getInstance(this).openInformationBrowser("Terms & Conditions",
                ApiUrlConfig.URL_TERMS_CONDITIONS);
    }
}
