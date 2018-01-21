package com.appyhome.appyproduct.mvvm.ui.bookingservices.step2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.data.AppyServiceDateRangerLimiter;
import com.appyhome.appyproduct.mvvm.utils.data.AppyServiceTimepointLimiter;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
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

        mBinder.llCeilingFan.setOnClickListener(this);
        mBinder.llIroning.setOnClickListener(this);
        mBinder.llLaundry.setOnClickListener(this);
        mBinder.llFolding.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                setTimeSlots();
                ServiceOrderInfo.getInstance().setExtraServices(getExtraServicesSelected());
                goToStep3();
                break;
            case R.id.btTimeslot1:
            case R.id.btTimeslot2:
            case R.id.btTimeslot3:
                mBtnTimeSlot = (Button) view;
                openDatePicker();
                break;

            case R.id.llCeilingFan:
            case R.id.llIroning:
            case R.id.llLaundry:
            case R.id.llFolding:
                chooseExtraServices(view);
                break;
        }
    }

    private String getExtraServicesSelected() {
        String extraServices = "";
        Boolean booleanValue = (Boolean) mBinder.llFolding.getTag();
        if (booleanValue != null) {
            extraServices = booleanValue ? (mBinder.tvFolding.getText().toString()) : "";
            booleanValue = (Boolean) mBinder.llLaundry.getTag();
            extraServices = extraServices + (booleanValue ? (" & " + mBinder.tvLaundry.getText().toString()) : "");
            booleanValue = (Boolean) mBinder.llIroning.getTag();
            extraServices = extraServices + (booleanValue ? (" & " + mBinder.tvIroning.getText().toString()) : "");
            booleanValue = (Boolean) mBinder.llCeilingFan.getTag();
            extraServices = extraServices + (booleanValue ? (" & " + mBinder.tvCeilingFan.getText().toString()) : "");
        }
        return extraServices;
    }

    private void setTimeSlots() {
        ServiceOrderInfo.getInstance().setTimeSlot1(ViewUtils.getStringByTag(mBinder.btTimeslot1));
        ServiceOrderInfo.getInstance().setTimeSlot2(ViewUtils.getStringByTag(mBinder.btTimeslot2));
        ServiceOrderInfo.getInstance().setTimeSlot3(ViewUtils.getStringByTag(mBinder.btTimeslot3));
    }

    private void chooseExtraServices(View view) {
        if (view != null) {
            Boolean booleanValue = (Boolean) view.getTag();
            if (booleanValue == null || !booleanValue) {
                view.setBackgroundResource(R.drawable.view_rounded_bg_orange);
                booleanValue = true;
            } else {
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                booleanValue = false;
            }
            view.setTag(booleanValue);
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
        mDateSelectedString = mDateSelectedString + " "  + hourOfDay + ":" + minute + ":00";
        mDateSelectedShowedString = hourOfDay + ":" + minute + " " + mDateSelectedShowedString;
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
        mDateSelectedString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
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
