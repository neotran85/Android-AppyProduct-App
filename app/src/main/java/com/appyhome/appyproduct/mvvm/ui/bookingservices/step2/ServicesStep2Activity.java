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
import com.appyhome.appyproduct.mvvm.ui.custom.MultipleChooseView;
import com.appyhome.appyproduct.mvvm.utils.data.AppyServiceDateRangerLimiter;
import com.appyhome.appyproduct.mvvm.utils.data.AppyServiceTimepointLimiter;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;

import javax.inject.Inject;

public class ServicesStep2Activity extends BaseActivity<ActivityServicesBookingStep2Binding, ServicesStep2ViewModel> implements ServicesStep2Navigator, View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener {

    @Inject
    ServicesStep2ViewModel mServicesStep2ViewModel;

    ActivityServicesBookingStep2Binding mBinder;

    private Button mBtnTimeSlot;
    private String mDateSelectedShowedString = "";
    private String mDateSelectedString = "";
    private MultipleChooseView mExtraServicesView;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep2Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mServicesStep2ViewModel);
        mServicesStep2ViewModel.setNavigator(this);
        setTitle("Details");
        activeBackButton();
        setUpListeners();
        setUpMultipleExtraServices();
    }

    private void setUpListeners() {
        mBinder.btnNext.setOnClickListener(this);
        mBinder.etAdditionalInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mBinder.etAdditionalInfo.getText().toString();
                if (text != null && text.length() > 0) {
                    ServiceOrderInfo.getInstance().setAdditionalInfo(text);
                }
            }
        });
        mBinder.cbFlexible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ServiceOrderInfo.getInstance().setFlexible(isChecked);
            }
        });

        mBinder.btTimeslot1.setOnClickListener(this);
        mBinder.btTimeslot2.setOnClickListener(this);
        mBinder.btTimeslot3.setOnClickListener(this);

    }

    private void setUpMultipleExtraServices() {
        View llCeilingFan = mBinder.llServiceExtra.findViewById(R.id.llCeilingFan);
        View llIroning = mBinder.llServiceExtra.findViewById(R.id.llIroning);
        View llLaundry = mBinder.llServiceExtra.findViewById(R.id.llLaundry);
        View llFolding = mBinder.llServiceExtra.findViewById(R.id.llFolding);
        mExtraServicesView = new MultipleChooseView(llCeilingFan, llIroning, llLaundry, llFolding);
        mExtraServicesView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                clickNextButton();
                break;
            case R.id.btTimeslot1:
            case R.id.btTimeslot2:
            case R.id.btTimeslot3:
                mBtnTimeSlot = (Button) view;
                openDatePicker();
                break;
        }
    }

    private boolean checkIfDateTimeInputted() {
        String datetime1 = ViewUtils.getStringByTag(mBinder.btTimeslot1);
        String datetime2 = ViewUtils.getStringByTag(mBinder.btTimeslot2);
        String datetime3 = ViewUtils.getStringByTag(mBinder.btTimeslot3);
        return datetime1.length() > 0 || datetime2.length() > 0 || datetime3.length() > 0;
    }

    private void clickNextButton() {
        if (checkIfDateTimeInputted()) {
            updateServiceOrderInfo();
            goToStep3();
        } else {
            AlertManager.getInstance(this).showLongToast(getString(R.string.step2_warning_date_time));
        }
    }

    private void updateServiceOrderInfo() {
        ServiceOrderInfo.getInstance().setTimeSlot1(ViewUtils.getStringByTag(mBinder.btTimeslot1));
        ServiceOrderInfo.getInstance().setTimeSlot2(ViewUtils.getStringByTag(mBinder.btTimeslot2));
        ServiceOrderInfo.getInstance().setTimeSlot3(ViewUtils.getStringByTag(mBinder.btTimeslot3));
        ServiceOrderInfo.getInstance().setExtraServices(mExtraServicesView.getSelectedStringValue());
    }

    private void openDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        AppyServiceDateRangerLimiter limiter = new AppyServiceDateRangerLimiter();
        limiter.setStartDate(now);
        datePicker.setDateRangeLimiter(limiter);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        AlertManager.getInstance(this).showLongToast("Please select the exact time.");
    }

    private void openTimePicker(Calendar current) {
        TimePickerDialog timePicker = TimePickerDialog.newInstance(
                this,
                current.get(Calendar.HOUR),
                current.get(Calendar.MINUTE),
                true
        );
        AppyServiceTimepointLimiter limiter = new AppyServiceTimepointLimiter();
        limiter.setMaxTime(new Timepoint(21, 0));
        limiter.setMinTime(new Timepoint(8, 0));
        timePicker.setTimepointLimiter(limiter);
        timePicker.setOnCancelListener(this);
        timePicker.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourOfDayStr = (hourOfDay < 10) ? "0" + hourOfDay : hourOfDay + "";
        String minuteStr = (minute < 10) ? "0" + minute : minute + "";

        mDateSelectedString = mDateSelectedString + " " + hourOfDayStr + ":" + minuteStr + ":00";
        mDateSelectedShowedString = hourOfDayStr + ":" + minuteStr + " " + mDateSelectedShowedString;
        if (mBtnTimeSlot != null) {
            mBtnTimeSlot.setText(mDateSelectedShowedString);
            mBtnTimeSlot.setTag(mDateSelectedString);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mDateSelectedString = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        openTimePicker(calendar);
        int month = monthOfYear + 1;
        String dayOfMonthStr = (dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth + "";
        String monthOfYearStr = (month < 10) ? "0" + month : month + "";
        mDateSelectedString = year + "-" + monthOfYearStr + "-" + dayOfMonthStr;
        mDateSelectedShowedString = "on " + dayOfMonthStr + "/" + monthOfYearStr + "/" + year;
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
