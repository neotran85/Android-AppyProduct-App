package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class BookingServicesActivityModule {

    @Provides
    BookingServicesViewModel provideBookingServicesViewModel(DataManager dataManager,
                                                   SchedulerProvider schedulerProvider) {
        return new BookingServicesViewModel(dataManager, schedulerProvider);
    }

}
