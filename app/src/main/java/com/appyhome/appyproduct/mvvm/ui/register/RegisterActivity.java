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
import com.appyhome.appyproduct.mvvm.data.model.api.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.SignUpResponse;
import com.appyhome.appyproduct.mvvm.databinding.ActivityRegisterBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.main.MainActivity;
import com.appyhome.appyproduct.mvvm.utils.AlertUtils;
import com.appyhome.appyproduct.mvvm.utils.NetworkUtils;
import com.appyhome.appyproduct.mvvm.utils.ValidationUtils;

import java.util.ArrayList;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements RegisterNavigator {

    @Inject
    RegisterViewModel mRegisterViewModel;

    ActivityRegisterBinding mActivityRegisterBinding;

    private ArrayList<TextInputEditText> mArrayTextInputs;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegisterBinding = getViewDataBinding();
        mRegisterViewModel.setNavigator(this);

        mArrayTextInputs = new ArrayList<>();
        mArrayTextInputs.add(mActivityRegisterBinding.etFirstName);
        mArrayTextInputs.add(mActivityRegisterBinding.etLastName);
        mArrayTextInputs.add(mActivityRegisterBinding.etEmailAddress);
        mArrayTextInputs.add(mActivityRegisterBinding.etNumberPhone);
        mArrayTextInputs.add(mActivityRegisterBinding.etAccountPassword);
        mArrayTextInputs.add(mActivityRegisterBinding.etRetypedPassword);
        for(int i = 0; i <mArrayTextInputs.size(); i++) {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mArrayTextInputs.clear();
        mArrayTextInputs = null;
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void register() {
        View view = getCurrentFocus();
        if (view != null)
            view.clearFocus();
        if(!NetworkUtils.isNetworkConnected(this)) {
            return;
        }
        String firstName = mActivityRegisterBinding.etFirstName.getText().toString();
        if (firstName == null || firstName.length() == 0) {
            showError(getString(R.string.register_error_missing_first_name));
            showTextInputError(mActivityRegisterBinding.etFirstName);
            return;
        }
        String lastName = mActivityRegisterBinding.etLastName.getText().toString();
        if (lastName == null || lastName.length() == 0) {
            showError(getString(R.string.register_error_missing_last_name));
            showTextInputError(mActivityRegisterBinding.etLastName);
            return;
        }
        String email = mActivityRegisterBinding.etEmailAddress.getText().toString();
        if (email == null || email.length() == 0) {
            showError(getString(R.string.register_error_missing_email_address));
            showTextInputError(mActivityRegisterBinding.etEmailAddress);
            return;
        } else if (!ValidationUtils.isEmailValid(email)) {
            showTextInputError(mActivityRegisterBinding.etEmailAddress);
            showError(getString(R.string.register_error_invalid_email));
            return;
        }

        String phoneNumber = mActivityRegisterBinding.etNumberPhone.getText().toString();
        if (phoneNumber == null || phoneNumber.length() == 0) {
            showError(getString(R.string.register_error_missing_phone));
            showTextInputError(mActivityRegisterBinding.etNumberPhone);
            return;
        } else if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            showError(getString(R.string.register_error_invalid_phone));
            showTextInputError(mActivityRegisterBinding.etNumberPhone);
            return;
        }

        String password = mActivityRegisterBinding.etAccountPassword.getText().toString();
        if (password == null || password.length() == 0) {
            showError(getString(R.string.register_error_missing_password));
            showTextInputError(mActivityRegisterBinding.etAccountPassword);
            return;
        } else if (!ValidationUtils.isPasswordValid(password)) {
            showError(getString(R.string.register_error_invalid_password));
            showTextInputError(mActivityRegisterBinding.etAccountPassword);
            return;
        }

        String retypedPassword = mActivityRegisterBinding.etRetypedPassword.getText().toString();
        if (retypedPassword == null || retypedPassword.length() == 0) {
            showError(getString(R.string.register_error_missing_retyped_password));
            showTextInputError(mActivityRegisterBinding.etRetypedPassword);
            return;
        }

        if (!password.equals(retypedPassword)) {
            mActivityRegisterBinding.etRetypedPassword.requestFocus();
            showError(getString(R.string.register_error_password_not_matched));
            showTextInputError(mActivityRegisterBinding.etRetypedPassword);
            return;
        }
        hideKeyboard();
        mRegisterViewModel.register(firstName, lastName, email, phoneNumber, password);
    }

    private void showError(String text) {
        mActivityRegisterBinding.txtError.setText(text);
        mActivityRegisterBinding.txtError.setVisibility(text.length() > 0 ? View.VISIBLE : View.INVISIBLE);
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
        if(edt.getText().length() > 0)
            edt.setTextColor(ContextCompat.getColor(this, R.color.red_dark));
        else
            edt.setHintTextColor(ContextCompat.getColor(this, R.color.red_dark2));
    }
    @Override
    public void handleSignUpResponse(SignUpResponse response) {
        if (response == null || response.getStatusCode() == null
                || response.getStatusCode().length() <= 0
                || response.getMessage() == null
                || response.getMessage().length() <= 0) {
            showError(getString(R.string.register_error_internal_server));
            return;
        }

        String statusCode = response.getStatusCode();
        String message = response.getMessage();
        if (message.equals("phone_number_duplicate")) {
            showError(getString(R.string.register_error_phone_duplicated));
            showTextInputError(mActivityRegisterBinding.etNumberPhone);
            return;
        }
        if (statusCode.equals("200")) {
            if (message.contains("user_created")) {
                String[] result = message.split(":");
                if (result != null && result.length == 2) {
                    String userId = result[1];
                    // Do login after sign up succeeded
                    String phoneNumber = mActivityRegisterBinding.etNumberPhone.getText().toString();
                    String password = mActivityRegisterBinding.etAccountPassword.getText().toString();
                    mRegisterViewModel.doUserLogin(phoneNumber, password);
                    return;
                }
            }
            showError(getString(R.string.register_error_something));
            return;
        }
    }

    @Override
    public void handleLoginResponse(LoginResponse response) {
        if (response == null || response.getStatusCode() == null
                || response.getStatusCode().length() <= 0
                || response.getMessage() == null
                || response.getMessage().length() <= 0) {
            showError(getString(R.string.login_error_internal_server));
            return;
        }
        String statusCode = response.getStatusCode();
        String message = response.getMessage();
        if(statusCode.equals("200")) {
            String[] result = message.split(" ");
            if(result != null && result.length == 2) {
                String token = result[1];
                if(token != null && token.length() > 0) {
                    AlertUtils.getInstance(this).showLongToast(getString(R.string.register_success_logged));
                    openMainActivity();
                }
            }
        } else {
            showError(getString(R.string.login_error));
        }
    }
}
