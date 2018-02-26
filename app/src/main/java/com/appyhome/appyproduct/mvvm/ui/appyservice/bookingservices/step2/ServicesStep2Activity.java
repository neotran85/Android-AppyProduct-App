package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step2;

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
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.data.remote.ApiUrlConfig;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep2Binding;
import com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step3.ServicesStep3Activity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.common.custom.ItemsSelectionView;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import javax.inject.Inject;

public class ServicesStep2Activity extends BaseActivity<ActivityServicesBookingStep2Binding, ServicesStep2ViewModel> implements ServicesStep2Navigator, View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener {

    private static final int SERVICE_TIME_START = 9;
    private static final int SERVICE_TIME_END = 21;
    @Inject
    ServicesStep2ViewModel mServicesStep2ViewModel;
    ActivityServicesBookingStep2Binding mBinder;
    private Button mBtnTimeSlot;
    private String mDateSelectedShowedString = "";
    private String mDateSelectedString = "";
    private ItemsSelectionView mExtraServicesView;

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
    
    private ServiceOrderUserInput getOrderUserInput() {
        return getViewModel().getDataManager().getServiceOrderUserInput();
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
                    getOrderUserInput().setAdditionalInfo(text);
                }
            }
        });
        mBinder.cbFlexible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getOrderUserInput().setFlexible(isChecked);
            }
        });

        ViewUtils.setOnClickListener(this, mBinder.btTimeslot1,
                mBinder.btTimeslot2, mBinder.btTimeslot3, mBinder.rlReadOurSchedulingFAQ);
    }

    private void setUpMultipleExtraServices() {
        View llCeilingFan = mBinder.llServiceExtra.findViewById(R.id.llCeilingFan);
        View llIroning = mBinder.llServiceExtra.findViewById(R.id.llIroning);
        View llLaundry = mBinder.llServiceExtra.findViewById(R.id.llLaundry);
        View llFolding = mBinder.llServiceExtra.findViewById(R.id.llFolding);
        mExtraServicesView = new ItemsSelectionView(true, llCeilingFan, llIroning, llLaundry, llFolding);
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
            case R.id.rlReadOurSchedulingFAQ:
                viewReadOurSchedulingFAQ();
                break;
        }
    }

    public void viewReadOurSchedulingFAQ() {
        AlertManager.getInstance(this).openInformationBrowser(getString(R.string.our_scheduling_faq),
                ApiUrlConfig.URL_SCHEDULING_FAQ);
    }

    private boolean checkIfDateTimeInputted() {
        String datetime1 = ViewUtils.getStringByTag(mBinder.btTimeslot1);
        String datetime2 = ViewUtils.getStringByTag(mBinder.btTimeslot2);
        String datetime3 = ViewUtils.getStringByTag(mBinder.btTimeslot3);
        return datetime1.length() > 0 || datetime2.length() > 0 || datetime3.length() > 0;
    }

    private void clickNextButton() {
        if (checkIfDateTimeInputted()) {
            mServicesStep2ViewModel.updateServiceOrderInfo(ViewUtils.getStringByTag(mBinder.btTimeslot1),
                    ViewUtils.getStringByTag(mBinder.btTimeslot2),
                    ViewUtils.getStringByTag(mBinder.btTimeslot3),
                    mExtraServicesView.getSelectedStringValue());
            goToStep3();
        } else {
            AlertManager.getInstance(this).showLongToast(getString(R.string.step2_warning_date_time));
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
        datePicker.setMinDate(now);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.please_select_the_exact_time));
    }

    private void openTimePicker(Calendar selected) {
        TimePickerDialog timePicker = TimePickerDialog.newInstance(
                this,
                selected.get(Calendar.HOUR),
                selected.get(Calendar.MINUTE),
                true
        );
        Calendar today = Calendar.getInstance();
        if (DataUtils.isTheSameDate(selected, today)) {
            timePicker.setMinTime(today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE), 0);
            timePicker.setMaxTime(SERVICE_TIME_END, 0, 0);
        } else {
            timePicker.setMinTime(SERVICE_TIME_START, 0, 0);
            timePicker.setMaxTime(SERVICE_TIME_END, 0, 0);
        }
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
