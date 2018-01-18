package com.appyhome.appyproduct.mvvm.ui.bookingservices.step2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep2Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step3.ServicesStep3Activity;
import com.appyhome.appyproduct.mvvm.utils.AlertUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import javax.inject.Inject;

public class ServicesStep2Activity extends BaseActivity<ActivityServicesBookingStep2Binding, ServicesStep2ViewModel> implements ServicesStep2Navigator, View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener {

    @Inject
    ServicesStep2ViewModel mServicesStep2ViewModel;

    ActivityServicesBookingStep2Binding mActivityServicesBookingStep2Binding;

    private Button mBtnTimeSlot;
    private String mDateSelectedShowedString = "";
    private String mDateSelectedString = "";

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep2Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityServicesBookingStep2Binding = getViewDataBinding();
        mServicesStep2ViewModel.setNavigator(this);
        setTitle("Details");
        activeBackButton();
        setUpListeners();
    }

    private void setUpListeners() {
        mActivityServicesBookingStep2Binding.btnNext.setOnClickListener(this);
        mActivityServicesBookingStep2Binding.etAdditionalInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mActivityServicesBookingStep2Binding.etAdditionalInfo.getText().toString();
                if (text != null && text.length() > 0) {
                    ServiceOrderInfo.getInstance().setAdditionalInfo(text);
                }
            }
        });
        mActivityServicesBookingStep2Binding.cbFlexible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ServiceOrderInfo.getInstance().setFlexible(isChecked);
            }
        });

        mActivityServicesBookingStep2Binding.btTimeslot1.setOnClickListener(this);
        mActivityServicesBookingStep2Binding.btTimeslot2.setOnClickListener(this);
        mActivityServicesBookingStep2Binding.btTimeslot3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                ServiceOrderInfo.getInstance().setTimeSlot1(mActivityServicesBookingStep2Binding.btTimeslot1.getTag().toString());
                ServiceOrderInfo.getInstance().setTimeSlot2(mActivityServicesBookingStep2Binding.btTimeslot2.getTag().toString());
                ServiceOrderInfo.getInstance().setTimeSlot3(mActivityServicesBookingStep2Binding.btTimeslot3.getTag().toString());
                goToStep3();
                break;
            case R.id.btTimeslot1:
            case R.id.btTimeslot2:
            case R.id.btTimeslot3:
                mBtnTimeSlot = (Button) view;
                openDatePicker();
                break;
        }
    }

    private void openDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }
    @Override
    public void onCancel(DialogInterface dialogInterface) {
        AlertUtils.getInstance(this).showLongToast("Please select the exact time.");
    }
    private void openTimePicker(Calendar current) {
        TimePickerDialog timePicker = TimePickerDialog.newInstance(
                this,
                current.get(Calendar.HOUR),
                current.get(Calendar.MINUTE),
                true
        );
        timePicker.setOnCancelListener(this);
        timePicker.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mDateSelectedString = mDateSelectedString + hourOfDay + ":" + minute + ":00";
        mDateSelectedShowedString = hourOfDay + ":" + minute + " " + mDateSelectedString;
        if (mBtnTimeSlot != null) {
            mBtnTimeSlot.setText(mDateSelectedString);
            mBtnTimeSlot.setTag(mDateSelectedString);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mDateSelectedString = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        openTimePicker(calendar);
        mDateSelectedString =  year + "-" + (monthOfYear + 1)  + "-" + dayOfMonth;
        mDateSelectedShowedString = "on " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }


    @Override
    public ServicesStep2ViewModel getViewModel() {
        return mServicesStep2ViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_booking_step2;
    }

    @Override
    public void goToStep3() {
        startActivity(ServicesStep3Activity.getStartIntent(this));
    }

}
