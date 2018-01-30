package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.ReceiptGetRequest;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import io.reactivex.functions.Consumer;

public class RequestItemViewModel extends BaseViewModel<RequestItemNavigator> {

    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> price = new ObservableField<>("");
    public ObservableField<String> address = new ObservableField<>("");
    public ObservableField<String> timeCreated = new ObservableField<>("");
    public ObservableField<String> timeCreatedLabel = new ObservableField<>("");
    public ObservableField<String> dateTime1 = new ObservableField<>("");
    public ObservableField<String> dateTime2 = new ObservableField<>("");
    public ObservableField<String> dateTime3 = new ObservableField<>("");
    public ObservableField<String> safetyCode = new ObservableField<>("");
    public ObservableField<String> additionalComments = new ObservableField<>("");
    public ObservableField<String> statusOfOrder = new ObservableField<>("");
    public ObservableField<String> additionalServices = new ObservableField<>("");
    public ObservableField<String> additionalDetail = new ObservableField<>("");
    public ObservableField<Float> rating = new ObservableField<>(-1.0f);
    public ObservableField<String> statusText = new ObservableField<>(RequestStatus.ADMIN_REVIEWING);

    public ObservableField<Integer> isTimeSlot1Visible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> isTimeSlot2Visible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> isTimeSlot3Visible = new ObservableField<>(View.GONE);

    public ObservableField<Integer> isStatusViewVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> isSafetyCodeVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> isAddServiceVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> isConfirmationVisible = new ObservableField<>(View.GONE);

    private int mType = 0;

    private String mIdNumber = "";

    private String editCode = "";


    private String phoneNumber = "";



    public RequestItemViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public RequestItemViewModel() {
        super();
    }

    public RequestItemViewModel(JSONObject item, int type) {
        super();
        processData(item, type);
    }

