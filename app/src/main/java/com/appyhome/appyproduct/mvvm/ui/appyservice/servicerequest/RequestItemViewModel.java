package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest;

import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderGetRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.ReceiptGetRequest;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import io.reactivex.Single;
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
    public ObservableField<Integer> isConfirmationVisible = new ObservableField<>(View.GONE);

    private int mType = 0;

    private String mIdNumber = "";

    private String editCode = "";

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

    private void processData(JSONObject item, int type) {
        mType = type;
        try {
            mIdNumber = DataUtils.getStringSafely(item, "id");
            editCode = DataUtils.getStringSafely(item, "edit_code");
            safetyCode.set(DataUtils.getStringSafely(item, "safety_code"));
            statusOfOrder.set(DataUtils.getStringSafely(item, "status"));

            isConfirmationVisible.set(isConfirmationVisible());
            isSafetyCodeVisible.set(isSafetyCodeVisible());
            isStatusViewVisible.set(isStatusVisible());

            rating.set(Float.parseFloat(DataUtils.getStringSafely(item, "rating")));

            String serviceString = DataUtils.getStringSafely(item, "services");
            if (serviceString.contains("{")) {
                JSONObject temp = new JSONObject(serviceString);
                String tempStr = DataUtils.getStringSafely(temp, "service1");
                String[] result = tempStr.split("::");
                this.title.set(result[0]);
                this.price.set(result[1]);
            } else {
                this.title.set(serviceString);
                String priceString = DataUtils.getStringSafely(item, "price");
                this.price.set(priceString);
            }
            address.set(DataUtils.getStringSafely(item, "address"));
            timeCreated.set(DataUtils.getStringSafely(item, "time"));

            if (item.has("archived_at")) {
                this.timeCreated.set(item.getString("archived_at"));
                isStatusViewVisible.set(isStatusVisible());
                this.statusText.set(RequestStatus.CLOSED);
            }
            String dateTimeStr = DataUtils.getStringSafely(item, "datetime");
            if (dateTimeStr.contains("{")) {
                JSONObject datetime = new JSONObject(dateTimeStr);
                dateTime1.set(DataUtils.getStringSafely(datetime, "datetime1"));
                isTimeSlot1Visible.set(isTimeSlot1Visible());
                dateTime2.set(DataUtils.getStringSafely(datetime, "datetime2"));
                isTimeSlot2Visible.set(isTimeSlot2Visible());
                dateTime3.set(DataUtils.getStringSafely(datetime, "datetime3"));
                isTimeSlot3Visible.set(isTimeSlot3Visible());
            } else {
                dateTime1.set(dateTimeStr);
                isTimeSlot1Visible.set(isTimeSlot1Visible());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
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

    private int isConfirmationVisible() {
        return mType == RequestType.TYPE_ORDER
                && statusOfOrder.get().equals(RequestStatus.PENDING_EXECUTION)
                ? View.VISIBLE : View.GONE;
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

    interface TypeRequestData {
        Single<JSONObject> getRequestData(DataManager manager, String id);

        String getDateLabel();
    }

    public void setEditCode(String code) {
        editCode = code;
    }

    public void fetchData(String id, final int type) {
        setIsLoading(true);
        setIdNumber(id);
        mType = type;
        Single<JSONObject> target = mArrayTypeRequest[mType]
                .getRequestData(getDataManager(), id);
        getCompositeDisposable().add(target
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
        timeCreatedLabel.set(mArrayTypeRequest[mType].getDateLabel());
    }

    private void handleResult(JSONObject result) {
        if (result != null && result.has("code")) {
            try {
                String code = result.getString("code");
                if (code.equals("200") && result.has("message"))
                    handleResultSuccess(result.getJSONObject("message"));
            } catch (Exception e) {
                e.printStackTrace();
                Crashlytics.logException(e);
            }
        }
    }

    private void handleResultSuccess(JSONObject result) {
        try {
            result = getItemInside(result);
            processData(result, mType);
            getNavigator().doAfterDataUpdated();
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
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
            Crashlytics.logException(e);
        }
        return null;
    }

    public String getEditCode() {
        return editCode;
    }

    private TypeRequestData[] mArrayTypeRequest = {
            new TypeRequestData() {
                public Single<JSONObject> getRequestData(DataManager manager, String id) {
                    return manager.getAppointment(new AppointmentGetRequest(id));
                }

                public String getDateLabel() {
                    return "Created at:";
                }
            },
            new TypeRequestData() {
                public Single<JSONObject> getRequestData(DataManager manager, String id) {
                    return manager.getOrder(new OrderGetRequest(id));
                }

                public String getDateLabel() {
                    return "Validated at:";
                }
            },
            new TypeRequestData() {
                public Single<JSONObject> getRequestData(DataManager manager, String id) {
                    return manager.getReceipt(new ReceiptGetRequest(id));
                }

                public String getDateLabel() {
                    return "Archived at:";
                }
            }
    };
}
