package com.appyhome.appyproduct.mvvm.data.model.db;


import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateRequest;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceOrderUserInput {
    public static final int SERVICE_HOME_CLEANING = 0;
    public static final int SERVICE_AIR_CON_CLEANING = 1;
    private static ServiceOrderUserInput mServiceOrderUserInput;
    private int mType = SERVICE_HOME_CLEANING;
    private JSONObject mServiceInfo;
    private boolean mIsFlexible = false;
    private AppyService mSelectedService;
    private String mAdditionalInfo = "";
    private String mAddress = "";

    private String mTimeSlot1 = "";
    private String mTimeSlot2 = "";
    private String mTimeSlot3 = "";

    private ArrayList<String> mServiceExtra;
    private ArrayList<String> mServiceMain;

    private String mAppointmentId;

    private ArrayList<String> mArrayHomeCleaningOpts;
    private ArrayList<String> mArrayAirConOpts;

    private String txn_ID;

    private int mNumberOfAirCons = 1;

    // Keep these data for the whole life of the app.
    private ArrayList<AppyService> mArrayAppyService;
    private ArrayList<AppyServiceCategory> mArrayAppyServiceCategory;

    private ServiceOrderUserInput() {
    }

    public static ServiceOrderUserInput getInstance() {
        if (mServiceOrderUserInput == null)
            mServiceOrderUserInput = new ServiceOrderUserInput();
        return mServiceOrderUserInput;
    }

    public void clear() {
        mTimeSlot1 = "";
        mTimeSlot2 = "";
        mTimeSlot3 = "";
        mServiceExtra = null;
        mServiceMain = null;
        mAppointmentId = "";
        mArrayHomeCleaningOpts = null;
        mArrayAirConOpts = null;
        txn_ID = "";
        mSelectedService = null;
        mAdditionalInfo = "";
        mAddress = "";
    }

    public String getServiceName() {
        try {
            return getServiceInfo().getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        return "";
    }

    public ArrayList<AppyService> getServices() {
        try {
            ArrayList<AppyService> array = (ArrayList<AppyService>) getServiceInfo().get("services");
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        return null;
    }

    public AppyService getSelectedService() {
        return mSelectedService;
    }

    public void setSelectedService(AppyService object) {
        mSelectedService = object;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public boolean isFlexible() {
        return mIsFlexible;
    }

    public void setFlexible(boolean mIsFlexible) {
        this.mIsFlexible = mIsFlexible;
    }

    private JSONObject getServiceInfo() {
        if (mServiceInfo == null) {
            setUpData(mType);
        }
        return mServiceInfo;
    }

    private ArrayList<AppyService> getArrayServiceByCategory(String category) {
        ArrayList<AppyService> array = new ArrayList<>();
        for (AppyService service : mArrayAppyService) {
            if (service.category.equals(category)) {
                array.add(service);
            }
        }
        return array;
    }

    public void setUpData(int type) {
        mType = type;
        mServiceInfo = new JSONObject();
        String name;
        try {
            switch (type) {
                case SERVICE_AIR_CON_CLEANING:
                    name = mArrayAppyServiceCategory.get(SERVICE_AIR_CON_CLEANING).name;
                    mServiceInfo.put("name", name);
                    mServiceInfo.put("services", getArrayServiceByCategory(name));
                    break;
                case SERVICE_HOME_CLEANING:
                    name = mArrayAppyServiceCategory.get(SERVICE_HOME_CLEANING).name;
                    mServiceInfo.put("name", name);
                    mServiceInfo.put("services", getArrayServiceByCategory(name));
                    break;
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    public String getAdditionalInfo() {
        return mAdditionalInfo;
    }

    public void setAdditionalInfo(String mAdditionalInfo) {
        this.mAdditionalInfo = mAdditionalInfo;
    }

    public String getTimeSlot1() {
        return mTimeSlot1;
    }

    public void setTimeSlot1(String mTimeSlot1) {
        this.mTimeSlot1 = mTimeSlot1;
    }

    public String getTimeSlot2() {
        return mTimeSlot2;
    }

    public void setTimeSlot2(String mTimeSlot2) {
        this.mTimeSlot2 = mTimeSlot2;
    }

    public String getTimeSlot3() {
        return mTimeSlot3;
    }

    public void setTimeSlot3(String mTimeSlot3) {
        this.mTimeSlot3 = mTimeSlot3;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public ArrayList<String> getExtraServices() {
        return mServiceExtra;
    }

    public void setExtraServices(ArrayList<String> mExtraServices) {
        this.mServiceExtra = mExtraServices;
    }

    public String getAppointmentId() {
        return mAppointmentId;
    }

    public void setAppointmentId(String mAppointmentId) {
        this.mAppointmentId = mAppointmentId;
    }

    public AppointmentCreateRequest getAppointmentCreateRequest() {
        AppointmentCreateRequest request = new AppointmentCreateRequest();
        try {
            request.setIdNumber(getAppointmentId());
            request.setAddress(getAddress());

            JSONObject datetime = new JSONObject();
            String time1 = getTimeSlot1();
            if (time1 != null && time1.length() > 0)
                datetime.put("datetime1", time1);
            String time2 = getTimeSlot2();
            if (time2 != null && time2.length() > 0)
                datetime.put("datetime2", time2);
            String time3 = getTimeSlot3();
            if (time3 != null && time3.length() > 0)
                datetime.put("datetime3", time3);

            request.setDateTime(datetime.toString());

            JSONObject services = new JSONObject();
            String name = getServiceInfo().getString("name");
            String price = getTotalCost();
            if (mSelectedService != null) {
                name = name + " - " + mSelectedService.name;
            }
            services.put("service1", name + "::" + price);
            request.setServices(services.toString());

            JSONObject additional = new JSONObject();

            ArrayList<String> additionalArrayOpts = new ArrayList<>();

            String extra1 = mIsFlexible ? "flexible::0" : "not_flexible::0";
            additionalArrayOpts.add(extra1);
            if (mServiceExtra != null && mServiceExtra.size() > 0)
                additionalArrayOpts.addAll(getServicesWithPrice(mServiceExtra));

            if (mType == SERVICE_AIR_CON_CLEANING) {
                if (mArrayAirConOpts != null && mArrayAirConOpts.size() > 0)
                    additionalArrayOpts.addAll(getServicesWithPrice(mArrayAirConOpts));
            }
            if (mType == SERVICE_HOME_CLEANING) {
                if (mArrayHomeCleaningOpts != null && mArrayHomeCleaningOpts.size() > 0)
                    additionalArrayOpts.addAll(getServicesWithPrice(mArrayHomeCleaningOpts));
            }
            String additionalComment = "Additional details: " + mAdditionalInfo + "_" + txn_ID + "::0";
            additionalArrayOpts.add(additionalComment);
            for (int i = 0; i < additionalArrayOpts.size(); i++) {
                additional.put("extra" + i, additionalArrayOpts.get(i));
            }
            request.setAdditional(additional.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }

        return request;
    }

    private ArrayList<String> getServicesWithPrice(ArrayList<String> arrayList) {
        ArrayList<String> result = new ArrayList<>();
        for (String str : arrayList) {
            if (str != null && str.length() > 0) {
                result.add(str + "::0");
            }
        }
        return result;
    }

    public ArrayList<String> getServiceMain() {
        return mServiceMain;
    }

    public void setServiceMain(ArrayList<String> mServiceMain) {
        this.mServiceMain = mServiceMain;
    }

    public String getTotalCost() {
        try {
            if (getSelectedService() != null) {
                Integer value = Integer.valueOf(getSelectedService().price);
                if (mType == SERVICE_AIR_CON_CLEANING) {
                    value = value * mNumberOfAirCons;
                }
                return value.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        return "";
    }

    public ArrayList<String> getArrayHomeCleaningOpts() {
        return mArrayHomeCleaningOpts;
    }

    public void setArrayHomeCleaningOpts(ArrayList<String> mArrayHomeCleaningOpts) {
        this.mArrayHomeCleaningOpts = mArrayHomeCleaningOpts;
    }

    public ArrayList<String> getArrayAirConOpts() {
        return mArrayAirConOpts;
    }

    public void setArrayAirConOpts(ArrayList<String> mArrayAirConOpts) {
        this.mArrayAirConOpts = mArrayAirConOpts;
    }

    public String getTxn_ID() {
        return txn_ID;
    }

    public void setTxn_ID(String txn_ID) {
        this.txn_ID = txn_ID;
    }

    public int getNumberOfAirCons() {
        return mNumberOfAirCons;
    }

    public void setNumberOfAirCons(int numberOfAirCons) {
        this.mNumberOfAirCons = numberOfAirCons;
    }

    public ArrayList<AppyService> getArrayAppyService() {
        return mArrayAppyService;
    }

    public void setArrayAppyService(ArrayList<AppyService> arrayAppyService) {
        this.mArrayAppyService = arrayAppyService;
    }

    public ArrayList<AppyServiceCategory> getArrayAppyServiceCategory() {
        return mArrayAppyServiceCategory;
    }

    public void setArrayAppyServiceCategory(ArrayList<AppyServiceCategory> arrayAppyServiceCategory) {
        this.mArrayAppyServiceCategory = arrayAppyServiceCategory;
    }
}