    public void processData(JSONObject item, int type) {
        mType = type;
        try {
            AppLogger.d("processData: hasID: " + item.has("id"));

            if (item.has("id"))
                mIdNumber = item.getString("id");
            if (item.has("edit_code")) {
                editCode = item.getString("edit_code");
                AppLogger.d("processData: editCode: " + editCode);
            }
            if (item.has("phone_number")) {
                phoneNumber = item.getString("phone_number");
                AppLogger.d("processData: phone: " + phoneNumber);
            }
            if (item.has("safety_code")) {
                safetyCode.set("Safety Code: " + item.getString("safety_code"));
            }
            if (item.has("status")) {
                statusOfOrder.set(item.getString("status"));
                isConfirmationVisible.set(isConfirmationVisible());
                isAddServiceVisible.set(isAddServiceVisible());
                isSafetyCodeVisible.set(isSafetyCodeVisible());
                isStatusViewVisible.set(isStatusVisible());
            }
            if (item.has("rating")) {
                try {
                    String rate = item.getString("rating");
                    rating.set(Float.parseFloat(rate));
                } catch (Exception e) {

                }
            }
            if (item.has("services")) {
                String serviceString = item.getString("services");
                if (serviceString.contains("{")) {
                    JSONObject temp = new JSONObject(serviceString);
                    String tempStr = temp.getString("service1");
                    String[] result = tempStr.split("::");
                    this.title.set(result[0]);
                    this.price.set(result[1]);
                } else {
                    this.title.set(serviceString);
                    String priceString = item.getString("price");
                    this.price.set(priceString);
                }
            }
            if (item.has("address")) {
                this.address.set(item.getString("address"));
                AppLogger.d("processData: address: " + item.getString("address"));
            }
            if (item.has("time")) {
                this.timeCreated.set(item.getString("time"));
            }
            if (item.has("archived_at")) {
                this.timeCreated.set(item.getString("archived_at"));
                isStatusViewVisible.set(isStatusVisible());
                this.statusText.set(RequestStatus.CLOSED);
            }
            if (item.has("datetime")) {
                String dateTimeStr = item.getString("datetime");
                if (dateTimeStr.contains("{")) {
                    JSONObject datetime = new JSONObject(dateTimeStr);
                    if (datetime != null) {
                        if (datetime.has("datetime1")) {
                            dateTime1.set(datetime.getString("datetime1"));
                            isTimeSlot1Visible.set(isTimeSlot1Visible());
                        }
                        if (datetime.has("datetime2")) {
                            dateTime2.set(datetime.getString("datetime2"));
                            isTimeSlot2Visible.set(isTimeSlot2Visible());
                        }
                        if (datetime.has("datetime3")) {
                            dateTime3.set(datetime.getString("datetime3"));
                            isTimeSlot3Visible.set(isTimeSlot3Visible());
                        }
                    }
                } else {
                    dateTime1.set(dateTimeStr);
                    isTimeSlot1Visible.set(isTimeSlot1Visible());
                }
            }

            if (item.has("additional")) {
                // Do something
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getIdNumber() {
        return mIdNumber;
    }

    public void setIdNumber(String mIdNumber) {
        this.mIdNumber = mIdNumber;
    }

    private int isSafetyCodeVisible() {
        return mType == RequestType.TYPE_ORDER ? View.VISIBLE : View.GONE;
    }

    private int isAddServiceVisible() {
        return mType == RequestType.TYPE_ORDER
                && statusOfOrder.get().equals(RequestStatus.PENDING_EXECUTION)
                ? View.VISIBLE : View.GONE;
    }

    private int isConfirmationVisible() {
        return isAddServiceVisible();
    }

    private int isStatusVisible() {
        boolean value = (mType == RequestType.TYPE_ORDER
                && statusOfOrder.get().equals(RequestStatus.ADMIN_REVIEW))
                || (mType == RequestType.TYPE_CLOSED);
        return value ? View.VISIBLE : View.GONE;
    }

    private int isTimeSlot2Visible() {
        return dateTime2.get() != null && dateTime2.get().length() > 0 ? View.VISIBLE : View.GONE;
    }

    private int isTimeSlot3Visible() {
        return dateTime3.get() != null && dateTime3.get().length() > 0 ? View.VISIBLE : View.GONE;
    }

    private int isTimeSlot1Visible() {
        return dateTime1.get() != null && dateTime1.get().length() > 0 ? View.VISIBLE : View.GONE;
    }

    public void fetchData(String id, final int type) {
        setIsLoading(true);
        setIdNumber(id);
        mType = type;
        if (type == RequestType.TYPE_REQUEST) {
            timeCreatedLabel.set("Created at:");
            getCompositeDisposable().add(getDataManager()
                    .getAppointment(new AppointmentGetRequest(id))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<JSONObject>() {
                        @Override
                        public void accept(JSONObject response) throws Exception {
                            setIsLoading(false);
                            handleResult(response);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            setIsLoading(false);
                        }
                    }));
        }
        if (type == RequestType.TYPE_ORDER) {
            timeCreatedLabel.set("Validated at:");
            getCompositeDisposable().add(getDataManager()
                    .getOrder(new OrderGetRequest(id))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<JSONObject>() {
                        @Override
                        public void accept(JSONObject response) throws Exception {
                            setIsLoading(false);
                            handleResult(response);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            setIsLoading(false);
                        }
                    }));
        }
        if (type == RequestType.TYPE_CLOSED) {
            timeCreatedLabel.set("Archived at:");
            getCompositeDisposable().add(getDataManager()
                    .getReceipt(new ReceiptGetRequest(id))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<JSONObject>() {
                        @Override
                        public void accept(JSONObject response) throws Exception {
                            setIsLoading(false);
                            handleResult(response);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            setIsLoading(false);
                        }
                    }));
        }
    }

    private void handleResult(JSONObject result) {
        if (result != null && result.has("code")) {
            try {
                String code = result.getString("code");
                if (code.equals("200") && result.has("message"))
                    handleResultSuccess(result.getJSONObject("message"));
            } catch (Exception e) {

            }
        }
    }

    private void handleResultSuccess(JSONObject result) {
        try {
            result = getItemInside(result);
            processData(result, mType);
            AppLogger.d("handleResultSuccess: " + result.toString());
            getNavigator().doAfterDataUpdated();
        } catch (Exception e) {

        }
    }

    private JSONObject getItemInside(JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
            // get some_name_i_wont_know in str_Name
            String str_Name = keys.next();
            // get the value i care about
            JSONObject result = jsonObject.getJSONObject(str_Name);
            result.put("id", str_Name);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEditCode() {
        return editCode;
    }
    public void setEditCode(String code) {
        editCode = code;
    }
}
