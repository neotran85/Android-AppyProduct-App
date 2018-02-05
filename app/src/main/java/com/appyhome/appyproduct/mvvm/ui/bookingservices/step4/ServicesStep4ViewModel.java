package com.appyhome.appyproduct.mvvm.ui.bookingservices.step4;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.ui.myprofile.MyProfileViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

public class ServicesStep4ViewModel extends BaseViewModel<ServicesStep4Navigator> {

    private final ObservableField<String> fieldAddress = new ObservableField<>("");
    private final ObservableField<String> timeSlot1 = new ObservableField<>("");
    private final ObservableField<String> timeSlot2 = new ObservableField<>("");
    private final ObservableField<String> timeSlot3 = new ObservableField<>("");
    private final ObservableField<String> totalCost = new ObservableField<>("");
    private final ObservableField<String> nameService = new ObservableField<>("");
    private final ObservableField<String> additionalDetail = new ObservableField<>("");
    private final ObservableField<String> additionalServices = new ObservableField<>("");

    public final ObservableField<String> numberOfAirCons = new ObservableField<>("1");
    public final ObservableField<Integer> isNumberOfAirConsVisible = new ObservableField<>(View.GONE);

    private int mServiceType = ServiceOrderUserInput.SERVICE_HOME_CLEANING;

    public ServicesStep4ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setUpData() {
        setNumberOfAirCons("x " + ServiceOrderUserInput.getInstance().getNumberOfAirCons() + "");
        setServiceType(ServiceOrderUserInput.getInstance().getType());
        setAddress(ServiceOrderUserInput.getInstance().getAddress());
        setTimeSlot1(ServiceOrderUserInput.getInstance().getTimeSlot1());
        setTimeSlot2(ServiceOrderUserInput.getInstance().getTimeSlot2());
        setTimeSlot3(ServiceOrderUserInput.getInstance().getTimeSlot3());
        setTotalCost(ServiceOrderUserInput.getInstance().getTotalCost());
        setNameService(ServiceOrderUserInput.getInstance().getServiceName());
        setAdditionalDetail(ServiceOrderUserInput.getInstance().getAdditionalInfo());
        setAdditionalServices(ServiceOrderUserInput.getInstance().getExtraServices());

        // fetch profile
        MyProfileViewModel profileViewModel = new MyProfileViewModel(getDataManager(), getSchedulerProvider());
        profileViewModel.fetchUserProfile();
    }

    public void setServiceType(int type) {
        mServiceType = type;
        if (mServiceType == ServiceOrderUserInput.SERVICE_AIR_CON_CLEANING) {
            isNumberOfAirConsVisible.set(View.VISIBLE);
        } else {
            isNumberOfAirConsVisible.set(View.GONE);
        }
    }

    public ObservableField<String> getAddress() {
        return fieldAddress;
    }

    public void setAddress(String add) {
        fieldAddress.set(add);
    }

    public String getAddressString() {
        return fieldAddress.get();
    }

    public String getTimeSlot1() {
        String value = timeSlot1.get();
        return value.length() > 0 ? value : "Time slot 1 not allocated";
    }

    public String getTimeSlot2() {
        String value = timeSlot2.get();
        return value.length() > 0 ? value : "Time slot 2 not allocated";
    }

    public String getTimeSlot3() {
        String value = timeSlot3.get();
        return value.length() > 0 ? value : "Time slot 3 not allocated";
    }

    public void setTimeSlot1(String value) {
        timeSlot1.set(value);
    }

    public void setTimeSlot2(String value) {
        timeSlot2.set(value);
    }

    public void setTimeSlot3(String value) {
        timeSlot3.set(value);
    }


    public void setTotalCost(String cost) {
        totalCost.set(cost);
    }

    public String getTotalCost() {
        return totalCost.get();
    }

    public void setNameService(String value) {
        nameService.set(value);
    }

    public String getNameService() {
        return nameService.get();
    }

    public void setAdditionalDetail(String value) {
        additionalDetail.set(value);
    }

    public String getAdditionalDetail() {
        return additionalDetail.get();
    }

    public int isAdditionalDetailViewVisible() {
        return additionalDetail.get().length() > 0 ? View.VISIBLE : View.GONE;
    }

    public void setAdditionalServices(String value) {
        additionalServices.set(value);
    }

    public void setAdditionalServices(ArrayList<String> values) {
        additionalServices.set(TextUtils.join(" & ", values));
    }

    public String getAdditionalServices() {
        return additionalServices.get();
    }

    public int isAdditionalServiceViewVisible() {
        return additionalServices.get().length() > 0 ? View.VISIBLE : View.GONE;
    }

    public String getNameOfUser() {
        String firstName = getDataManager().getUserFirstName();
        String lastName = getDataManager().getUserLastName();
        if (firstName != null && firstName.length() > 0)
            return firstName + " " + lastName;
        return "";
    }

    public void setNumberOfAirCons(String number) {
        numberOfAirCons.set(number);
    }

    public String getEmailOfUser() {
        return getDataManager().getCurrentUserEmail();
    }

    public String getPhoneNumberOfUser() {
        return getDataManager().getCurrentPhoneNumber();
    }
}
