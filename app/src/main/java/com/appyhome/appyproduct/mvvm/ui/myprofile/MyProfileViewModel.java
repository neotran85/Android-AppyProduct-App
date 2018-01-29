package com.appyhome.appyproduct.mvvm.ui.myprofile;

import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;

public class MyProfileViewModel extends BaseViewModel<MyProfileNavigator> {

    public ObservableField<String> lastName = new ObservableField<>("");
    public ObservableField<String> firstName = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public MyProfileViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onFieldClick(View view) {
        getNavigator().onFieldClick(view);
    }

    public void fetchUserProfile() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getUserProfile()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject userGetResponse) throws Exception {
                        setIsLoading(false);
                        AppLogger.d(userGetResponse.toString());
                        if(userGetResponse != null) {
                            if(userGetResponse.getString("code").equals("200")) {
                                try {
                                    JSONObject message = userGetResponse.getJSONObject("message");
                                    lastName.set(message.getString("last_name"));
                                    firstName.set(message.getString("first_name"));
                                    email.set(message.getString("email"));
                                    phoneNumber.set(message.getString("phone_number"));
                                    return;
                                } catch (Exception e) {

                                }
                            }
                        }
                        getNavigator().handleErrorService(null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(null);
                    }
                }));
    }

}
