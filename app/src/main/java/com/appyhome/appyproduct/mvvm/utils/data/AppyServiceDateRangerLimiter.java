package com.appyhome.appyproduct.mvvm.utils.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.wdullaer.materialdatetimepicker.date.DateRangeLimiter;

import java.util.Calendar;

public class AppyServiceDateRangerLimiter implements DateRangeLimiter {
    private Calendar mStartDate;

    public AppyServiceDateRangerLimiter() {

    }

    @SuppressWarnings("WeakerAccess")

    public AppyServiceDateRangerLimiter(Parcel in) {

    }

    @SuppressWarnings("WeakerAccess")
    public static final Parcelable.Creator<AppyServiceDateRangerLimiter> CREATOR
            = new Parcelable.Creator<AppyServiceDateRangerLimiter>() {
        public AppyServiceDateRangerLimiter createFromParcel(Parcel in) {
            return new AppyServiceDateRangerLimiter(in);
        }

        public AppyServiceDateRangerLimiter[] newArray(int size) {
            return new AppyServiceDateRangerLimiter[size];
        }
    };

    @Override
    public int getMinYear() {
        return mStartDate.get(Calendar.YEAR);
    }

    @Override
    public int getMaxYear() {
        return 2100;
    }

    @NonNull
    @Override
    public Calendar getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Calendar startDate) {
        mStartDate = startDate;
    }

    @NonNull
    @Override
    public Calendar getEndDate() {
        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, 2100);
        output.set(Calendar.DAY_OF_MONTH, 1);
        output.set(Calendar.MONTH, Calendar.JANUARY);
        return output;

    }

    @Override
    public boolean isOutOfRange(int year, int month, int day) {
        return false;
    }

    @NonNull
    @Override
    public Calendar setToNearestDate(@NonNull Calendar day) {
        return mStartDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}